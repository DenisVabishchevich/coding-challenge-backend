package com.am.challenge.repository;

import com.am.challenge.domain.PastProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface PastProjectRepository extends JpaRepository<PastProject, Long> {
    @Modifying
    void deleteAllByApplicationId(Long applicationId);
}
