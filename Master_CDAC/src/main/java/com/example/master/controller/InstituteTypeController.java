package com.example.master.controller;

import com.example.master.dto.institute.InstituteTypeRequestDTO;
import com.example.master.dto.institute.InstituteTypeResponseDTO;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.InstituteTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // Allow React frontend
@RestController
@RequestMapping("/institute")
public class InstituteTypeController {

    private final InstituteTypeService service;

    @Autowired
    public InstituteTypeController(InstituteTypeService service) {
        this.service = service;
    }

    // GET all
    @GetMapping
    public ResponseEntity<List<InstituteTypeResponseDTO>> getAll() {
        List<InstituteTypeResponseDTO> list = service.getAllInstitutes();
        return ResponseEntity.ok(list);
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<InstituteTypeResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody InstituteTypeRequestDTO requestDTO) {
        try {
            InstituteTypeResponseDTO saved = service.save(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody InstituteTypeRequestDTO requestDTO) {
        try {
            InstituteTypeResponseDTO updated = service.update(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
