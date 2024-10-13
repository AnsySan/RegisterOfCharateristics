package com.ansysan.register_of_characteristics.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ansysan.register_of_characteristics.dto.CommentDto;
import com.ansysan.register_of_characteristics.entity.Comment;
import com.ansysan.register_of_characteristics.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllComments_ShouldReturnPageOfComments() {

        Pageable pageable = Pageable.unpaged();
        Page<Comment> commentPage = mock(Page.class);
        when(commentService.getAll(pageable)).thenReturn(commentPage);

        Page<Comment> result = commentController.getAllComments(pageable);

        assertNotNull(result);
        verify(commentService, times(1)).getAll(pageable);
    }

    @Test
    public void getNewsComments_ShouldReturnListOfComments_WhenFound() {
        long postId = 1L;
        List<CommentDto> comments = Collections.singletonList(new CommentDto()); // Заполните данными
        when(commentService.getNewsComments(postId)).thenReturn(comments);

        List<CommentDto> result = commentController.getNewsComments(postId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commentService, times(1)).getNewsComments(postId);
    }

    @Test
    public void findAllCommentsBy_ShouldReturnListOfComments_WhenFound() {
        String word = "test";
        List<Comment> comments = Collections.singletonList(new Comment()); // Заполните данными
        when(commentService.findAllBy(word)).thenReturn(comments);

        List<Comment> result = commentController.findAllCommentsBy(word);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commentService, times(1)).findAllBy(word);
    }

    @Test
    public void createComment_ShouldReturnCreatedCommentDto() {
        long userId = 1L;
        long postId = 1L;
        CommentDto commentDto = new CommentDto(); // Заполните данными
        when(commentService.createComment(any(), eq(userId), eq(postId))).thenReturn(commentDto);

        CommentDto result = commentController.createComment(userId, postId, commentDto);

        assertNotNull(result);
        verify(commentService, times(1)).createComment(any(), eq(userId), eq(postId));
    }

    @Test
    public void updateComment_ShouldReturnUpdatedCommentDto() {
        long userId = 1L;
        long commentId = 1L;
        CommentDto commentDto = new CommentDto(); // Заполните данными
        when(commentService.updateComment(any(), eq(userId), eq(commentId))).thenReturn(commentDto);

        CommentDto result = commentController.updateComment(userId, commentId, commentDto);

        assertNotNull(result);
        verify(commentService, times(1)).updateComment(any(), eq(userId), eq(commentId));
    }

    @Test
    public void deleteComment_ShouldReturnDeletedCommentDto() {
        long userId = 1L;
        long commentId = 1L;
        long postId = 1L;
        CommentDto commentDto = new CommentDto(); // Заполните данными
        when(commentService.deleteComment(eq(commentId), eq(postId), eq(userId))).thenReturn(commentDto);

        CommentDto result = commentController.deleteComment(userId, commentId, postId);

        assertNotNull(result);
        verify(commentService, times(1)).deleteComment(eq(commentId), eq(postId), eq(userId));
    }
}
