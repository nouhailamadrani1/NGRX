package com.example.task_flow.model.entities;

import com.example.task_flow.enums.DemandStatus;
import com.example.task_flow.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token_demandes")
public class TokenDemand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "demand_date")
    private LocalDateTime demandDate;

    @Enumerated(EnumType.STRING)
    private DemandStatus status;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}



