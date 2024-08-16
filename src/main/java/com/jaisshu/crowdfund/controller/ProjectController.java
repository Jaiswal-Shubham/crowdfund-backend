//package com.jaisshu.crowdfund.controller;
//
//import com.jaisshu.crowdfund.dto.ProjectDTO;
//import com.jaisshu.crowdfund.entity.Project;
//import com.jaisshu.crowdfund.service.ProjectService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/project")
//public class ProjectController {
//
//    @Autowired
//    private ProjectService projectService;
//
//    @PostMapping
//    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
//        Project createdProject = projectService.createProject(projectDTO);
//        return ResponseEntity.ok(createdProject.getDTO());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ProjectDTO>> getAllActiveProjects(UUID donorId) {
//        List<Project> projects = projectService.getAllActiveProjects();
//        List<ProjectDTO> responseDTOs = projects.stream()
//                .map(Project::getDTO)
//                .toList();
//        return ResponseEntity.ok(responseDTOs);
//    }
//
//    @GetMapping("/innovator/{innovatorId}")
//    public ResponseEntity<List<ProjectDTO>> getInnovatorProjects(@PathVariable UUID innovatorId) {
//        List<Project> projects = projectService.getProjectsByInnovator(innovatorId);
//        List<ProjectDTO> responseDTOs = projects.stream()
//                .map(Project::getDTO)
//                .toList();
//        return ResponseEntity.ok(responseDTOs);
//    }
//
//}