package com.example.master.service;

import com.example.master.dto.usertype.UsertypeTypeRequestDTO;
import com.example.master.dto.usertype.UsertypeTypeResponseDTO;
import com.example.master.entity.UserType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTypeService {

    private final UserTypeRepository repository;

    public UserTypeService(UserTypeRepository repository) {
        this.repository = repository;
    }

    // Convert Entity -> Response DTO
    private UsertypeTypeResponseDTO convertToResponseDTO(UserType entity) {
        UsertypeTypeResponseDTO dto = new UsertypeTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // Convert Request DTO -> Entity
    private UserType convertToEntity(UsertypeTypeRequestDTO dto) {
        UserType entity = new UserType();
        entity.setName(dto.getName());
        return entity;
    }

    public List<UsertypeTypeResponseDTO> getAllUserTypes() {
        return repository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsertypeTypeResponseDTO> findById(Long id) {
        return repository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public UsertypeTypeResponseDTO save(UsertypeTypeRequestDTO requestDTO) throws DuplicateEntryException {
        boolean exists = repository.findByName(requestDTO.getName()).isPresent();
        if (exists) {
            throw new DuplicateEntryException("User Type name already exists: " + requestDTO.getName());
        }
        UserType entity = convertToEntity(requestDTO);
        UserType savedEntity = repository.save(entity);
        return convertToResponseDTO(savedEntity);
    }

    public UsertypeTypeResponseDTO update(Long id, UsertypeTypeRequestDTO requestDTO) throws DuplicateEntryException {
        Optional<UserType> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("User Type not found with id: " + id);
        }

        boolean exists = repository.findByName(requestDTO.getName())
                .filter(u -> !u.getId().equals(id))
                .isPresent();

        if (exists) {
            throw new DuplicateEntryException("User Type name already exists: " + requestDTO.getName());
        }

        UserType entity = existingOpt.get();
        entity.setName(requestDTO.getName());

        UserType updatedEntity = repository.save(entity);
        return convertToResponseDTO(updatedEntity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
