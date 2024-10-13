package com.ansysan.register_of_characteristics.mapper;

import com.ansysan.register_of_characteristics.entity.Comment;
import com.ansysan.register_of_characteristics.dto.CommentDto;
import com.ansysan.register_of_characteristics.entity.News;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "news", target = "idNews")
    CommentDto toDto (Comment comment);

    @Mapping(target = "news", ignore = true)
    Comment toEntity (CommentDto commentDto);

    @Mapping(target = "id", ignore = true)
    void updateDto (CommentDto commentDto, @MappingTarget Comment comment);

    default Long map(News news) {
        return news != null ? news.getId() : null;
    }
}
