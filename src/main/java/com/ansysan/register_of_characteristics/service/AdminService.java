package com.ansysan.register_of_characteristics.service;

import com.ansysan.register_of_characteristics.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService  {

    private final UserService userService;

    /**
     * Назначает роль администратора пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль администратора.
     */
    public void setAdmin(Long id) {
        userService.setRoleAdmin(id);
    }

    /**
     * Назначает роль журналиста пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль журналиста.
     */
    public void setJournalist(Long id) {
        userService.setRoleJournalist(id);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список объектов {@link UserDto}, представляющих пользователей.
     */
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}