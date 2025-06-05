package com.example.master.controller;

import com.example.master.dto.usertype.UsertypeTypeRequestDTO;
import com.example.master.dto.usertype.UsertypeTypeResponseDTO;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user-type")
public class UserTypeController {

    private final UserTypeService service;

    public UserTypeController(UserTypeService service) {
        this.service = service;
    }

    // GET all user types
    @GetMapping
    public ResponseEntity<List<UsertypeTypeResponseDTO>> getAll() {
        List<UsertypeTypeResponseDTO> userTypes = service.getAllUserTypes();
        return ResponseEntity.ok(userTypes);
    }

    // GET a user type by ID
    @GetMapping("/{id}")
    public ResponseEntity<UsertypeTypeResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new user type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UsertypeTypeRequestDTO requestDTO) {
        try {
            UsertypeTypeResponseDTO saved = service.save(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing user type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody UsertypeTypeRequestDTO requestDTO) {
        try {
            UsertypeTypeResponseDTO updated = service.update(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE a user type
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
