package com.example.master.service;

import com.example.master.entity.InstituteType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.InstituteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InstituteTypeService {

    @Autowired
    private final InstituteTypeRepository repository;

    public InstituteTypeService(InstituteTypeRepository repository) {
        this.repository = repository;
    }

    public List<InstituteType> getAllInstituteTypes() {
        return repository.findAll();
    }

    public Optional<InstituteType> findById(Long id) {
        return repository.findById(id);
    }

    public InstituteType save(InstituteType instituteType) throws DuplicateEntryException {
        boolean exists;
        if (instituteType.getId() == null) {
            exists = repository.findByName(instituteType.getName()).isPresent();
        } else {
            exists = repository.findByName(instituteType.getName())
                    .filter(u -> !u.getId().equals(instituteType.getId()))
                    .isPresent();
        }
        if (exists) {
            throw new DuplicateEntryException("User Type name already exists: " + instituteType.getName());
        }
        return repository.save(instituteType);
    }


    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
