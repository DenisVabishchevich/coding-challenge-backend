package com.am.challenge.repository;

import com.am.challenge.domain.Application;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByNameAndEmail(String name, String email);

    @EntityGraph(attributePaths = "projects")
    List<Application> findWithProjectsAllBy();
}
