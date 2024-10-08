package com.ansysan.register_of_characteristics.service;


import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.exception.DataValidationException;
import com.ansysan.register_of_characteristics.exception.NotFoundException;
import com.ansysan.register_of_characteristics.mapper.NewsMapper;
import com.ansysan.register_of_characteristics.repository.NewsRepository;
import com.ansysan.register_of_characteristics.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public NewsDto findById(long id) {

        News news =  newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("News with id %s not found", id)));


        return newsMapper.toDto(news);
    }

    @Transactional
    public NewsDto createNews(NewsDto postDto) {
        News post = newsMapper.toEntity(postDto);
        post = newsRepository.save(post);
        return newsMapper.toDto(post);
    }

    @Transactional
    public NewsDto publishNews(long postId) {
        News post = existsNews(postId);
        post.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        newsRepository.save(post);
        return newsMapper.toDto(post);
    }

    @Transactional
    public NewsDto updateNews(long postId, NewsDto postDto) {
        News post = existsNews(postId);
        post.setLastEditDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        post = newsRepository.save(post);
        return newsMapper.toDto(post);
    }

    @Transactional
    public NewsDto deleteNews(long postId) {
        News post = existsNews(postId);
        return newsMapper.toDto(post);
    }

    public Page<NewsDto> findAllNews(Pageable pageable) {
        Page<NewsDto> news = newsRepository.findAll(pageable);
        if(news.isEmpty()) throw new NotFoundException("News not found");

        return news;
    }

    @Transactional(readOnly = true)
    public NewsDto getNewsById(long postId) {
        return newsMapper.toDto(existsNews(postId));
    }

    private News existsNews(Long postId) {
        return newsRepository.findById(postId)
                .orElseThrow(() -> new DataValidationException("Post with ID " + postId + " not found"));
    }
}
