package com.ansysan.register_of_characteristics.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ansysan.register_of_characteristics.dto.NewsDto;
import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class NewsControllerTest {

    @InjectMocks
    private NewsController newsController;

    @Mock
    private NewsService newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getNewsById_ShouldReturnNewsDto_WhenNewsExists() {
        long newsId = 1L;
        NewsDto newsDto = new NewsDto(); // Заполните данными
        when(newsService.getNewsById(newsId)).thenReturn(newsDto);

        NewsDto result = newsController.getNewsById(newsId);

        assertNotNull(result);
        verify(newsService, times(1)).getNewsById(newsId);
    }

    @Test
    public void getAllNews_ShouldReturnPageOfNews() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<News> newsPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(newsService.findAllNews(pageable)).thenReturn(newsPage);

        Page<News> result = newsController.getAllNews(pageable);

        assertNotNull(result);
        verify(newsService, times(1)).findAllNews(pageable);
    }

    @Test
    public void findAllNewsBy_ShouldReturnListOfNews_WhenFound() {
        String word = "test";
        List<News> newsList = Collections.singletonList(new News()); // Заполните данными
        when(newsService.findAllBy(word)).thenReturn(newsList);

        List<News> result = newsController.findAllNewsBy(word);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(newsService, times(1)).findAllBy(word);
    }

    @Test
    public void createNews_ShouldReturnCreatedNewsDto() {
        NewsDto newsDto = new NewsDto(); // Заполните данными
        when(newsService.createNews(any())).thenReturn(newsDto);

        NewsDto result = newsController.createNews(newsDto);

        assertNotNull(result);
        verify(newsService, times(1)).createNews(any());
    }

    @Test
    public void publishNews_ShouldReturnPublishedNewsDto_WhenPublished() {
        long newsId = 1L;
        NewsDto newsDto = new NewsDto(); // Заполните данными
        when(newsService.publishNews(newsId)).thenReturn(newsDto);

        NewsDto result = newsController.publishNews(newsId);

        assertNotNull(result);
        verify(newsService, times(1)).publishNews(newsId);
    }

    @Test
    public void updateNews_ShouldReturnUpdatedNewsDto_WhenUpdated() {
        long newsId = 1L;
        NewsDto newsDto = new NewsDto(); // Заполните данными
        when(newsService.updateNews(eq(newsId), any())).thenReturn(newsDto);

        NewsDto result = newsController.updateNews(newsId, newsDto);

        assertNotNull(result);
        verify(newsService, times(1)).updateNews(eq(newsId), any());
    }

    @Test
    public void deleteNews_ShouldReturnDeletedNewsDto_WhenDeleted() {
        long newsId = 1L;
        NewsDto newsDto = new NewsDto(); // Заполните данными
        when(newsService.deleteNews(newsId)).thenReturn(newsDto);

        NewsDto result = newsController.deleteNews(newsId);

        assertNotNull(result);
        verify(newsService, times(1)).deleteNews(newsId);
    }
}
