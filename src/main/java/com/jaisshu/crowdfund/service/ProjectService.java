package com.jaisshu.crowdfund.service;

import com.jaisshu.crowdfund.dto.ContributionDTO;
import com.jaisshu.crowdfund.dto.ContributionRequestDTO;
import com.jaisshu.crowdfund.dto.ProjectDTO;
import com.jaisshu.crowdfund.entity.Contribution;
import com.jaisshu.crowdfund.entity.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    Project createProject(ProjectDTO projectDTO);
    List<ProjectDTO> getAllActiveProjects();
    List<Project> getProjectsByInnovator(UUID innovatorId);
    void archiveProject(Project project);
    Optional<Project> findById(Long id);

}