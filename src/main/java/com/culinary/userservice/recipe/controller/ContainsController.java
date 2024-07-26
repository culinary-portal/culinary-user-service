package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.ContainsDTO;
import com.culinary.userservice.recipe.service.ContainsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contains")
@RequiredArgsConstructor
public class ContainsController {

    private final ContainsService containsService;

    @PostMapping
    public ResponseEntity<ContainsDTO> createContains(@RequestBody ContainsDTO containsDTO) {
        ContainsDTO createdContains = containsService.createContains(containsDTO);
        return ResponseEntity.ok(createdContains);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContainsDTO> updateContains(@PathVariable int id, @RequestBody ContainsDTO containsDTO) {
        ContainsDTO updatedContains = containsService.updateContains(id, containsDTO);
        return ResponseEntity.ok(updatedContains);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContains(@PathVariable int id) {
        containsService.deleteContains(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContainsDTO> getContainsById(@PathVariable int id) {
        ContainsDTO containsDTO = containsService.getContainsById(id);
        return ResponseEntity.ok(containsDTO);
    }

    @GetMapping
    public ResponseEntity<List<ContainsDTO>> getAllContains() {
        List<ContainsDTO> contains = containsService.getAllContains();
        return ResponseEntity.ok(contains);
    }
}