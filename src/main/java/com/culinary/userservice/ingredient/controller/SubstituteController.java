package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.substitute.GetSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.PutSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.SubstituteDTO;
import com.culinary.userservice.ingredient.service.SubstituteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/substitutes")
@RequiredArgsConstructor
@Tag(name = "Substitutes", description = "Operations related to Substitute entities")
public class SubstituteController {

    private final SubstituteService substituteService;

    @Operation(summary = "Get all Substitutes", responses = {
            @ApiResponse(responseCode = "200", description = "Found Substitutes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubstituteDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<SubstituteDTO>> getAllSubstitutes() {
        List<SubstituteDTO> substitutes = substituteService.findAll();
        return ResponseEntity.ok(substitutes);
    }

    @Operation(summary = "Get a Substitute by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Found the Substitute",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubstituteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Substitute not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetSubstituteDTO> getSubstituteById(@PathVariable int id) {
        GetSubstituteDTO substitute = substituteService.findById(id);
        return ResponseEntity.ok(substitute);
    }

    @Operation(summary = "Create a new Substitute", responses = {
            @ApiResponse(responseCode = "200", description = "Substitute created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubstituteDTO.class)))
    })
    @PostMapping
    public ResponseEntity<SubstituteDTO> createSubstitute(@RequestBody PutSubstituteDTO substituteDto) {
        SubstituteDTO createdSubstitute = substituteService.save(substituteDto);
        return ResponseEntity.ok(createdSubstitute);
    }

    @Operation(summary = "Update an existing Substitute", responses = {
            @ApiResponse(responseCode = "200", description = "Substitute updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubstituteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Substitute not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SubstituteDTO> updateSubstitute(@PathVariable int id, @RequestBody PutSubstituteDTO substituteDto) {
        SubstituteDTO updatedSubstitute = substituteService.update(id, substituteDto);
        return ResponseEntity.ok(updatedSubstitute);
    }

    @Operation(summary = "Delete a Substitute by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Substitute deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Substitute not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubstitute(@PathVariable int id) {
        substituteService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ingredient/{ingredientId}")
    public ResponseEntity<List<GetSubstituteDTO>> getSubstituteByIngredientId(@PathVariable int ingredientId) {
        List<GetSubstituteDTO> substitutes = substituteService.findSubstituteByIngredientId(ingredientId);
        return ResponseEntity.ok(substitutes);
    }
}