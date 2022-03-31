package ru.otus.spring.homework05.shell;

import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.exception.AppException;
import ru.otus.spring.homework05.service.LibraryConverter;
import ru.otus.spring.homework05.service.LibraryService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@ShellComponent
public class ShellCommands {
    private final LibraryService libraryService;
    private final LibraryConverter libraryConverter;

    public ShellCommands(LibraryService libraryService, LibraryConverter libraryConverter) {
        this.libraryService = libraryService;
        this.libraryConverter = libraryConverter;
    }


    @ShellMethod(value = "List all books", key = {"lb"})
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\t", "ID", "AUTHOR_ID", "GENRE_ID", "TITLE")).append("\n");
        libraryService.getAllBooks().forEach(book -> sb.append(libraryConverter.toString(book)).append("\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Get Book Info", key = {"gb"})
    public String getBookInfo(@Min(1) Long id) {
        try {
            return String.join("\t", "ID", "AUTHOR_ID", "GENRE_ID", "TITLE") + "\n" +
                    libraryConverter.toString(libraryService.getBookById(id)) + "\n";
        } catch (DataAccessException dae) {
            return "Error! " + dae.getMessage();
        }
    }

    @ShellMethod(value = "Add book", key = {"ab"})
    public String addBook(@NotBlank String title, @Min(1) Long authorId, @Min(1) Long genreId) {
        return libraryConverter.toString(libraryService.createBook(title, authorId, genreId));
    }

    @ShellMethod(value = "Update book", key = {"ub"})
    public String updateBook(@Min(1) Long id, @NotBlank String title, @Min(1) Long authorId, @Min(1) Long genreId) {
        try {
            libraryService.updateBook(id, title, authorId, genreId);
            return "Book updated successfully!";
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Delete book", key = {"db"})
    public String deleteBook(@Min(1) Long id) {
        try {
            libraryService.deleteBook(id);
            return "Book deleted successfully!";
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "List all authors", key = {"la"})
    public String getAllAuthors() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\t", "ID", "NAME")).append("\n");
        libraryService.getAllAuthors().forEach(author -> sb.append(libraryConverter.toString(author)).append("\n"));
        return sb.toString();
    }

    @ShellMethod(value = "List all genres", key = {"lg"})
    public String getAllGenres() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\t", "ID", "NAME")).append("\n");
        libraryService.getAllGenres().forEach(genre -> sb.append(libraryConverter.toString(genre)).append("\n"));
        return sb.toString();
    }
}