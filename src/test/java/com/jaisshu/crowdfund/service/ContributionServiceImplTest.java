package com.jaisshu.crowdfund.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.jaisshu.crowdfund.dto.ContributionDTO;
import com.jaisshu.crowdfund.entity.Contribution;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.entity.ProjectStatus;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.repository.ContributionRepository;
import com.jaisshu.crowdfund.repository.ProjectRepository;
import com.jaisshu.crowdfund.repository.UserRepository;
import com.jaisshu.crowdfund.service.impl.ContributionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ContributionServiceImplTest {

    @Mock
    private ContributionRepository contributionRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContributionServiceImpl contributionService;

    private ContributionDTO contributionDTO;
    private Project project;
    private User donor;

    private User innovator;

    @BeforeEach
    public void setUp() {
        UUID donorId = UUID.randomUUID();
        Long projectId = 1L;

        innovator = User.builder()
                .userId(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Smith")
                .build();

        donor = User.builder()
                .userId(donorId)
                .firstName("John")
                .lastName("Doe")
                .build();

        project = Project.builder()
                .id(projectId)
                .title("Innovative Project")
                .description("This is a very innovative project.")
                .requestedAmount(BigDecimal.valueOf(1000))
                .currentFunding(BigDecimal.valueOf(500))
                .status(ProjectStatus.ACTIVE)
                .innovator(innovator)
                .build();

        contributionDTO = ContributionDTO.builder()
                .donorId(donorId)
                .projectId(projectId)
                .amount(BigDecimal.valueOf(200))
                .build();
    }

    @Test
    public void testSaveContribution_SuccessfulContribution() {
        // Arrange
        when(projectRepository.findById(contributionDTO.getProjectId())).thenReturn(Optional.of(project));
        when(userRepository.findByUserId(contributionDTO.getDonorId())).thenReturn(Optional.of(donor));
        when(contributionRepository.save(any(Contribution.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Contribution contribution = contributionService.saveContribution(contributionDTO);

        // Assert
        assertNotNull(contribution);
        assertEquals(contributionDTO.getAmount(), contribution.getAmount());
        assertEquals(donor, contribution.getDonor());
        assertEquals(project, contribution.getProject());
        verify(contributionRepository, times(1)).save(any(Contribution.class));
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testSaveContribution_ExceedsRequestedAmount_ArchivesProject() {
        // Arrange
        contributionDTO.setAmount(BigDecimal.valueOf(600)); // This will exceed the requested amount
        when(projectRepository.findById(contributionDTO.getProjectId())).thenReturn(Optional.of(project));
        when(userRepository.findByUserId(contributionDTO.getDonorId())).thenReturn(Optional.of(donor));
        when(contributionRepository.save(any(Contribution.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Contribution contribution = contributionService.saveContribution(contributionDTO);

        // Assert
        assertEquals(ProjectStatus.ARCHIVED, project.getStatus());
        verify(contributionRepository, times(1)).save(any(Contribution.class));
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testSaveContribution_InvalidProjectOrDonorId() {
        // Arrange
        when(projectRepository.findById(contributionDTO.getProjectId())).thenReturn(Optional.empty());
        when(userRepository.findByUserId(contributionDTO.getDonorId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> contributionService.saveContribution(contributionDTO));
        verify(contributionRepository, never()).save(any(Contribution.class));
        verify(projectRepository, never()).save(any(Project.class));
    }
}