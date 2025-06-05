package com.example.master.controller;

import com.example.master.dto.designation.DesignationTypeRequestDTO;
import com.example.master.dto.designation.DesignationTypeResponseDTO;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.DesignationTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/designation")
public class DesignationTypeController {

    private final DesignationTypeService service;

    @Autowired
    public DesignationTypeController(DesignationTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DesignationTypeResponseDTO>> getAll() {
        List<DesignationTypeResponseDTO> list = service.getAllDesignations();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesignationTypeResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DesignationTypeRequestDTO requestDTO) {
        try {
            DesignationTypeResponseDTO savedDTO = service.save(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody DesignationTypeRequestDTO requestDTO) {
        try {
            DesignationTypeResponseDTO updatedDTO = service.update(id, requestDTO);
            return ResponseEntity.ok(updatedDTO);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
