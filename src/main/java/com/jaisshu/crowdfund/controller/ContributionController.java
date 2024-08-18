//package com.jaisshu.crowdfund.controller;
//
//import com.jaisshu.crowdfund.dto.ContributionDTO;
//import com.jaisshu.crowdfund.dto.ContributionRequestDTO;
//import com.jaisshu.crowdfund.entity.Contribution;
//import com.jaisshu.crowdfund.service.ContributionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/contribution")
//public class ContributionController {
//
//    @Autowired
//    private ContributionService contributionService;
//
//    @PostMapping("{innovatorId}/request")
//    public ResponseEntity<Boolean> requestContribution(@PathVariable UUID innovatorId, @RequestBody List<ContributionRequestDTO> requests) {
//        contributionService.requestContribution(innovatorId,requests);
//        return ResponseEntity.ok(true);
//    }
//
//    @PostMapping
//    public ResponseEntity<ContributionDTO> createContribution(@RequestBody ContributionDTO contributionDTO) {
//        Contribution contribution = contributionService.saveContribution(contributionDTO);
//        return ResponseEntity.ok(contribution.getDTO());
//    }
//
//    @GetMapping("/project/{projectId}")
//    public ResponseEntity<List<ContributionDTO>> getContributionsByProject(@PathVariable Long projectId) {
//        Optional<List<Contribution>> contributions = contributionService.getContributionsByProject(projectId);
//        return contributions
//                .map(contributionList ->
//                    ResponseEntity.ok(contributionList.stream().map(Contribution::getDTO).toList()))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/donor/{donorId}")
//    public ResponseEntity<List<ContributionDTO>> getContributionsByDonor(@PathVariable UUID donorId) {
//        Optional<List<Contribution>> contributions = contributionService.getContributionsByDonor(donorId);
//        return contributions
//                .map(contributionList ->
//                        ResponseEntity.ok(contributionList.stream().map(Contribution::getDTO).toList()))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//
//
//}