package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.substitute.GetSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.PutSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.SubstituteDTO;
import com.culinary.userservice.ingredient.service.SubstituteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/substitutes")
@RequiredArgsConstructor
@Tag(name = "Substitutes", description = "Operations related to Substitute entities")
public class SubstituteController {

    private final SubstituteService substituteService;


    @GetMapping
    public ResponseEntity<List<SubstituteDTO>> getAllSubstitutes() {
        List<SubstituteDTO> substitutes = substituteService.findAll();
        return ResponseEntity.ok(substitutes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSubstituteDTO> getSubstituteById(@PathVariable long id) {
        GetSubstituteDTO substitute = substituteService.findById(id);
        return ResponseEntity.ok(substitute);
    }

    @PostMapping
    public ResponseEntity<SubstituteDTO> createSubstitute(@RequestBody PutSubstituteDTO substituteDto) {
        SubstituteDTO createdSubstitute = substituteService.save(substituteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubstitute);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SubstituteDTO> updateSubstitute(@PathVariable long id, @RequestBody PutSubstituteDTO substituteDto) {
        SubstituteDTO updatedSubstitute = substituteService.update(id, substituteDto);
        return ResponseEntity.ok(updatedSubstitute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubstitute(@PathVariable long id) {
        substituteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ingredient")
    public ResponseEntity<List<GetSubstituteDTO>> getSubstituteByIngredientId(@RequestParam(name = "ingredientId") long ingredientId) {
        List<GetSubstituteDTO> substitutes = substituteService.findSubstituteByIngredientId(ingredientId);
        return ResponseEntity.ok(substitutes);
    }
}