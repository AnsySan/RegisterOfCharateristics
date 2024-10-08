package com.ansysan.register_of_characteristics.mapper;

import com.ansysan.register_of_characteristics.entity.News;
import com.ansysan.register_of_characteristics.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    @Mapping(target = "comments")
    News toEntity(NewsDto newsDto);

    NewsDto toDto(News news);
}
