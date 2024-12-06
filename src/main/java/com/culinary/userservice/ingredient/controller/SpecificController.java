package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.specific.GetSpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.PutSpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.SpecificDTO;
import com.culinary.userservice.ingredient.service.SpecificService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/specific")
@RequiredArgsConstructor
@Tag(name = "Specifics", description = "Operations related to Specific entities")
public class SpecificController {

    private final SpecificService specificService;

    @GetMapping
    public ResponseEntity<List<SpecificDTO>> getAllSpecifics() {
        List<SpecificDTO> specifics = specificService.findAll();
        return ResponseEntity.ok(specifics);
    }

    @GetMapping("/{specificId}")
    public ResponseEntity<SpecificDTO> getSpecificById(@PathVariable long specificId) {
        SpecificDTO specific = specificService.findById(specificId);
        return ResponseEntity.ok(specific);
    }

    @PostMapping
    public ResponseEntity<SpecificDTO> createSpecific(@RequestBody PutSpecificDTO specificDto) {
        SpecificDTO createdSpecific = specificService.save(specificDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecific);
    }

    @PutMapping("/{specificId}")
    public ResponseEntity<SpecificDTO> updateSpecific(@PathVariable long specificId,
                                                      @RequestBody PutSpecificDTO specificDto) {
        SpecificDTO updatedSpecific = specificService.update(specificId, specificDto);
        return ResponseEntity.ok(updatedSpecific);
    }

    @DeleteMapping("/{specificId}")
    public ResponseEntity<Void> deleteSpecific(@PathVariable long specificId) {
        specificService.delete(specificId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/disliked-ingredients")
    public ResponseEntity<List<GetSpecificDTO>> getSpecificByUserId(@PathVariable long userId) {
        List<GetSpecificDTO> dislikedIngredients = specificService.getDislikedIngredients(userId);
        return ResponseEntity.ok(dislikedIngredients);
    }
}