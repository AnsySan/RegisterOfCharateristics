package com.ansysan.register_of_characteristics.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ansysan.register_of_characteristics.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetAdmin() {
        Long userId = 1L;

        adminService.setAdmin(userId);

        verify(userService, times(1)).setRoleAdmin(userId);
    }

    @Test
    public void testSetJournalist() {
        Long userId = 2L;

        adminService.setJournalist(userId);

        verify(userService, times(1)).setRoleJournalist(userId);
    }

    @Test
    public void testGetAllUsers() {
        List<UserDto> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserDto());

        when(userService.getAllUsers()).thenReturn(expectedUsers);

        List<UserDto> actualUsers = adminService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
        verify(userService, times(1)).getAllUsers();
    }
}
