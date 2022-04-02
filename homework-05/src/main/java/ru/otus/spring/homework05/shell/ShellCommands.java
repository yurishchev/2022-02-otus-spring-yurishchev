package ru.otus.spring.homework05.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework05.domain.Book;
import ru.otus.spring.homework05.exception.AppException;
import ru.otus.spring.homework05.service.LibraryConverter;
import ru.otus.spring.homework05.service.LibraryService;
import ru.otus.spring.homework05.service.Localizer;
import ru.otus.spring.homework05.service.Settings;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@ShellComponent
public class ShellCommands {
    private static final String BOOK_TABLE_HEADER = String.join("\t", "ID", "AUTHOR_ID", "GENRE_ID", "TITLE");

    private final LibraryService libraryService;
    private final LibraryConverter libraryConverter;
    private final Localizer localizer;
    private final Settings settings;

    public ShellCommands(LibraryService libraryService, LibraryConverter libraryConverter, Localizer localizer, Settings settings) {
        this.libraryService = libraryService;
        this.libraryConverter = libraryConverter;
        this.localizer = localizer;
        this.settings = settings;
    }


    @ShellMethod(value = "List all books", key = {"lb"})
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append(BOOK_TABLE_HEADER).append("\n");
        libraryService.getAllBooks().forEach(book -> sb.append(libraryConverter.toString(book)).append("\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Get Book Details. Format: 'gb book-id'", key = {"gb"})
    public String getBookInfo(@Min(1) Long id) {
        try {
            return libraryConverter.toString(libraryService.getBookById(id));
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Add book. Format: 'ab author-id genre-id title'", key = {"ab"})
    public String addBook(@Min(1) Long authorId, @Min(1) Long genreId, @NotBlank String title) {
        try {
            Book book = libraryService.createBook(title, authorId, genreId);
            return localizer.getMessage("app.book.create.success") + "\n" + libraryConverter.toString(book);
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Update book. Format: 'ub book-id author-id genre-id title'", key = {"ub"})
    public String updateBook(@Min(1) Long id, @Min(1) Long authorId, @Min(1) Long genreId, @NotBlank String title) {
        try {
            libraryService.updateBook(id, title, authorId, genreId);
            return localizer.getMessage("app.book.update.success");
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Delete book. Format: 'db book-id'", key = {"db"})
    public String deleteBook(@Min(1) @ShellOption("Book Id") Long id) {
        try {
            libraryService.deleteBook(id);
            return localizer.getMessage("app.book.delete.success");
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

/*
    @ShellMethod(value = "Set application locale", key = {"sl"})
    public String setLocale(@ShellOption(defaultValue = DEFAULT_LANGUAGE, value = {"locale"}) String localeTag) {
        if (!StringUtils.hasLength(localeTag) || settings.getLanguages().stream().noneMatch(locale -> locale.equals(localeTag))) {
            return localizer.getMessage("app.unsupported.language.text", settings.getLanguages());
        }

        settings.setSelectedLanguage(localeTag);
        return localizer.getMessage("app.language.text", settings.getSelectedLanguage());
    }

    @ShellMethod(value = "Print application settings", key = {"ps"})
    public String getApplicationSettings() {
        return localizer.getMessage("app.language.text", settings.getSelectedLanguage()) + "\n" +
                localizer.getMessage("app.available.languages", settings.getLanguages());
    }
*/
}