package com.ansysan.register_of_characteristics.service;

import com.ansysan.register_of_characteristics.dto.UserDto;
import com.ansysan.register_of_characteristics.entity.Role;
import com.ansysan.register_of_characteristics.entity.User;
import com.ansysan.register_of_characteristics.exception.DataValidationException;
import com.ansysan.register_of_characteristics.exception.EntityNotFoundException;
import com.ansysan.register_of_characteristics.mapper.UserMapper;
import com.ansysan.register_of_characteristics.repository.RoleRepository;
import com.ansysan.register_of_characteristics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    /**
     * Получает пользователя по его имени пользователя.
     *
     * @param username Имя пользователя для поиска.
     * @return Пользователь с указанным именем пользователя или {@code null}, если пользователь не найден.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    /**
     * Сохраняет данные о регистрации пользователя.
     *
     * @param userRegistrationDto Данные о регистрации пользователя для сохранения.
     * @return
     */
    public UserDto save(UserDto userRegistrationDto) {
        User user = userMapper.toEntity(userRegistrationDto);
        Optional<Role> optionalRole = roleRepository.findByName("SUBSCRIBER");
        if (optionalRole.isPresent()) {
            user.setRoles(optionalRole.get());
            userRepository.save(user);
        } else {
            throw new DataValidationException("Data not found");
        }
        return userRegistrationDto;
    }

    /**
     * Проверяет, существует ли пользователь с указанным именем пользователя.
     *
     * @param username Имя пользователя для проверки.
     * @return {@code true}, если пользователь существует, в противном случае {@code false}.
     */
    public boolean isUserExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Назначает роль администратора пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль администратора.
     */
    public void setRoleAdmin(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Optional<Role> optionalRole = roleRepository.findByName("ADMIN");
            if (optionalRole.isPresent()) {
                User user = userOptional.get();;
                Role role = optionalRole.get();
                user.setRoles(role);
            } else {
                throw new DataValidationException("Data not found");
            }
        } else {
            throw new EntityNotFoundException("Entity not found");
        }
    }

    /**
     * Назначает роль журналиста пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль журналиста.
     */
    public void setRoleJournalist(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Optional<Role> optionalRole = roleRepository.findByName("JOURNALIST");
            if (optionalRole.isPresent()) {
                User user = userOptional.get();
                Role role = optionalRole.get();
                user.setRoles(role);
                userRepository.save(user);
            } else {
                throw new DataValidationException("Data not found");
            }
        } else {
            throw new EntityNotFoundException("Entity not found");
        }
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список объектов {@link UserDto}, представляющих пользователей.
     */
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }
}