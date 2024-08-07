package com.culinary.userservice.ingredient.service;

import com.culinary.userservice.ingredient.dto.substitute.GetSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.PutSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.SubstituteDTO;
import com.culinary.userservice.ingredient.mapper.SubstituteMapper;
import com.culinary.userservice.ingredient.model.Substitute;
import com.culinary.userservice.ingredient.repository.SubstituteRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubstituteService {

    private final SubstituteRepository substituteRepository;
    private final SubstituteMapper substituteMapper;

    @Transactional(readOnly = true)
    public List<SubstituteDTO> findAll() {
        return substituteRepository.findAll().stream()
                .map(e -> substituteMapper.toDto(e))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GetSubstituteDTO findById(int id) {
        Substitute substitute = substituteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Substitute not found"));
        return SubstituteMapper.toGetDto(substitute);
    }

    @Transactional
    public SubstituteDTO save(PutSubstituteDTO substituteDto) {
        Substitute substitute = substituteMapper.toEntity(substituteDto);
        Substitute savedSubstitute = substituteRepository.save(substitute);
        return substituteMapper.toDto(savedSubstitute);
    }

    @Transactional
    public SubstituteDTO update(int id, PutSubstituteDTO substituteDto) {
        Substitute substitute = substituteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Substitute not found"));
        substituteMapper.updateEntityFromDto(substitute, substituteDto);
        Substitute updatedSubstitute = substituteRepository.save(substitute);
        return substituteMapper.toDto(updatedSubstitute);
    }

    @Transactional
    public void delete(int id) {
        substituteRepository.deleteById(id);
    }

    public List<GetSubstituteDTO> findSubstituteByIngredientId(int ingredientId) {
        return substituteRepository.findSubstituteByIngredientId(ingredientId).stream()
                .map(e -> SubstituteMapper.toGetDto(e))
                .collect(Collectors.toList());
    }
}