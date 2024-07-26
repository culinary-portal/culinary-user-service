package com.culinary.userservice.ingredient.service;

import com.culinary.userservice.ingredient.dto.SpecificDTO;
import com.culinary.userservice.ingredient.mapper.SpecificMapper;
import com.culinary.userservice.ingredient.model.Specific;
import com.culinary.userservice.ingredient.repository.IngredientRepository;
import com.culinary.userservice.ingredient.repository.SpecificRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import com.culinary.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecificService {

    private final SpecificRepository specificRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public List<SpecificDTO> findAll() {
        return specificRepository.findAll().stream()
                .map(SpecificMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SpecificDTO findById(int id) {
        Specific specific = specificRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Specific not found"));
        return SpecificMapper.toDto(specific);
    }

    @Transactional
    public SpecificDTO save(SpecificDTO specificDto) {
        Specific specific = SpecificMapper.toEntity(
                specificDto,
                userRepository.findById(specificDto.getUserId()).orElseThrow(() -> new NotFoundException("User not found")),
                ingredientRepository.findById(specificDto.getIngredientId()).orElseThrow(() -> new NotFoundException("Ingredient not found"))
        );
        Specific savedSpecific = specificRepository.save(specific);
        return SpecificMapper.toDto(savedSpecific);
    }

    @Transactional
    public SpecificDTO update(int id, SpecificDTO specificDto) {
        Specific specific = specificRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Specific not found"));
        specific.setLikes(specificDto.getLikes());
        Specific updatedSpecific = specificRepository.save(specific);
        return SpecificMapper.toDto(updatedSpecific);
    }

    @Transactional
    public void delete(int id) {
        specificRepository.deleteById(id);
    }
}