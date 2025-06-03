package com.example.master.controller;

import com.example.master.entity.UniversityType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UniversityTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // React dev server URL
@RestController
@RequestMapping("/university")
public class UniversityTypeController {

    private final UniversityTypeService service;

    @Autowired
    public UniversityTypeController(UniversityTypeService service) {
        this.service = service;
    }

    // GET all university types
    @GetMapping
    public ResponseEntity<List<UniversityType>> getAll() {
        List<UniversityType> universityTypes = service.getAllUniversityTypes();
        return ResponseEntity.ok(universityTypes);
    }

    // GET a university type by ID
    @GetMapping("/{id}")
    public ResponseEntity<UniversityType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new university type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UniversityType universityType) {
        try {
            UniversityType saved = service.save(universityType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing university type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody UniversityType universityType) {
        try {
            universityType.setId(id);
            UniversityType updated = service.save(universityType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a university type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
