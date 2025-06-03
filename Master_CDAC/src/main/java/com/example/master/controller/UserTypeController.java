package com.example.master.controller;

import com.example.master.entity.UserType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // React dev server URL
@RestController
@RequestMapping("/user-type")
public class UserTypeController {

    private final UserTypeService service;

    @Autowired
    public UserTypeController(UserTypeService service) {
        this.service = service;
    }

    // GET all user types
    @GetMapping
    public ResponseEntity<List<UserType>> getAll() {
        List<UserType> userTypes = service.getAllUserTypes();
        return ResponseEntity.ok(userTypes);
    }

    // GET a specific user type by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new user type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserType userType) {
        try {
            UserType saved = service.save(userType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing user type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody UserType userType) {
        try {
            userType.setId(id);
            UserType updated = service.save(userType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a user type
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
