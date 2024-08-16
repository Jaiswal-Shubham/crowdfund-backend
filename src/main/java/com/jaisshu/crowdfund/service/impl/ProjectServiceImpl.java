package com.jaisshu.crowdfund.service.impl;

import com.jaisshu.crowdfund.dto.ProjectDTO;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.entity.ProjectStatus;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.repository.ProjectRepository;
import com.jaisshu.crowdfund.repository.UserRepository;
import com.jaisshu.crowdfund.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

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
    public List<Project> getAllActiveProjects() {
        return projectRepository.findByStatus(ProjectStatus.ACTIVE);
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
    public Project archiveProject(Project project) {
        project.setStatus(ProjectStatus.ARCHIVED);
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }
}