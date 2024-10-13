package com.ansysan.register_of_characteristics.controller;

import com.ansysan.register_of_characteristics.dto.UserDto;
import com.ansysan.register_of_characteristics.entity.MessageResponse;
import com.ansysan.register_of_characteristics.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * Назначает роль администратора пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль администратора.
     * @return Ответ с сообщением об успешном изменении роли пользователя.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set/admin/{id}")
    @Operation(
            summary = "Set the administrator role for the user",
            description = "Update the user role by specifying its id. The response is a message about the successful changed a role",
            tags = "post"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")})
    public ResponseEntity<?> setAdmin(@PathVariable("id") @Min(1) Long id) {
        adminService.setAdmin(id);
        return ResponseEntity.ok(new MessageResponse("User role Admin"));
    }

    /**
     * Назначает роль журналиста пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль журналиста.
     * @return Ответ с сообщением об успешном изменении роли пользователя.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set/journalist/{id}")
    @Operation(
            summary = "Set the journalist role for the user",
            description = "Update the user role by specifying its id. The response is a message about the successful changed a role",
            tags = "post"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")})
    public ResponseEntity<?> setJournalist(@PathVariable("id") @Min(1) Long id) {
        adminService.setJournalist(id);
        return ResponseEntity.ok(new MessageResponse("User role Admin"));
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Ответ со списком объектов {@link UserDto}, представляющих пользователей.
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Retrieve all users",
            description = "Collect all users. The answer is an array of users with identifier, username and role for each of the array element",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")})
    public List<UserDto> getAllUsers() {
        return adminService.getAllUsers();
    }
}
