package com.jaisshu.crowdfund.service;

import com.jaisshu.crowdfund.dto.ContributionDTO;
import com.jaisshu.crowdfund.dto.ContributionRequestDTO;
import com.jaisshu.crowdfund.entity.Contribution;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContributionService {
    Contribution saveContribution(ContributionDTO contributionDTO);
    Optional<List<Contribution>> getContributionsByProject(Long projectId);
    Optional<List<Contribution>> getContributionsByDonor(UUID donorId);

    void requestContribution(UUID innovatorId, List<ContributionRequestDTO> request);
}