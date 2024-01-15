package com.example.task_flow.repository;

import com.example.task_flow.enums.DemandStatus;
import com.example.task_flow.enums.TokenType;
import com.example.task_flow.model.entities.Task;
import com.example.task_flow.model.entities.TokenDemand;
import com.example.task_flow.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TokenDemandRepository extends JpaRepository<TokenDemand, Long> {
    @Query("SELECT COUNT(td) FROM TokenDemand td WHERE td.user.id = :userId " +
            "AND td.type = :deletionType AND EXTRACT(MONTH FROM td.demandDate) = EXTRACT(MONTH FROM :currentDate)")
    int countMonthlyDeletionTokens(
            @Param("userId") Long userId,
            @Param("deletionType") TokenType deletionType,
            @Param("currentDate") LocalDate currentDate
    );

    @Query("SELECT COUNT(td) FROM TokenDemand td WHERE td.user.id = :userId " +
            "AND td.type = :modificationType AND EXTRACT(MONTH FROM td.demandDate) = EXTRACT(MONTH FROM :currentDate)")
    int countMonthlyModificationTokens(
            @Param("userId") Long userId,
            @Param("modificationType") TokenType modificationType,
            @Param("currentDate") LocalDate currentDate
    );

    boolean existsByTask(Task task);
    boolean existsByTaskAndStatus(Task task, DemandStatus status);
    @Query("SELECT td FROM TokenDemand td WHERE td.status = 'PENDING'")
    List<TokenDemand> findPendingTokenRequests();

}
