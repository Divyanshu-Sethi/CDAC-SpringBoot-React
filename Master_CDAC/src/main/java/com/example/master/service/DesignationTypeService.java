package com.example.master.service;

import com.example.master.dto.designation.DesignationTypeRequestDTO;
import com.example.master.dto.designation.DesignationTypeResponseDTO;
import com.example.master.entity.DesignationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.DesignationTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DesignationTypeService {

    private final DesignationTypeRepository repository;

    public DesignationTypeService(DesignationTypeRepository repository) {
        this.repository = repository;
    }
    // Convert Entity -> Response DTO
    private DesignationTypeResponseDTO convertToResponseDTO(DesignationType entity) {
        DesignationTypeResponseDTO dto = new DesignationTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private DesignationType convertToEntity(DesignationTypeRequestDTO dto) {
        DesignationType entity = new DesignationType();
        entity.setName(dto.getName());
        return entity;
    }

    public List<DesignationTypeResponseDTO> getAllDesignations() {
        List<DesignationType> entities = repository.findAll();
        return entities.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<DesignationTypeResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::convertToResponseDTO); }

    public DesignationTypeResponseDTO save(DesignationTypeRequestDTO requestDTO) throws DuplicateEntryException {
        boolean exists = repository.findByName(requestDTO.getName()).isPresent();
        if (exists) {
            throw new DuplicateEntryException("Designation Type name already exists: " + requestDTO.getName());
        }
        DesignationType entity = convertToEntity(requestDTO);
        DesignationType savedEntity = repository.save(entity);
        return convertToResponseDTO(savedEntity);
    }

    public DesignationTypeResponseDTO update(Long id, DesignationTypeRequestDTO requestDTO) throws DuplicateEntryException {
        Optional<DesignationType> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Designation Type not found with id: " + id);
        }

        boolean exists = repository.findByName(requestDTO.getName())
                .filter(u -> !u.getId().equals(id))
                .isPresent();

        if (exists) {
            throw new DuplicateEntryException("Designation Type name already exists: " + requestDTO.getName());
        }

        DesignationType entity = existingOpt.get();
        entity.setName(requestDTO.getName());

        DesignationType updatedEntity = repository.save(entity);
        return convertToResponseDTO(updatedEntity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
