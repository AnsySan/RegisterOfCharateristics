package com.ansysan.register_of_characteristics.controller;

import com.ansysan.register_of_characteristics.dto.NewsDto;
import com.ansysan.register_of_characteristics.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Operation(
            summary = "Creating a draft post",
            description = "Exactly one author user or project creates a draft of the post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created"),
            @ApiResponse(responseCode = "500", description = "There is no access to the database of users or projects")
    })
    @PostMapping("/create")
    public NewsDto createNews(@RequestBody @Valid NewsDto postDto) {
        return newsService.createNews(postDto);
    }

    @Operation(summary = "Publishing a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post published"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error when publishing a post")
    })
    @PutMapping("/publish/{postId}")
    public NewsDto publishNews(@PathVariable @Positive(message = "Id must be greater than zero") long postId) {
        return newsService.publishNews(postId);
    }

    @Operation(
            summary = "Update existing post",
            description = "Gets PostDto and updates existing post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error updating the post")
    })
    @PutMapping("/{postId}")
    public NewsDto updateNews(@PathVariable long postId, @RequestBody @Valid NewsDto postDto) {
        return newsService.updateNews(postId, postDto);
    }

    @Operation(summary = "Deleting a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error when deleting a post")
    })
    @DeleteMapping("/{postId}")
    public NewsDto deleteNews(@PathVariable @Positive(message = "Id must be greater than zero") long postId) {
        return newsService.deleteNews(postId);
    }

    @Operation(summary = "Getting a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post received"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error when receiving a post")
    })
    @GetMapping("/{postId}")
    public NewsDto getNewsById(@PathVariable @Positive(message = "Id must be greater than zero") long postId) {
        return newsService.getNewsById(postId);
    }
}
