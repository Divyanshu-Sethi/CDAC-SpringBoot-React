package com.example.master.service;

import com.example.master.dto.qualification.QualificationTypeRequestDTO;
import com.example.master.dto.qualification.QualificationTypeResponseDTO;
import com.example.master.entity.QualificationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.QualificationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QualificationTypeService {

    private final QualificationTypeRepository repository;

    public QualificationTypeService(QualificationTypeRepository repository) {
        this.repository = repository;
    }

    // Convert Entity -> Response DTO
    private QualificationTypeResponseDTO convertToResponseDTO(QualificationType entity) {
        QualificationTypeResponseDTO dto = new QualificationTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // Convert Request DTO -> Entity
    private QualificationType convertToEntity(QualificationTypeRequestDTO dto) {
        QualificationType entity = new QualificationType();
        entity.setName(dto.getName());
        return entity;
    }

    public List<QualificationTypeResponseDTO> getAllQualifications() {
        return repository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<QualificationTypeResponseDTO> getQualificationById(Long id) {
        return repository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public QualificationTypeResponseDTO save(QualificationTypeRequestDTO requestDTO) throws DuplicateEntryException {
        boolean exists = repository.findByName(requestDTO.getName()).isPresent();
        if (exists) {
            throw new DuplicateEntryException("Qualification Type name already exists: " + requestDTO.getName());
        }
        QualificationType entity = convertToEntity(requestDTO);
        QualificationType savedEntity = repository.save(entity);
        return convertToResponseDTO(savedEntity);
    }

    public QualificationTypeResponseDTO update(Long id, QualificationTypeRequestDTO requestDTO) throws DuplicateEntryException {
        Optional<QualificationType> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Qualification Type not found with id: " + id);
        }

        boolean exists = repository.findByName(requestDTO.getName())
                .filter(u -> !u.getId().equals(id))
                .isPresent();

        if (exists) {
            throw new DuplicateEntryException("Qualification Type name already exists: " + requestDTO.getName());
        }

        QualificationType entity = existingOpt.get();
        entity.setName(requestDTO.getName());

        QualificationType updatedEntity = repository.save(entity);
        return convertToResponseDTO(updatedEntity);
    }

    public void deleteQualification(Long id) {
        repository.deleteById(id);
    }
}
