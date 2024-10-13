package com.ansysan.register_of_characteristics.controller;

import com.ansysan.register_of_characteristics.entity.Comment;
import com.ansysan.register_of_characteristics.service.CommentService;
import com.ansysan.register_of_characteristics.dto.CommentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * Возвращент все комментарии
     */
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public Page<Comment> getAllComments(Pageable pageable) {
        return commentService.getAll(pageable);
    }

    /**
     * Возвращает все комментраии под новостью
     */
    @Operation(summary = "Get all comments for a news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of news comments")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/news/{newsId}")
    public List<CommentDto> getNewsComments(@PathVariable("newsId") long newsId) {
        return commentService.getNewsComments(newsId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find")
    public List<Comment> findAllCommentsBy(@RequestParam String word) {
        return commentService.findAllBy(word);
    }

    /**
     * Создаёт новый комментарий
     * @param userId индентификатор пользователя, что написал комментарий.
     *
     * @param newsId индентификатор новости, где был написан комментарий.
     */
    @Operation(summary = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/news/{newsId}")
    public CommentDto createComment(@PathVariable("userId") long userId,
                                    @PathVariable("newsId") long newsId,
                                    @RequestBody @Valid CommentDto commentDto) {
        return commentService.createComment(commentDto, userId, newsId);
    }

    /**
     * Изменяет комментарий.
     *
     * @param userId индентификатор пользователя, что написал комментарий.
     *
     * @param commentId индентификатор комментария, который изменяется.
     */
    @Operation(summary = "Update a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable("userId") long userId,
                                    @PathVariable("commentId") long commentId,
                                    @RequestBody @Valid CommentDto commentDto) {
        return commentService.updateComment(commentDto, userId, commentId);
    }

    /**
     * Удаленние комментария.
     *
     * @param userId индентификатор пользователя, что удалил комментарий.
     *
     * @param newsId индентификатор новости, где был удалён комментарий.
     *
     * @param commentId индентификатор комментария, что был удалён.
     */
    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class))})
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{commentId}/news/{newsId}")
    public CommentDto deleteComment(@PathVariable("userId") long userId,
                                    @PathVariable("commentId") long commentId,
                                    @PathVariable("newsId") long newsId) {
        return commentService.deleteComment(commentId, newsId, userId);
    }
}