package com.example.master.service;

import com.example.master.entity.LeaveType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveTypeService {
    @Autowired
    private final LeaveTypeRepository repository;

    public LeaveTypeService(LeaveTypeRepository repository) {
        this.repository = repository;
    }

    public List<LeaveType> getAllLeaveTypes() {
        return repository.findAll();
    }

    public Optional<LeaveType> findById(Long id) {
        return repository.findById(id);
    }

    public LeaveType save(LeaveType leaveType) throws DuplicateEntryException{
        boolean exists;
        if (leaveType.getId() == null) {
            exists = repository.findByName(leaveType.getName()).isPresent();
        }else{
            exists = repository.findByName(leaveType.getName())
                    .filter(u -> !u.getId().equals(leaveType.getId()))
                    .isPresent();
        }
        if (exists) {
            throw new DuplicateEntryException("Leave Type name already exists: " + leaveType.getName());
        }
        return repository.save(leaveType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
