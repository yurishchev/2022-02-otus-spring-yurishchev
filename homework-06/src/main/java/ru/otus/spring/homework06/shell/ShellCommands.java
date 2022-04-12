package ru.otus.spring.homework06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Comment;
import ru.otus.spring.homework06.exception.AppException;
import ru.otus.spring.homework06.service.LibraryConverter;
import ru.otus.spring.homework06.service.LibraryService;
import ru.otus.spring.homework06.service.Localizer;
import ru.otus.spring.homework06.service.Settings;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ShellCommands {
    private static final String BOOK_TABLE_HEADER =
            String.join("\t", "ID", "AUTHOR_INFO", "GENRE_INFO", "COMMENT_NUMBER", "TITLE");

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

    //---------- Book ----------------

    @ShellMethod(value = "List all books", key = {"lb"})
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append(BOOK_TABLE_HEADER).append("\n");
        libraryService.getAllBooks().forEach(book -> sb.append(libraryConverter.toString(book)).append("\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Get book details. Format: 'gb <book-id>'", key = {"gb"})
    public String getBookInfo(@Min(1) Long id) {
        try {
            return libraryConverter.toString(libraryService.getBookById(id));
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Add book. Format: 'ab <author-id> <genre-id-1;genre-id-2;...> <title>'", key = {"ab"})
    public String addBook(@Min(1) Long authorId, @NotBlank String genreIdsAsString, @NotBlank String title) {
        try {
            List<Long> genreIds = getIdsFromString(genreIdsAsString);
            Book book = libraryService.createBook(title, authorId, genreIds);
            return localizer.getMessage("app.book.create.success") + "\n" + libraryConverter.toString(book);
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Update book. Format: 'ub <book-id> <author-id> <genre-id-1;genre-id-2;...> <title>'", key = {"ub"})
    public String updateBook(@Min(1) Long id, @Min(1) Long authorId, @NotBlank String genreIdsAsString, @NotBlank String title) {
        try {
            List<Long> genreIds = getIdsFromString(genreIdsAsString);
            libraryService.updateBook(id, title, authorId, genreIds);
            return localizer.getMessage("app.book.update.success");
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    private List<Long> getIdsFromString(String str) {
        try {
            return Arrays.stream(str.split(";")).map(Long::valueOf).collect(Collectors.toList());
        } catch (IllegalArgumentException iae) {
            throw new AppException("Non-numeric ids passed! Ids=" + str, iae);
        }
    }

    @ShellMethod(value = "Delete book. Format: 'db <book-id>'", key = {"db"})
    public String deleteBook(@Min(1) @ShellOption("Book Id") Long id) {
        try {
            libraryService.deleteBook(id);
            return localizer.getMessage("app.book.delete.success");
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    //---------- Comments --------------

    @ShellMethod(value = "List all comments in book. Format: 'lc <book-id>'", key = {"lc"})
    public String getAllCommentsInBook(@Min(1) Long id) {
        StringBuilder sb = new StringBuilder();
        sb.append(BOOK_TABLE_HEADER).append("\n");
        libraryService.getAllCommentsByBookId(id).forEach(comment -> sb.append(libraryConverter.toString(comment)).append("\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Add book. Format: 'ac <book-id> <text> <from>'", key = {"ac"})
    public String addComment(@Min(1) Long bookId, @NotBlank String text, @NotBlank String from) {
        try {
            Comment comment = libraryService.createComment(bookId, text, from);
            return localizer.getMessage("app.comment.create.success") + "\n" + libraryConverter.toString(comment);
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Update comment. Format: 'uc <comment-id> <text> <from>'", key = {"uc"})
    public String updateComment(@Min(1) Long commentId, @NotBlank String text, @NotBlank String from) {
        try {
            libraryService.updateComment(commentId, text, from);
            return localizer.getMessage("app.comment.update.success");
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
    }

    @ShellMethod(value = "Delete comment. Format: 'dc <comment-id>'", key = {"dc"})
    public String deleteComment(@Min(1) Long commentId) {
        try {
            libraryService.deleteComment(commentId);
            return localizer.getMessage("app.comment.delete.success");
        } catch (AppException ae) {
            return "Error! " + ae.getMessage();
        }
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