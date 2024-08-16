package com.jaisshu.crowdfund.controller;

import com.jaisshu.crowdfund.dto.ProjectDTO;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/innovator")
public class InnovatorController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("{innovatorId}/project")
    public ResponseEntity<ProjectDTO> createProject(@PathVariable UUID innovatorId,
                                                    @RequestBody ProjectDTO projectDTO) {
        projectDTO.setInnovatorId(innovatorId);
        Project createdProject = projectService.createProject(projectDTO);
        return ResponseEntity.ok(createdProject.getDTO());
    }

    @GetMapping("{innovatorId}/project")
    public ResponseEntity<List<ProjectDTO>> getInnovatorProjects(@PathVariable UUID innovatorId) {
        List<Project> projects = projectService.getProjectsByInnovator(innovatorId);
        List<ProjectDTO> responseDTOs = projects.stream()
                .map(Project::getDTO)
                .toList();
        return ResponseEntity.ok(responseDTOs);
    }

}