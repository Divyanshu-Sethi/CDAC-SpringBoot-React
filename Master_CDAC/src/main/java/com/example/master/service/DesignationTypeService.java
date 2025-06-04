package com.example.master.service;

import com.example.master.entity.DesignationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.DesignationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DesignationTypeService {

    private final DesignationTypeRepository repository;

    public DesignationTypeService(DesignationTypeRepository repository) {
        this.repository = repository;
    }

    public List<DesignationType> getAllDesignations() {
        return repository.findAll();
    }

    public Optional<DesignationType> findById(Long id) { return repository.findById(id); }

    public DesignationType save(DesignationType designationType)throws DuplicateEntryException {
        boolean exists;
        if (designationType.getId() == null) {
            exists = repository.findByName(designationType.getName()).isPresent();
        }else{
            exists = repository.findByName(designationType.getName())
                    .filter(u -> !u.getId().equals(designationType.getId()))
                    .isPresent();
        }
        if (exists) {
            throw new DuplicateEntryException("Designation Type name already exists: " + designationType.getName());
        }
        return repository.save(designationType);
    }

    public void deleteById(Long id) { repository.deleteById(id); }

}
