package com.cacib.msgconsumer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDTO {

    @NotBlank(message = "content is required")
    private String content;

    @NotBlank(message = "origin is required")
    private String origin;
}
