package com.example.master.controller;

import com.example.master.entity.DesignationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.DesignationTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // Allow React frontend
@RestController
@RequestMapping("/designation")
public class DesignationTypeController {

    private final DesignationTypeService service;

    @Autowired
    public DesignationTypeController(DesignationTypeService service) {
        this.service = service;
    }

    // GET all Designation types
    @GetMapping
    public ResponseEntity<List<DesignationType>> getAll() {
        List<DesignationType> designations = service.getAllDesignations();
        return ResponseEntity.ok(designations);
    }

    // GET a specific Designation type by ID
    @GetMapping("/{id}")
    public ResponseEntity<DesignationType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new Designation type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DesignationType designationType) {
        try {
            DesignationType saved = service.save(designationType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing Designation type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody DesignationType designationType) {
        try {
            designationType.setId(id);
            DesignationType updated = service.save(designationType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a Designation type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
