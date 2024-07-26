package com.culinary.userservice.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
//
//    private final FavoriteService favoriteService;
//
//    @PostMapping
//    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteDTO favoriteDTO) {
//        FavoriteDTO createdFavorite = favoriteService.createFavorite(favoriteDTO);
//        return ResponseEntity.ok(createdFavorite);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<FavoriteDTO> updateFavorite(@PathVariable long id, @RequestBody FavoriteDTO favoriteDTO) {
//        FavoriteDTO updatedFavorite = favoriteService.updateFavorite(id, favoriteDTO);
//        return ResponseEntity.ok(updatedFavorite);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFavorite(@PathVariable long id) {
//        favoriteService.deleteFavorite(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable long id) {
//        FavoriteDTO favoriteDTO = favoriteService.getFavoriteById(id);
//        return ResponseEntity.ok(favoriteDTO);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
//        List<FavoriteDTO> favorites = favoriteService.getAllFavorites();
//        return ResponseEntity.ok(favorites);
//    }
}