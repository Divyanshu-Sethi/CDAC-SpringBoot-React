package com.example.master.service;

import com.example.master.dto.university.UniversityTypeRequestDTO;
import com.example.master.dto.university.UniversityTypeResponseDTO;
import com.example.master.entity.UniversityType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.UniversityTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UniversityTypeService {

    private final UniversityTypeRepository repository;

    public UniversityTypeService(UniversityTypeRepository repository) {
        this.repository = repository;
    }

    // Convert Entity -> Response DTO
    private UniversityTypeResponseDTO convertToResponseDTO(UniversityType entity) {
        UniversityTypeResponseDTO dto = new UniversityTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // Convert Request DTO -> Entity
    private UniversityType convertToEntity(UniversityTypeRequestDTO dto) {
        UniversityType entity = new UniversityType();
        entity.setName(dto.getName());
        return entity;
    }

    public List<UniversityTypeResponseDTO> getAllUniversityTypes() {
        return repository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UniversityTypeResponseDTO> findById(Long id) {
        return repository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public UniversityTypeResponseDTO save(UniversityTypeRequestDTO requestDTO) throws DuplicateEntryException {
        boolean exists = repository.findByName(requestDTO.getName()).isPresent();
        if (exists) {
            throw new DuplicateEntryException("University Type name already exists: " + requestDTO.getName());
        }
        UniversityType entity = convertToEntity(requestDTO);
        UniversityType savedEntity = repository.save(entity);
        return convertToResponseDTO(savedEntity);
    }

    public UniversityTypeResponseDTO update(Long id, UniversityTypeRequestDTO requestDTO) throws DuplicateEntryException {
        Optional<UniversityType> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("University Type not found with id: " + id);
        }

        boolean exists = repository.findByName(requestDTO.getName())
                .filter(u -> !u.getId().equals(id))
                .isPresent();

        if (exists) {
            throw new DuplicateEntryException("University Type name already exists: " + requestDTO.getName());
        }

        UniversityType entity = existingOpt.get();
        entity.setName(requestDTO.getName());

        UniversityType updatedEntity = repository.save(entity);
        return convertToResponseDTO(updatedEntity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
