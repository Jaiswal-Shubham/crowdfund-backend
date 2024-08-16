package com.jaisshu.crowdfund.repository;

import com.jaisshu.crowdfund.entity.Contribution;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    Optional<List<Contribution>> findByProject(Project project);
    Optional<List<Contribution>> findByDonor(User donor);
}