package com.jaisshu.crowdfund.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String title;
    private String description;
    @Schema(hidden = true)
    private UUID donorId;
    private String donorFirstName;
    private String donorLastName;
    private BigDecimal amount;
    private LocalDateTime contributedAt;


}
