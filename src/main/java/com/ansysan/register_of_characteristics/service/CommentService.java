package com.ansysan.register_of_characteristics.service;

import com.ansysan.register_of_characteristics.dto.NewsDto;
import com.ansysan.register_of_characteristics.entity.Comment;
import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.exception.NotFoundException;
import com.ansysan.register_of_characteristics.repository.CommentRepository;
import com.ansysan.register_of_characteristics.repository.NewsRepository;
import com.ansysan.register_of_characteristics.dto.CommentDto;
import com.ansysan.register_of_characteristics.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsRepository newsRepository;

    public CommentDto findById(long id) {

        Comment news =  commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s not found", id)));


        return commentMapper.toDto(news);
    }

    public CommentDto createComment(CommentDto commentDto, long userId, long newsId) {
        News news = newsRepository.findById(newsId).orElse(null);

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setInsertedById(userId);
        comment.setIdNews(news);

        comment = commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public CommentDto updateComment(CommentDto commentDto, long userId, long newsId) {
        Comment commentToUpdate = commentRepository.findById(newsId).orElse(null);

        commentMapper.updateDto(commentDto, commentToUpdate);
        commentToUpdate = commentRepository.save(commentToUpdate);

        return commentMapper.toDto(commentToUpdate);
    }

    public CommentDto deleteComment(long commentId, long userId, long newsId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        CommentDto commentToDelete = commentMapper.toDto(comment);

        commentRepository.deleteById(commentId);

        return commentToDelete;
    }

    public Page<Comment> getAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        if(comments.isEmpty()) throw new NotFoundException("Comments not found");

        return comments;
    }

    public List<CommentDto> getNewsComments(long newsId) {
        return commentRepository.findAllByNewsId(newsId).stream()
                .sorted(Comparator.comparing(Comment::getCreatedDate))
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
}
    }
