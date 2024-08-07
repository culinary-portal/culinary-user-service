package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
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

    @Transactional
    public DietTypeDTO getDietTypeById(int id) {
        return DietTypeMapper.toDto(getDietTypeEntityById(id));
    }

    @Transactional
    public DietType getDietTypeEntityById(int id) {
        return dietTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DietType not found"));
    }

    public List<DietTypeDTO> getAllDietTypes() {
        return dietTypeRepository.findAll().stream()
                .map(DietTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public DietType getDietTypeEntityByType(String dietType) {
        return dietTypeRepository.findDietTypeByDietTypeIgnoreCase(dietType)
                .orElseThrow(() -> new NotFoundException("Diet type not found"));
    }
}