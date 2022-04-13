package ru.otus.spring.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework06.domain.Author;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Comment;
import ru.otus.spring.homework06.repository.impl.AuthorRepositoryJpa;
import ru.otus.spring.homework06.repository.impl.CommentRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Comment Repository Test Suite")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {
    private static final int COMMENTS_COUNT = 2;

    private static final long EXISTING_BOOK_ID_WITH_COMMENTS = 1;
    private static final long EXISTING_BOOK_ID_WITHOUT_COMMENTS = 2;

    private static final long EXISTING_COMMENT_ID = 1;
    private static final String EXISTING_COMMENT_TEXT = "Это потрясающее прозведение!";
    private static final String EXISTING_COMMENT_FROM = "Василь Быков";
    private static final String NEW_COMMENT_TEXT = "New comment text.";
    private static final String NEW_COMMENT_FROM = "Sporadic visitor";

    @Autowired
    private CommentRepositoryJpa commentRepository;

    @Autowired
    private TestEntityManager em;


    @DisplayName("Get all comments for specified book")
    @Test
    void getAllCommentsForSpecifiedBook() {
        List<Comment> actualComments = commentRepository.findAllInBook(EXISTING_BOOK_ID_WITH_COMMENTS);
        assertThat(actualComments).hasSize(COMMENTS_COUNT);

        actualComments = commentRepository.findAllInBook(EXISTING_BOOK_ID_WITHOUT_COMMENTS);
        assertThat(actualComments).hasSize(0);
    }

    @DisplayName("Get comment by id")
    @Test
    void getCommentByIdTest() {
        Optional<Comment> actualComment = commentRepository.findById(EXISTING_COMMENT_ID);
        assertThat(actualComment).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("text", EXISTING_COMMENT_TEXT)
                .hasFieldOrPropertyWithValue("from", EXISTING_COMMENT_FROM);
    }

    @DisplayName("Create comment")
    @Test
    void createCommentTest() {
        Book book = em.find(Book.class, EXISTING_BOOK_ID_WITHOUT_COMMENTS);
        assertThat(book).isNotNull();
        assertThat(book.getComments().size()).isEqualTo(0);

        Comment comment = new Comment(null, NEW_COMMENT_FROM, NEW_COMMENT_TEXT, book);
        commentRepository.save(comment);

        em.clear();

        book = em.find(Book.class, EXISTING_BOOK_ID_WITHOUT_COMMENTS);
        assertThat(book.getComments().size()).isEqualTo(1);
        comment = book.getComments().iterator().next();
        assertThat(comment.getText()).isEqualTo(NEW_COMMENT_TEXT);
        assertThat(comment.getFrom()).isEqualTo(NEW_COMMENT_FROM);
    }

    @DisplayName("Update comment")
    @Test
    void updateCommentTest() {
        Comment actualComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        actualComment.setText(NEW_COMMENT_TEXT);
        actualComment.setFrom(NEW_COMMENT_FROM);
        em.detach(actualComment);

        commentRepository.save(actualComment);

        actualComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(actualComment.getText()).isEqualTo(NEW_COMMENT_TEXT);
        assertThat(actualComment.getFrom()).isEqualTo(NEW_COMMENT_FROM);
    }

    @DisplayName("Delete comment")
    @Test
    void deleteCommentTest() {
        commentRepository.deleteById(EXISTING_COMMENT_ID);

        em.clear();

        Comment comment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(comment).isNull();
    }
}