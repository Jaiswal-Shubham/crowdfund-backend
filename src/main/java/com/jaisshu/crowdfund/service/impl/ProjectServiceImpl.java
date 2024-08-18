package com.jaisshu.crowdfund.service.impl;

import com.jaisshu.crowdfund.dto.ContributionDTO;
import com.jaisshu.crowdfund.dto.ContributionRequestDTO;
import com.jaisshu.crowdfund.dto.ProjectDTO;
import com.jaisshu.crowdfund.entity.Contribution;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.entity.ProjectStatus;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.exception.DatabaseException;
import com.jaisshu.crowdfund.repository.ContributionRepository;
import com.jaisshu.crowdfund.repository.ProjectRepository;
import com.jaisshu.crowdfund.repository.UserRepository;
import com.jaisshu.crowdfund.service.ContributionService;
import com.jaisshu.crowdfund.service.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Project createProject(ProjectDTO projectDTO) {
        Optional<User> innovatorOpt = userRepository.findByUserId(projectDTO.getInnovatorId());
        if (innovatorOpt.isPresent()) {
            Project project = Project.builder()
                    .title(projectDTO.getTitle())
                    .description(projectDTO.getDescription())
                    .requestedAmount(projectDTO.getRequestedAmount())
                    .currentFunding(BigDecimal.ZERO)
                    .status(ProjectStatus.INACTIVE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .innovator(innovatorOpt.get())
                    .build();
            return projectRepository.save(project);
        } else {
            throw new IllegalArgumentException("Innovator not found");
        }
    }

    @Override
    public List<ProjectDTO> getAllActiveProjects() {
        List<Project> projects = projectRepository.findByStatus(ProjectStatus.ACTIVE);
        return projects.stream().map(project -> ProjectDTO.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .currentFunding(project.getCurrentFunding())
                .requestedAmount(project.getRequestedAmount())
                .createdAt(project.getCreatedAt())
                .description(project.getDescription()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<Project> getProjectsByInnovator(UUID innovatorId) {
        Optional<User> innovatorOpt = userRepository.findByUserId(innovatorId);
        if (innovatorOpt.isPresent()) {
            return projectRepository.findByInnovator(innovatorOpt.get());
        } else {
            throw new IllegalArgumentException("Innovator not found");
        }
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public void archiveProject(Project project) {
        project.setStatus(ProjectStatus.ARCHIVED);
        logger.info("Funds transferred to innovator: " + project.getInnovator().getFirstName());
    }
}