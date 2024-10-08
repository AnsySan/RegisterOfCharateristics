package com.ansysan.register_of_characteristics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    @NotBlank(message = "text can't be empty")
    private String text;

    @NonNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime lastEditDate;

}
