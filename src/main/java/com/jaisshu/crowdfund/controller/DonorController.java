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
@RequestMapping("/api/donor")
public class DonorController {

    @Autowired
    private ProjectService projectService;


    @GetMapping("projects")
    public ResponseEntity<List<ProjectDTO>> getAllActiveProjects() {
        List<Project> projects = projectService.getAllActiveProjects();
        List<ProjectDTO> responseDTOs = projects.stream()
                .map(Project::getDTO)
                .toList();
        return ResponseEntity.ok(responseDTOs);
    }


}