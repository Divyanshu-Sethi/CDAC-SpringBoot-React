package com.example.master.service;

import com.example.master.dto.institute.InstituteTypeRequestDTO;
import com.example.master.dto.institute.InstituteTypeResponseDTO;
import com.example.master.entity.InstituteType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.InstituteTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstituteTypeService {

    private final InstituteTypeRepository repository;

    public InstituteTypeService(InstituteTypeRepository repository) {
        this.repository = repository;
    }

    // Convert Entity -> Response DTO
    private InstituteTypeResponseDTO convertToResponseDTO(InstituteType entity) {
        InstituteTypeResponseDTO dto = new InstituteTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private InstituteType convertToEntity(InstituteTypeRequestDTO dto) {
        InstituteType entity = new InstituteType();
        entity.setName(dto.getName());
        return entity;
    }

    public List<InstituteTypeResponseDTO> getAllInstitutes() {
        List<InstituteType> entities = repository.findAll();
        return entities.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<InstituteTypeResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::convertToResponseDTO);
    }

    public InstituteTypeResponseDTO save(InstituteTypeRequestDTO requestDTO) throws DuplicateEntryException {
        boolean exists = repository.findByName(requestDTO.getName()).isPresent();
        if (exists) {
            throw new DuplicateEntryException("Institute name already exists: " + requestDTO.getName());
        }

        InstituteType entity = convertToEntity(requestDTO);
        InstituteType savedEntity = repository.save(entity);
        return convertToResponseDTO(savedEntity);
    }

    public InstituteTypeResponseDTO update(Long id, InstituteTypeRequestDTO requestDTO) throws DuplicateEntryException {
        Optional<InstituteType> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Institute Type not found with id: " + id);
        }

        boolean exists = repository.findByName(requestDTO.getName())
                .filter(u -> !u.getId().equals(id))
                .isPresent();

        if (exists) {
            throw new DuplicateEntryException("Institute name already exists: " + requestDTO.getName());
        }

        InstituteType entity = existingOpt.get();
        entity.setName(requestDTO.getName());

        InstituteType updatedEntity = repository.save(entity);
        return convertToResponseDTO(updatedEntity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
