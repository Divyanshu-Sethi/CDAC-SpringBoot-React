package com.example.master.controller;

import com.example.master.entity.LeaveType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.LeaveTypeService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<LeaveType>> getAll() {
        List<LeaveType> leaveTypes = service.getAllLeaveTypes();
        return ResponseEntity.ok(leaveTypes);
    }

    // GET a specific leave type by ID
    @GetMapping("/{id}")
    public ResponseEntity<LeaveType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new leave type
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LeaveType leaveType) {
        try {
            LeaveType saved = service.save(leaveType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE an existing leave type
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody LeaveType leaveType) {
        try {
            leaveType.setId(id);
            LeaveType updated = service.save(leaveType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a leave type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
