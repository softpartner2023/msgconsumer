package com.cacib.msgconsumer.dto;

import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestDTO {
    @NotBlank(message = "Alias is required")
    private String alias;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Direction is required")
    private Direction direction;

    private String application;

    private ProcessedFlowType processedFlowType;

    @NotBlank(message = "Description is required")
    private String description;
}
