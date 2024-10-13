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
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    /**
     * Находит новость с указанным идентификатором.
     *
     * @param id Идентификатор новости, которую нужно найти.
     */
    public NewsDto findById(long id) {

        News news =  newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("News with id %s not found", id)));


        return newsMapper.toDto(news);
    }

    /**
     * Создаёт новость.
     */
    @Transactional
    public NewsDto createNews(NewsDto postDto) {
        News post = newsMapper.toEntity(postDto);
        post = newsRepository.save(post);
        return newsMapper.toDto(post);
    }

    /**
     * Публикует новость.
     *
     * @param newsId индентификатор новости, которую нужно опубликовать.
     */
    @Transactional
    public NewsDto publishNews(long newsId) {
        News post = existsNews(newsId);
        post.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        newsRepository.save(post);
        return newsMapper.toDto(post);
    }

    /**
     * Обновление новости.
     *
     * @param newsId индентификатор новости, которую нужно обновить.
     */
    @Transactional
    public NewsDto updateNews(long newsId, NewsDto postDto) {
        News post = existsNews(newsId);
        post.setLastEditDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        post = newsRepository.save(post);
        return newsMapper.toDto(post);
    }

    /**
     * Удаление новости.
     *
     * @param newsId индентификатор новости, которую нужно удалить.
     */
    @Transactional
    public NewsDto deleteNews(long newsId) {
        News post = existsNews(newsId);
        return newsMapper.toDto(post);
    }

    /**
     * Возвращает все комментарии .
     */
    public Page<News> findAllNews(Pageable pageable) {
        Page<News> news = newsRepository.findAll(pageable);
        if(news.isEmpty()) throw new NotFoundException("News not found");

        return news;
    }

    /**
     * Возвращает новость по индентификатору
     */
    @Transactional(readOnly = true)
    public NewsDto getNewsById(long postId) {
        return newsMapper.toDto(existsNews(postId));
    }

    private News existsNews(Long postId) {
        return newsRepository.findById(postId)
                .orElseThrow(() -> new DataValidationException("Post with ID " + postId + " not found"));
    }

    public List<News> findAllBy(String word) {
        List<News> news = newsRepository.findAll();
        if(news.isEmpty()) throw new NotFoundException("News not found");

        return news.stream()
                .filter(obj -> obj.toStringForFind().contains(word))
                .toList();
    }
}
