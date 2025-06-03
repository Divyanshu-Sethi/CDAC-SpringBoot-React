package com.example.master.controller;

import com.example.master.entity.InstituteType;
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

    // GET all institute types
    @GetMapping
    public ResponseEntity<List<InstituteType>> getAll() {
        List<InstituteType> institutes = service.getAllInstituteTypes();
        return ResponseEntity.ok(institutes);
    }

    // GET a specific institute type by ID
    @GetMapping("/{id}")
    public ResponseEntity<InstituteType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new institute type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody InstituteType instituteType) {
        try {
            InstituteType saved = service.save(instituteType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing institute type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody InstituteType instituteType) {
        try {
            instituteType.setId(id);
            InstituteType updated = service.save(instituteType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE an institute type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
