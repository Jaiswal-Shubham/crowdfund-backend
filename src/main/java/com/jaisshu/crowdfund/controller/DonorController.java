package com.jaisshu.crowdfund.controller;

import com.jaisshu.crowdfund.dto.ContributionDTO;
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
@RequestMapping("/api/donor")
public class DonorController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ContributionService contributionService;


    @GetMapping("projects")
    public ResponseEntity<List<ProjectDTO>> getAllActiveProjects() {
        List<ProjectDTO> responseDTOs = projectService.getAllActiveProjects();

        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping("{donorId}/contribution")
    public ResponseEntity<ContributionDTO> createContribution(@PathVariable UUID donorId, @RequestBody ContributionDTO contributionDTO) {
        contributionDTO.setDonorId(donorId);
        Contribution contribution = contributionService.saveContribution(contributionDTO);
        return ResponseEntity.ok(contribution.getDTO());
    }

    @GetMapping("{donorId}/contribution")
    public ResponseEntity<List<ContributionDTO>> getContributionsByDonor(@PathVariable UUID donorId) {
        Optional<List<Contribution>> contributions = contributionService.getContributionsByDonor(donorId);
        return contributions
                .map(contributionList ->
                        ResponseEntity.ok(contributionList.stream().map(Contribution::getDTO).toList()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}