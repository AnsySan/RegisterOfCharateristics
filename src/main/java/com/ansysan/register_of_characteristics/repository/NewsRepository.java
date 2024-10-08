package com.ansysan.register_of_characteristics.repository;

import com.ansysan.register_of_characteristics.dto.NewsDto;
import com.ansysan.register_of_characteristics.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    Page<NewsDto> findAll(Pageable pageable);
}
