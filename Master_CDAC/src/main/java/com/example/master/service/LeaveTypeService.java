package com.example.master.service;

import com.example.master.dto.leave.LeaveTypeRequestDTO;
import com.example.master.dto.leave.LeaveTypeResponseDTO;
import com.example.master.entity.LeaveType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository repository;

    public LeaveTypeService(LeaveTypeRepository repository) {
        this.repository = repository;
    }

    // Convert Entity -> Response DTO
    private LeaveTypeResponseDTO convertToResponseDTO(LeaveType entity) {
        LeaveTypeResponseDTO dto = new LeaveTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // Convert Request DTO -> Entity
    private LeaveType convertToEntity(LeaveTypeRequestDTO dto) {
        LeaveType entity = new LeaveType();
        entity.setName(dto.getName());
        return entity;
    }

    public List<LeaveTypeResponseDTO> getAllLeaveTypes() {
        List<LeaveType> entities = repository.findAll();
        return entities.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<LeaveTypeResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::convertToResponseDTO);
    }

    public LeaveTypeResponseDTO save(LeaveTypeRequestDTO requestDTO) throws DuplicateEntryException {
        boolean exists = repository.findByName(requestDTO.getName()).isPresent();
        if (exists) {
            throw new DuplicateEntryException("Leave Type name already exists: " + requestDTO.getName());
        }

        LeaveType entity = convertToEntity(requestDTO);
        LeaveType savedEntity = repository.save(entity);
        return convertToResponseDTO(savedEntity);
    }

    public LeaveTypeResponseDTO update(Long id, LeaveTypeRequestDTO requestDTO) throws DuplicateEntryException {
        Optional<LeaveType> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Leave Type not found with id: " + id);
        }

        boolean exists = repository.findByName(requestDTO.getName())
                .filter(u -> !u.getId().equals(id))
                .isPresent();

        if (exists) {
            throw new DuplicateEntryException("Leave Type name already exists: " + requestDTO.getName());
        }

        LeaveType entity = existingOpt.get();
        entity.setName(requestDTO.getName());

        LeaveType updatedEntity = repository.save(entity);
        return convertToResponseDTO(updatedEntity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
