package com.example.master.controller;

import com.example.master.dto.qualification.QualificationTypeRequestDTO;
import com.example.master.dto.qualification.QualificationTypeResponseDTO;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.QualificationTypeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // Allow React frontend
@RestController
@RequestMapping("/qualification")
public class QualificationTypeController {

    private final QualificationTypeService service;

    public QualificationTypeController(QualificationTypeService service) {
        this.service = service;
    }

    // GET all qualifications
    @GetMapping
    public ResponseEntity<List<QualificationTypeResponseDTO>> getAll() {
        List<QualificationTypeResponseDTO> qualifications = service.getAllQualifications();
        return ResponseEntity.ok(qualifications);
    }

    // GET qualification by ID
    @GetMapping("/{id}")
    public ResponseEntity<QualificationTypeResponseDTO> getById(@PathVariable Long id) {
        return service.getQualificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE qualification
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody QualificationTypeRequestDTO requestDTO) {
        try {
            QualificationTypeResponseDTO saved = service.save(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE qualification
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody QualificationTypeRequestDTO requestDTO) {
        try {
            QualificationTypeResponseDTO updated = service.update(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE qualification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteQualification(id);
        return ResponseEntity.noContent().build();
    }
}
