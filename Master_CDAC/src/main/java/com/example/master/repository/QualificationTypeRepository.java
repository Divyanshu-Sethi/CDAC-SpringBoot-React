package com.example.master.repository;

import com.example.master.entity.QualificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QualificationTypeRepository extends JpaRepository<QualificationType, Long> {
Optional<QualificationType> findByName(String name);
}
