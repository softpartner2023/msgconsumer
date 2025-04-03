package com.cacib.msgconsumer.dto;


import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerResponseDTO {
    private Long id;
    private String alias;
    private String type;
    private Direction direction;
    private String application;
    private ProcessedFlowType processedFlowType;
    private String description;
}
