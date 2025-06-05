package com.example.master.controller;

import com.example.master.dto.leave.LeaveTypeRequestDTO;
import com.example.master.dto.leave.LeaveTypeResponseDTO;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.LeaveTypeService;
import jakarta.validation.Valid;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // Allow React frontend
@RestController
@RequestMapping("/leave")
public class LeaveTypeController {

    private final LeaveTypeService service;

    @Autowired
    public LeaveTypeController(LeaveTypeService service) {
        this.service = service;
    }

    // GET all leave types
    @GetMapping
    public ResponseEntity<List<LeaveTypeResponseDTO>> getAll() {
        List<LeaveTypeResponseDTO> list = service.getAllLeaveTypes();
        return ResponseEntity.ok(list);
    }

    // GET a specific leave type by ID
    @GetMapping("/{id}")
    public ResponseEntity<LeaveTypeResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new leave type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LeaveTypeRequestDTO requestDTO) {
        try {
            LeaveTypeResponseDTO saved = service.save(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing leave type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody LeaveTypeRequestDTO requestDTO) {
        try {
            LeaveTypeResponseDTO updated = service.update(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE a leave type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
