package com.example.master.controller;

import com.example.master.dto.university.UniversityTypeRequestDTO;
import com.example.master.dto.university.UniversityTypeResponseDTO;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UniversityTypeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // React dev server URL
@RestController
@RequestMapping("/university")
public class UniversityTypeController {

    private final UniversityTypeService service;

    public UniversityTypeController(UniversityTypeService service) {
        this.service = service;
    }

    // GET all university types
    @GetMapping
    public ResponseEntity<List<UniversityTypeResponseDTO>> getAll() {
        List<UniversityTypeResponseDTO> universityTypes = service.getAllUniversityTypes();
        return ResponseEntity.ok(universityTypes);
    }

    // GET a university type by ID
    @GetMapping("/{id}")
    public ResponseEntity<UniversityTypeResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new university type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UniversityTypeRequestDTO requestDTO) {
        try {
            UniversityTypeResponseDTO saved = service.save(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing university type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody UniversityTypeRequestDTO requestDTO) {
        try {
            UniversityTypeResponseDTO updated = service.update(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE a university type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
