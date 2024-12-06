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

    @PutMapping("/{dietTypeId}")
    public ResponseEntity<DietTypeDTO> updateDietType(@PathVariable long dietTypeId, @RequestBody DietTypeDTO dietTypeDTO) {
        DietTypeDTO updatedDietType = dietTypeService.updateDietType(dietTypeId, dietTypeDTO);
        return ResponseEntity.ok(updatedDietType);
    }

    @DeleteMapping("/{dietTypeId}")
    public ResponseEntity<Void> deleteDietType(@PathVariable long dietTypeId) {
        dietTypeService.deleteDietType(dietTypeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{dietTypeId}")
    public ResponseEntity<DietTypeDTO> getDietTypeById(@PathVariable long dietTypeId) {
        DietTypeDTO dietTypeDTO = dietTypeService.getDietTypeById(dietTypeId);
        return ResponseEntity.ok(dietTypeDTO);
    }

    @GetMapping
    public ResponseEntity<List<DietTypeDTO>> getAllDietTypes() {
        List<DietTypeDTO> dietTypes = dietTypeService.getAllDietTypes();
        return ResponseEntity.ok(dietTypes);
    }
}