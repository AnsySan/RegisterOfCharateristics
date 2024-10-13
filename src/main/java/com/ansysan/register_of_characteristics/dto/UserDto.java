package com.ansysan.register_of_characteristics.dto;

import com.ansysan.register_of_characteristics.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private long id;

    private String surname;

    private String name;

    private String parentName;

    private String username;

    private String password;

    private Role role;

}
