package com.ansysan.register_of_characteristics.service;

import com.ansysan.register_of_characteristics.dto.NewsDto;
import com.ansysan.register_of_characteristics.entity.Comment;
import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.exception.NotFoundException;
import com.ansysan.register_of_characteristics.mapper.NewsMapper;
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
    private final CommonServiceMethods commonServiceMethods;

    /**
     * Находит комментарий с указанным идентификатором.
     *
     * @param id Идентификатор комментария, который нужно найти.
     */
    public CommentDto findById(long id) {

        Comment news = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s not found", id)));


        return commentMapper.toDto(news);
    }

    /**
     * Создаёт комментарий к новости.
     *
     * @param userId Идентификатор пользователя, которой написал комментарий.
     *
     * @param newsId Индентификатор новости к которой пишется комментарий
     */
    public CommentDto createComment(CommentDto commentDto, long userId, long newsId) {
        News news = commonServiceMethods.findEntityById(newsRepository, newsId, "News");

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setInsertedById(userId);
        comment.setNews(news);

        comment = commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    /**
     * Обновляет комментарий к новости.
     *
     * @param userId Идентификатор пользователя, которой изменил комментарий.
     *
     * @param commentId Индентификатор комментарий  которой изменяется
     */
    public CommentDto updateComment(CommentDto commentDto, long userId, long commentId) {
        Comment commentToUpdate = commonServiceMethods.findEntityById(commentRepository, commentId, "Comment");

        commentMapper.updateDto(commentDto, commentToUpdate);
        commentToUpdate = commentRepository.save(commentToUpdate);

        return commentMapper.toDto(commentToUpdate);
    }

    /**
     * Удаляет комментарий к новости.
     *
     * @param userId Идентификатор пользователя, которой удалил комментарий.
     *
     * @param newsId Индентификатор новости у которой удалили комментарий
     */
    public CommentDto deleteComment(long commentId, long userId, long newsId) {
        Comment comment = commonServiceMethods.findEntityById(commentRepository, commentId, "Comment");

        CommentDto commentToDelete = commentMapper.toDto(comment);

        commentRepository.deleteById(commentId);

        return commentToDelete;
    }

    /**
     * Возвращает комментарии .
     */
    public Page<Comment> getAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        if (comments.isEmpty()) throw new NotFoundException("Comments not found");

        return comments;
    }

    /**
     * Возвращает комментарии к новости
     */
    public List<CommentDto> getNewsComments(long newsId) {
        return commentRepository.findAllByNewsId(newsId).stream()
                .sorted(Comparator.comparing(Comment::getCreatedDate))
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Comment> findAllBy(String word) {
        List<Comment> comments = commentRepository.findAll();
        if (comments.isEmpty()) throw new NotFoundException("Comments not found");

        return comments.stream()
                .filter(obj -> obj.toStringForFind().contains(word))
                .toList();
    }
}
