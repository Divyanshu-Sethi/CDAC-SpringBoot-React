package com.example.master.repository;

import com.example.master.entity.InstituteType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InstituteTypeRepository extends JpaRepository<InstituteType, Long> {
    Optional<InstituteType> findByName(String name);

}
