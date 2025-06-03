package com.example.master.service;

import com.example.master.entity.UniversityType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.UniversityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UniversityTypeService {

    @Autowired
    private final UniversityTypeRepository repository;

    public UniversityTypeService(UniversityTypeRepository repository) {
        this.repository = repository;
    }

    public List<UniversityType> getAllUniversityTypes() { return repository.findAll(); }

    public Optional<UniversityType> findById(Long id) { return repository.findById(id); }

    public UniversityType save(UniversityType universityType) throws DuplicateEntryException {
        boolean exists;
        if (universityType.getId() == null) {
            exists = repository.findByName(universityType.getName()).isPresent();
        } else {
            exists = repository.findByName(universityType.getName())
                    .filter(u -> !u.getId().equals(universityType.getId()))
                    .isPresent();
        }
        if (exists) {
            throw new DuplicateEntryException("University Type name already exists: " + universityType.getName());
        }
        return repository.save(universityType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
