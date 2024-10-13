package com.ansysan.register_of_characteristics.controller;

import com.ansysan.register_of_characteristics.dto.NewsDto;
import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    /**
     * Возвращает новость по индентификатору
     *
     * @param newsId индентификатор новости
     */
    @Operation(summary = "Getting a news by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post received"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Error when receiving a post")
    })
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{newsId}")
    public NewsDto getNewsById(@PathVariable @Positive(message = "Id must be greater than zero") long newsId) {
        return newsService.getNewsById(newsId);
    }

    /**
     * Возвращает все новости
     */
    @GetMapping
    public Page<News> getAllNews(Pageable pageable) {
        return newsService.findAllNews(pageable);
    }

    @Operation(
            summary = "Find a news"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News Find"),
            @ApiResponse(responseCode = "404", description = "News not found")
    })
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find")
    public List<News> findAllNewsBy(@RequestParam String word) {
        return newsService.findAllBy(word);
    }

    /**
     * Создаёт новцю новость
     */
    @Operation(
            summary = "Creating a draft news",
            description = "Exactly one author user or project creates a draft of the news"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News created"),
            @ApiResponse(responseCode = "500", description = "There is no access to the database of users or projects")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public NewsDto createNews(@RequestBody @Valid NewsDto newsDto) {
        return newsService.createNews(newsDto);
    }

    /**
     * Публикует новость по индентификатору.
     *
     * @param newsId индентификатор новости для публикации.
     */
    @Operation(summary = "Publishing a news by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News published"),
            @ApiResponse(responseCode = "404", description = "News not found"),
            @ApiResponse(responseCode = "500", description = "Error when publishing a news")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/publish/{newsId}")
    public NewsDto publishNews(@PathVariable @Positive(message = "Id must be greater than zero") long newsId) {
        return newsService.publishNews(newsId);
    }

    /**
     * Изменение новости.
     *
     * @param newsId индентификатор новости, что изменяется.
     */
    @Operation(
            summary = "Update existing news",
            description = "Gets NewsDto and updates existing news"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News updated"),
            @ApiResponse(responseCode = "404", description = "News not found"),
            @ApiResponse(responseCode = "500", description = "Error updating the news")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{newsId}")
    public NewsDto updateNews(@PathVariable long newsId, @RequestBody @Valid NewsDto newsDto) {
        return newsService.updateNews(newsId, newsDto);
    }

    /**
     * Удаление новости по индентификатору.
     *
     * @param newsId индентификатор новости, что удаляется
     */
    @Operation(summary = "Deleting a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News deleted"),
            @ApiResponse(responseCode = "404", description = "News not found"),
            @ApiResponse(responseCode = "500", description = "Error when deleting a news")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{newsId}")
    public NewsDto deleteNews(@PathVariable @Positive(message = "Id must be greater than zero") long newsId) {
        return newsService.deleteNews(newsId);
    }

}
