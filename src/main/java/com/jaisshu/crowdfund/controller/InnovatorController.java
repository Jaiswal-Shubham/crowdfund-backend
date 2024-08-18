package com.jaisshu.crowdfund.controller;

import com.jaisshu.crowdfund.dto.ContributionDTO;
import com.jaisshu.crowdfund.dto.ContributionRequestDTO;
import com.jaisshu.crowdfund.dto.ProjectDTO;
import com.jaisshu.crowdfund.entity.Contribution;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.service.ContributionService;
import com.jaisshu.crowdfund.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/innovator")
public class InnovatorController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ContributionService contributionService;

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

    @PostMapping("{innovatorId}/request")
    public ResponseEntity<Boolean> requestContribution(@PathVariable UUID innovatorId, @RequestBody List<ContributionRequestDTO> requests) {
        contributionService.requestContribution(innovatorId,requests);
        return ResponseEntity.ok(true);
    }

    @GetMapping("{innovatorId}/contribution/{projectId}")
    public ResponseEntity<List<ContributionDTO>> getContributionsByProject(@PathVariable UUID innovatorId, @PathVariable Long projectId) {
        Optional<List<Contribution>> contributions = contributionService.getContributionsByProject(innovatorId,projectId);
        return contributions
                .map(contributionList ->
                        ResponseEntity.ok(contributionList.stream().map(Contribution::getDTO).toList()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}