package com.ansysan.register_of_characteristics.mapper;

import com.ansysan.register_of_characteristics.dto.UserDto;
import com.ansysan.register_of_characteristics.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", source = "roles")
    UserDto toDto(User user);


    User toEntity(UserDto userDto);

    }
