package com.ansysan.register_of_characteristics.service;

import com.ansysan.register_of_characteristics.dto.NewsDto;
import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.mapper.NewsMapper;
import com.ansysan.register_of_characteristics.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @Spy
    private NewsMapper postMapper = Mappers.getMapper(NewsMapper.class);
    @InjectMocks
    private NewsService newsService;

    @Test
    void successCreate() {
        NewsDto expectedResult = NewsDto.builder()
                .build();
        News news = postMapper.toEntity(expectedResult);

        when(newsRepository.save(news)).thenReturn(news);

        NewsDto result = newsService.createNews(expectedResult);
        assertEquals(expectedResult, result);
    }

    @Test
    void successPublish() {
        News post = News.builder()
                .id(1L)
                .build();

        when(newsRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(newsRepository.save(post)).thenReturn(post);

        NewsDto result = newsService.publishNews(1L);
    }

    @Test
    void successUpdate() {
        News post = News.builder()
                .id(1L)
                .build();
        when(newsRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(newsRepository.save(post)).thenReturn(post);

        NewsDto result = newsService.publishNews(1L);
    }

    @Test
    void successDeleteById() {
        News news = News.builder()
                .id(1L)
                .build();
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));

        NewsDto result =  newsService.deleteNews(1L);

    }
}