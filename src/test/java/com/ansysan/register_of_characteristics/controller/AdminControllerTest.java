package com.ansysan.register_of_characteristics.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ansysan.register_of_characteristics.dto.UserDto;
import com.ansysan.register_of_characteristics.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testSetAdmin() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post("/set/admin/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User role Admin"));

        verify(adminService, times(1)).setAdmin(userId);
    }

    @Test
    public void testSetJournalist() throws Exception {
        Long userId = 2L;

        mockMvc.perform(post("/set/journalist/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User role Admin"));

        verify(adminService, times(1)).setJournalist(userId);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDto> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserDto()); // Заполните данными UserDto

        when(adminService.getAllUsers()).thenReturn(expectedUsers);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(adminService, times(1)).getAllUsers();
    }
}
