package com.jaisshu.crowdfund.repository;

import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.entity.ProjectStatus;
import com.jaisshu.crowdfund.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByInnovator(User innovator);

    Optional<Project> findByIdAndInnovator_UserId(Long id, UUID innovatorId);

    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.status = ?2 WHERE p.id IN ?1 and p.innovator.userId = ?3")
    void updateProjectStatus(List<Long> projectIds, ProjectStatus status, UUID innovatorId);

}