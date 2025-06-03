package com.example.master.controller;

import com.example.master.entity.QualificationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.QualificationTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // Allow React frontend
@RestController
@RequestMapping("/qualification")
public class QualificationTypeController {

    private final QualificationTypeService service;

    @Autowired
    public QualificationTypeController(QualificationTypeService service) {
        this.service = service;
    }

    // GET all qualifications
    @GetMapping
    public ResponseEntity<List<QualificationType>> getAll() {
        return ResponseEntity.ok(service.getAllQualifications());
    }

    // GET qualification by ID
    @GetMapping("/{id}")
    public ResponseEntity<QualificationType> getById(@PathVariable Long id) {
        return service.getQualificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE qualification
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody QualificationType qualificationType) {
        try {
            QualificationType saved = service.save(qualificationType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE qualification
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody QualificationType qualificationType) {
        try {
            qualificationType.setId(id);
            QualificationType updated = service.save(qualificationType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE qualification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteQualification(id);
        return ResponseEntity.noContent().build();
    }
}
