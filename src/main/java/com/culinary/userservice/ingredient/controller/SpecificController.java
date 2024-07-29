package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.specific.SpecificDTO;
import com.culinary.userservice.ingredient.service.SpecificService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specifics")
@RequiredArgsConstructor
@Tag(name = "Specifics", description = "Operations related to Specific entities")
public class SpecificController {

    private final SpecificService specificService;

    @Operation(summary = "Get all Specifics", responses = {
            @ApiResponse(responseCode = "200", description = "Found Specifics",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecificDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<SpecificDTO>> getAllSpecifics() {
        List<SpecificDTO> specifics = specificService.findAll();
        return ResponseEntity.ok(specifics);
    }

    @Operation(summary = "Get a Specific by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Found the Specific",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecificDTO.class))),
            @ApiResponse(responseCode = "404", description = "Specific not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SpecificDTO> getSpecificById(@PathVariable @Parameter(description = "ID of the Specific to get") int id) {
        SpecificDTO specific = specificService.findById(id);
        return ResponseEntity.ok(specific);
    }

    @Operation(summary = "Create a new Specific", responses = {
            @ApiResponse(responseCode = "200", description = "Specific created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecificDTO.class)))
    })
    @PostMapping
    public ResponseEntity<SpecificDTO> createSpecific(@RequestBody @Parameter(description = "SpecificDTO object to create a new Specific") SpecificDTO specificDto) {
        SpecificDTO createdSpecific = specificService.save(specificDto);
        return ResponseEntity.ok(createdSpecific);
    }

    @Operation(summary = "Update an existing Specific", responses = {
            @ApiResponse(responseCode = "200", description = "Specific updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecificDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SpecificDTO> updateSpecific(@PathVariable @Parameter(description = "ID of the Specific to update") int id, @RequestBody @Parameter(description = "SpecificDTO object with updated data") SpecificDTO specificDto) {
        SpecificDTO updatedSpecific = specificService.update(id, specificDto);
        return ResponseEntity.ok(updatedSpecific);
    }

    @Operation(summary = "Delete a Specific by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Specific deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecific(@PathVariable @Parameter(description = "ID of the Specific to delete") int id) {
        specificService.delete(id);
        return ResponseEntity.ok().build();
    }
}