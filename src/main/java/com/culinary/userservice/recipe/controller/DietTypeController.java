package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.recipe.service.DietTypeService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(createdDietType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietTypeDTO> updateDietType(@PathVariable int id, @RequestBody DietTypeDTO dietTypeDTO) {
        DietTypeDTO updatedDietType = dietTypeService.updateDietType(id, dietTypeDTO);
        return ResponseEntity.ok(updatedDietType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietType(@PathVariable int id) {
        dietTypeService.deleteDietType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietTypeDTO> getDietTypeById(@PathVariable int id) {
        DietTypeDTO dietTypeDTO = dietTypeService.getDietTypeById(id);
        return ResponseEntity.ok(dietTypeDTO);
    }

    @GetMapping
    public ResponseEntity<List<DietTypeDTO>> getAllDietTypes() {
        List<DietTypeDTO> dietTypes = dietTypeService.getAllDietTypes();
        return ResponseEntity.ok(dietTypes);
    }
}