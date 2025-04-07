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
public class MessageRequestDTO {

    @NotBlank(message = "content is required")
    private String content;

    @NotBlank(message = "Partner alias is required")
    private String partnerAlias;

}
