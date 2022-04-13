package ru.otus.spring.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework06.domain.Book;
import ru.otus.spring.homework06.domain.Comment;
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
    private static final String EXISTING_COMMENT_TEXT = "Outstanding masterpiece!";
    private static final String EXISTING_COMMENT_FROM = "Vasil Bykov";
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
        Book actualBook = em.find(Book.class, EXISTING_BOOK_ID_WITHOUT_COMMENTS);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getComments().size()).isEqualTo(0);

        Comment newComment = new Comment(null, NEW_COMMENT_FROM, NEW_COMMENT_TEXT, actualBook);
        commentRepository.save(newComment);
        em.flush();

        Long newCommentId = newComment.getId();
        assertThat(newCommentId).isNotNull();

        Comment actualComment = em.find(Comment.class, newCommentId);
        assertThat(actualComment.getText()).isEqualTo(NEW_COMMENT_TEXT);
        assertThat(actualComment.getFrom()).isEqualTo(NEW_COMMENT_FROM);
        assertThat(actualComment.getBook().getId()).isEqualTo(EXISTING_BOOK_ID_WITHOUT_COMMENTS);
    }

    @DisplayName("Update comment")
    @Test
    void updateCommentTest() {
        Comment actualComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        actualComment.setText(NEW_COMMENT_TEXT);
        actualComment.setFrom(NEW_COMMENT_FROM);
        commentRepository.save(actualComment);
        em.flush();

        actualComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(actualComment.getText()).isEqualTo(NEW_COMMENT_TEXT);
        assertThat(actualComment.getFrom()).isEqualTo(NEW_COMMENT_FROM);
    }

    @DisplayName("Delete comment")
    @Test
    void deleteCommentTest() {
        commentRepository.deleteById(EXISTING_COMMENT_ID);
        em.flush();

        Comment comment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(comment).isNull();
    }
}