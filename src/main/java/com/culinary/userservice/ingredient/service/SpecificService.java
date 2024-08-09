package com.culinary.userservice.ingredient.service;

import com.culinary.userservice.ingredient.dto.specific.GetSpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.PutSpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.SpecificDTO;
import com.culinary.userservice.ingredient.mapper.SpecificMapper;
import com.culinary.userservice.ingredient.model.Specific;
import com.culinary.userservice.ingredient.repository.IngredientRepository;
import com.culinary.userservice.ingredient.repository.SpecificRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecificService {

    private final SpecificRepository specificRepository;
    private final UserService userService;
    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public List<SpecificDTO> findAll() {
        return specificRepository.findAll().stream()
                .map(SpecificMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SpecificDTO findById(long id) {
        Specific specific = specificRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Specific not found"));
        return SpecificMapper.toDto(specific);
    }

    @Transactional
    public SpecificDTO save(PutSpecificDTO specificDto) {
        Specific specific = SpecificMapper.toEntity(
                specificDto,
                userService.getUserEntityById(specificDto.userId()),
                ingredientRepository.findById(specificDto.ingredient().ingredientId()).orElseThrow(() -> new NotFoundException("Ingredient not found"))
        );
        Specific savedSpecific = specificRepository.save(specific);
        return SpecificMapper.toDto(savedSpecific);
    }

    @Transactional
    public SpecificDTO update(long id, PutSpecificDTO specificDto) {
        Specific specific = specificRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Specific not found"));
        specific.setLikes(specificDto.likes());
        Specific updatedSpecific = specificRepository.save(specific);
        return SpecificMapper.toDto(updatedSpecific);
    }

    @Transactional
    public void delete(long id) {
        specificRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<GetSpecificDTO> getDislikedIngredients(long userId) {
        User user = userService.getUserEntityById(userId);
        return user.getSpecifics().stream()
                .map(SpecificMapper::toDetailsDto).filter(e -> !e.likes()).toList();
    }
}