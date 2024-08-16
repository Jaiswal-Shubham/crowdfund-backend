package com.jaisshu.crowdfund.dto;

import com.jaisshu.crowdfund.entity.ProjectStatus;
import com.jaisshu.crowdfund.service.ProjectService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private UUID innovatorId;
    private long projectId;
    private String title;
    private String description;
    private BigDecimal requestedAmount;
    private BigDecimal currentFunding;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}