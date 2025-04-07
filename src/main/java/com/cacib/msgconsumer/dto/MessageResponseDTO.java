package com.cacib.msgconsumer.dto;


import com.cacib.msgconsumer.entity.Partner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {

    private Long id;

    private String content;

    private LocalDateTime receptionDate;

    private PartnerResponseDTO partner;
}
