package com.jaisshu.crowdfund.service.impl;

import com.jaisshu.crowdfund.dto.ContributionDTO;
import com.jaisshu.crowdfund.dto.ContributionRequestDTO;
import com.jaisshu.crowdfund.entity.Contribution;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.entity.ProjectStatus;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.exception.DatabaseException;
import com.jaisshu.crowdfund.repository.ContributionRepository;
import com.jaisshu.crowdfund.repository.ProjectRepository;
import com.jaisshu.crowdfund.repository.UserRepository;
import com.jaisshu.crowdfund.service.ContributionService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContributionServiceImpl implements ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public Contribution saveContribution(ContributionDTO contributionDTO) {
        Optional<Project> projectOpt = projectRepository.findById(contributionDTO.getProjectId());
        Optional<User> donorOpt = userRepository.findByUserId(contributionDTO.getDonorId());

        if (projectOpt.isPresent() && donorOpt.isPresent()) {
            Project project = projectOpt.get();
            User donor = donorOpt.get();

            Contribution contribution = Contribution.builder()
                    .project(project)
                    .donor(donor)
                    .amount(contributionDTO.getAmount())
                    .contributedAt(LocalDateTime.now())
                    .build();
            Contribution contributionPersisted =  contributionRepository.save(contribution);

            // Update the project's current funding
            BigDecimal newFunding = project.getCurrentFunding().add(contribution.getAmount());
            project.setCurrentFunding(newFunding);

            // Check if the project has met or exceeded its funding goal
            if (newFunding.compareTo(project.getRequestedAmount()) >= 0) {
                archiveProject(project);
            }
            projectRepository.save(project);
            return contribution;
        } else {
            throw new IllegalArgumentException("Invalid Project ID or Donor ID");
        }
    }

    @Transactional
    public void archiveProject(Project project) {
        project.setStatus(ProjectStatus.ARCHIVED);

        logger.info("Funds transferred to innovator: " + project.getInnovator().getFirstName());
    }

    @Override
    public Optional<List<Contribution>> getContributionsByProject(UUID innovatorId, Long projectId) {
        Optional<Project> project = projectRepository.findByIdAndInnovator_UserId(projectId, innovatorId);
        return project.map(proj -> contributionRepository.findByProject(proj).orElseGet(List::of));
    }

    @Override
    public Optional<List<Contribution>> getContributionsByDonor(UUID donorId) {
        Optional<User> donor = userRepository.findByUserId(donorId);
        return donor.flatMap(contributionRepository::findByDonor);
    }

    @Override
    public void requestContribution(UUID innovatorId, List<ContributionRequestDTO> request) {
        try {
            List<Long> projectIds = request.stream().map(ContributionRequestDTO::getProjectId).toList();
            ProjectStatus status = ProjectStatus.ACTIVE;
            projectRepository.updateProjectStatus(projectIds, status, innovatorId);
        }
        catch(Exception ex){
            logger.error("Exception while performing contribution request");
            throw new DatabaseException(ex.getMessage());
        }

    }
}