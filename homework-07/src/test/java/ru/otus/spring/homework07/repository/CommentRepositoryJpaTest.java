package ru.otus.spring.homework07.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.homework07.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Comment Repository Test Suite")
@DataJpaTest
class CommentRepositoryJpaTest {
    private static final int COMMENTS_COUNT = 2;

    private static final long EXISTING_BOOK_ID_WITH_COMMENTS = 1;
    private static final long EXISTING_BOOK_ID_WITHOUT_COMMENTS = 2;

    @Autowired
    private CommentRepository commentRepository;


    @DisplayName("Get all comments for specified book")
    @Test
    void getAllCommentsForSpecifiedBook() {
        List<Comment> actualComments = commentRepository.findByBookId(EXISTING_BOOK_ID_WITH_COMMENTS);
        assertThat(actualComments).hasSize(COMMENTS_COUNT);

        actualComments = commentRepository.findByBookId(EXISTING_BOOK_ID_WITHOUT_COMMENTS);
        assertThat(actualComments).hasSize(0);
    }
}