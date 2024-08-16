package com.jaisshu.crowdfund.service;

import com.jaisshu.crowdfund.dto.ProjectDTO;
import com.jaisshu.crowdfund.entity.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    Project createProject(ProjectDTO projectDTO);
    List<Project> getAllActiveProjects();
    List<Project> getProjectsByInnovator(UUID innovatorId);
    Project archiveProject(Project project);
    Optional<Project> findById(Long id);
}