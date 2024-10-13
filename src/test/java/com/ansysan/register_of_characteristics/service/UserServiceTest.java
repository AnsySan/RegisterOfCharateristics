package com.ansysan.register_of_characteristics.service;

import com.ansysan.register_of_characteristics.dto.UserDto;
import com.ansysan.register_of_characteristics.entity.Role;
import com.ansysan.register_of_characteristics.entity.User;
import com.ansysan.register_of_characteristics.exception.DataValidationException;
import com.ansysan.register_of_characteristics.exception.EntityNotFoundException;
import com.ansysan.register_of_characteristics.mapper.UserMapper;
import com.ansysan.register_of_characteristics.repository.RoleRepository;
import com.ansysan.register_of_characteristics.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Nested
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        user = new User(user);
        user.setUsername("testuser");
        userDto = new UserDto();
        userDto.setUsername("testuser");
    }

    @Test
    public void testGetUserByUsername_UserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testGetUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUsername("testuser"));
    }

    @Test
    public void testSave_UserRegistrationSuccessful() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(roleRepository.findByName(Role.SUBSCRIBER.name())).thenReturn(Optional.of(Role.SUBSCRIBER));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto savedUserDto = userService.save(userDto);

        assertNotNull(savedUserDto);
        assertEquals("testuser", savedUserDto.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testSave_RoleNotFound() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(roleRepository.findByName(Role.SUBSCRIBER.name())).thenReturn(Optional.empty());

        assertThrows(DataValidationException.class, () -> userService.save(userDto));
    }

    @Test
    public void testIsUserExist_UserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        boolean exists = userService.isUserExist("testuser");

        assertTrue(exists);
    }

    @Test
    public void testIsUserExist_UserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        boolean exists = userService.isUserExist("testuser");

        assertFalse(exists);
    }

    @Test
    public void testSetRoleAdmin_UserExistsAndRoleExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(Role.ADMIN.name())).thenReturn(Optional.of(Role.ADMIN));

        userService.setRoleAdmin(1L);

        verify(userRepository).save(user);
        assertTrue(user.getRoles().contains(Role.ADMIN));
    }

    @Test
    public void testSetRoleAdmin_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.setRoleAdmin(1L));
    }

    @Test
    public void testSetRoleAdmin_RoleNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(Role.ADMIN.name())).thenReturn(Optional.empty());

        assertThrows(DataValidationException.class, () -> userService.setRoleAdmin(1L));
    }

    @Test
    public void testSetRoleJournalist_UserExistsAndRoleExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(Role.JOURNALIST.name())).thenReturn(Optional.of(Role.JOURNALIST));

        userService.setRoleJournalist(1L);

        verify(userRepository).save(user);
        assertTrue(user.getRoles().contains(Role.JOURNALIST));
    }

    @Test
    public void testSetRoleJournalist_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.setRoleJournalist(1L));
    }

    @Test
    public void testSetRoleJournalist_RoleNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(Role.JOURNALIST.name())).thenReturn(Optional.empty());

        assertThrows(DataValidationException.class, () -> userService.setRoleJournalist(1L));
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }
}
