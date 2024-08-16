package com.jaisshu.crowdfund.entity;

import com.jaisshu.crowdfund.dto.ProjectDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private BigDecimal requestedAmount;

    @Column(nullable = false)
    private BigDecimal currentFunding;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innovator_id", referencedColumnName = "userId", nullable = false)
    private User innovator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public ProjectDTO getDTO() {
        return ProjectDTO.builder()
                .title(this.title)
                .description(this.description)
                .requestedAmount(this.requestedAmount)
                .currentFunding(this.currentFunding)
                .status(this.status.name())
                .projectId(this.id)
                .innovatorId(this.innovator.getUserId())
                .build();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}