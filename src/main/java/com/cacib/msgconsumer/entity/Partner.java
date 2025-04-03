package com.cacib.msgconsumer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String direction; // INBOUND ou OUTBOUND

    private String application;

    @Column(name = "processed_flow_type")
    private String processedFlowType; // MESSAGE, ALERTING, NOTIFICATION

    @Column(nullable = false)
    private String description;
}
