package com.ansysan.register_of_characteristics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {

    private long id;

    @NotBlank(message = "title can't be empty")
    private String title;

    @NotBlank(message = "text can't be empty")
    private String text;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private LocalDateTime lastEditDate;

}
