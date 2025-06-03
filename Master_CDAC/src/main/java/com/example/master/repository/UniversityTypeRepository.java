package com.example.master.repository;

import com.example.master.entity.UniversityType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UniversityTypeRepository extends JpaRepository<UniversityType, Long> {
    Optional<UniversityType> findByName(String name);

}
