package com.ansysan.register_of_characteristics.mapper;

import com.ansysan.register_of_characteristics.entity.Comment;
import com.ansysan.register_of_characteristics.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "news.id", target = "newsId")
    CommentDto toDto (Comment comment);

    @Mapping(target = "news", ignore = true)
    Comment toEntity (CommentDto commentDto);

    @Mapping(target = "id", ignore = true)
    void updateDto (CommentDto commentDto, @MappingTarget Comment comment);
}
