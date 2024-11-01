package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.recipe.service.DietTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diet-types")
@RequiredArgsConstructor
public class DietTypeController {

    private final DietTypeService dietTypeService;

    @PostMapping
    public ResponseEntity<DietTypeDTO> createDietType(@RequestBody DietTypeDTO dietTypeDTO) {
        DietTypeDTO createdDietType = dietTypeService.createDietType(dietTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDietType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietTypeDTO> updateDietType(@PathVariable long id, @RequestBody DietTypeDTO dietTypeDTO) {
        DietTypeDTO updatedDietType = dietTypeService.updateDietType(id, dietTypeDTO);
        return ResponseEntity.ok(updatedDietType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietType(@PathVariable long id) {
        dietTypeService.deleteDietType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietTypeDTO> getDietTypeById(@PathVariable long id) {
        DietTypeDTO dietTypeDTO = dietTypeService.getDietTypeById(id);
        return ResponseEntity.ok(dietTypeDTO);
    }

    @GetMapping
    public ResponseEntity<List<DietTypeDTO>> getAllDietTypes() {
        List<DietTypeDTO> dietTypes = dietTypeService.getAllDietTypes();
        return ResponseEntity.ok(dietTypes);
    }
}