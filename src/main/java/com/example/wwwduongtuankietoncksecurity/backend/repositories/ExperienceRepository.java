package com.example.wwwduongtuankietoncksecurity.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.wwwduongtuankietoncksecurity.backend.enums.Roles;
import com.example.wwwduongtuankietoncksecurity.backend.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
//    @Query(name = "Experience.findByRole")
//    List<Experience> findByRole(@Param("role") Roles role);

}
