package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.DietTypeDTO;
import com.culinary.userservice.recipe.mapper.DietTypeMapper;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.repository.DietTypeRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietTypeService {

    private final DietTypeRepository dietTypeRepository;

    @Transactional
    public DietTypeDTO createDietType(DietTypeDTO dietTypeDTO) {
        DietType dietType = DietTypeMapper.toEntity(dietTypeDTO);
        dietType = dietTypeRepository.save(dietType);
        return DietTypeMapper.toDto(dietType);
    }

    @Transactional
    public DietTypeDTO updateDietType(int id, DietTypeDTO dietTypeDTO) {
        DietType dietType = dietTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DietType not found"));
        dietType.setDietType(dietTypeDTO.getDietType());
        dietType = dietTypeRepository.save(dietType);
        return DietTypeMapper.toDto(dietType);
    }

    @Transactional
    public void deleteDietType(int id) {
        dietTypeRepository.deleteById(id);
    }

    public DietTypeDTO getDietTypeById(int id) {
        DietType dietType = dietTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DietType not found"));
        return DietTypeMapper.toDto(dietType);
    }

    public List<DietTypeDTO> getAllDietTypes() {
        return dietTypeRepository.findAll().stream()
                .map(DietTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public DietType getDietTypeEntityByType(String dietType) {
        return dietTypeRepository.findDietTypeByDietType(dietType.toLowerCase())
                .orElseThrow(() -> new NotFoundException("Diet type not found"));
    }
}