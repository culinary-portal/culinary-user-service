package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.contains.ContainsDTO;
import com.culinary.userservice.recipe.service.ContainsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContains);
    }

    @PutMapping("/{containsId}")
    public ResponseEntity<ContainsDTO> updateContains(@PathVariable long containsId, @RequestBody ContainsDTO containsDTO) {
        ContainsDTO updatedContains = containsService.updateContains(containsId, containsDTO);
        return ResponseEntity.ok(updatedContains);
    }

    @DeleteMapping("/{containsId}")
    public ResponseEntity<Void> deleteContains(@PathVariable long containsId) {
        containsService.deleteContains(containsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{containsId}")
    public ResponseEntity<ContainsDTO> getContainsById(@PathVariable long containsId) {
        ContainsDTO containsDTO = containsService.getContainsById(containsId);
        return ResponseEntity.ok(containsDTO);
    }

    @GetMapping
    public ResponseEntity<List<ContainsDTO>> getAllContains() {
        List<ContainsDTO> contains = containsService.getAllContains();
        return ResponseEntity.ok(contains);
    }
}