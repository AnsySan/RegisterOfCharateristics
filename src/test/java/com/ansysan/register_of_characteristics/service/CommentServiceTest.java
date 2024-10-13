package com.ansysan.register_of_characteristics.service;


import com.ansysan.register_of_characteristics.dto.CommentDto;
import com.ansysan.register_of_characteristics.entity.Comment;
import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.mapper.CommentMapper;
import com.ansysan.register_of_characteristics.repository.CommentRepository;
import com.ansysan.register_of_characteristics.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentMapper commentMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CommonServiceMethods commonServiceMethods;
    @InjectMocks
    private CommentService commentService;

    @Test
    void createComment_success() {
        long postId = 1L;
        long userId = 1L;
        CommentDto commentDto = new CommentDto();
        News post = new News();
        Comment comment = new Comment();

        when(commonServiceMethods.findEntityById(newsRepository , postId, "News")).thenReturn(post);
        when(commentMapper.toEntity(commentDto)).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.createComment(commentDto, userId, postId);

        verify(commonServiceMethods, times(1)).findEntityById(newsRepository, postId, "News");
        verify(commentMapper, times(1)).toEntity(commentDto);
        verify(commentRepository, times(1)).save(comment);

        assertEquals(commentDto, result);
    }

    @Test
    void getAllPostComments_success() {
        long postId = 1L;
        Comment comment1 = new Comment();
        comment1.setCreatedDate(LocalDateTime.now().minusDays(1));
        Comment comment2 = new Comment();
        comment2.setCreatedDate(LocalDateTime.now());
        List<Comment> comments = Arrays.asList(comment1, comment2);
        CommentDto commentDto1 = new CommentDto();
        CommentDto commentDto2 = new CommentDto();

        when(commentRepository.findAllByNewsId(postId)).thenReturn(comments);
        when(commentMapper.toDto(comment1)).thenReturn(commentDto1);
        when(commentMapper.toDto(comment2)).thenReturn(commentDto2);

        List<CommentDto> result = commentService.getNewsComments(postId);

        verify(commentRepository, times(1)).findAllByNewsId(postId);
        verify(commentMapper, times(1)).toDto(comment1);
        verify(commentMapper, times(1)).toDto(comment2);

        assertEquals(Arrays.asList(commentDto1, commentDto2), result);
    }

    @Test
    void updateComment_success() {
        long commentId = 1L;
        long userId = 1L;
        CommentDto updatedCommentDto = new CommentDto();
        CommentDto commentDto = new CommentDto();
        Comment comment = new Comment();
        News post = new News();
        comment.setNews(post);

        when(commonServiceMethods.findEntityById(commentRepository, commentId, "Comment")).thenReturn(comment);
        doNothing().when(commentMapper).updateDto(updatedCommentDto, comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.updateComment(commentDto, userId, commentId);

        verify(commonServiceMethods, times(1)).findEntityById(commentRepository, commentId, "Comment");
        verify(commentMapper, times(1)).updateDto(updatedCommentDto, comment);
        verify(commentRepository, times(1)).save(comment);
        verify(commentMapper, times(1)).toDto(comment);

        assertEquals(commentDto, result);
    }

    @Test
    void deleteComment_success() {
        long postId = 1L;
        long commentId = 1L;
        long userId = 1L;
        Comment comment = new Comment();
        News post = new News();
        comment.setNews(post);
        CommentDto commentDto = new CommentDto();

        when(commonServiceMethods.findEntityById(commentRepository, commentId, "Comment")).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentDto);

        CommentDto result = commentService.deleteComment(postId, commentId, userId);

        verify(commonServiceMethods, times(1)).findEntityById(commentRepository, commentId, "Comment");
        verify(commentRepository, times(1)).deleteById(commentId);
        verify(commentMapper, times(1)).toDto(comment);

        assertEquals(commentDto, result);
    }

}