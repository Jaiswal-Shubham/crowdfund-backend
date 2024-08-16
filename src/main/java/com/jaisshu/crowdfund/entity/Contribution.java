package com.jaisshu.crowdfund.entity;

import com.jaisshu.crowdfund.dto.ContributionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contributions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", referencedColumnName = "userId", nullable = false)
    private User donor;

    @Column(nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime contributedAt;

    @Transient
    public ContributionDTO getDTO() {
        return ContributionDTO.builder()
                .id(this.id)
                .projectId(this.project.getId())
                .donorId(this.donor.getUserId())
                .amount(this.amount)
                .contributedAt(this.contributedAt)
                .build();
    }
}