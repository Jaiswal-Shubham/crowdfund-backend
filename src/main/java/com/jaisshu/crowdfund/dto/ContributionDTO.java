package com.jaisshu.crowdfund.dto;

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
public class ContributionDTO {
    private Long id;
    private Long projectId;
    private UUID donorId;
    private BigDecimal amount;
    private LocalDateTime contributedAt;


}
