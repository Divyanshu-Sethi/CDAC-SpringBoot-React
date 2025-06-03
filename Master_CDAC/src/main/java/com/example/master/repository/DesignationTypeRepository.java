package com.example.master.repository;

import com.example.master.entity.DesignationType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DesignationTypeRepository extends JpaRepository<DesignationType, Long> {
    Optional<DesignationType> findByName(String name);
}
