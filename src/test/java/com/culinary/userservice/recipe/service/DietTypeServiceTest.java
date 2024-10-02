package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.recipe.mapper.DietTypeMapper;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.repository.DietTypeRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DietTypeServiceTest {

    @Mock
    private DietTypeRepository dietTypeRepository;

    @InjectMocks
    private DietTypeService dietTypeService;

    private EasyRandom easyRandom;
    private PodamFactoryImpl podamFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        easyRandom = new EasyRandom();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void testCreateDietType() {
        DietTypeDTO dietTypeDTO = podamFactory.manufacturePojo(DietTypeDTO.class);
        DietType dietType = easyRandom.nextObject(DietType.class);

        try (MockedStatic<DietTypeMapper> mockedMapper = mockStatic(DietTypeMapper.class)) {
            when(DietTypeMapper.toEntity(dietTypeDTO)).thenReturn(dietType);
            when(dietTypeRepository.save(any(DietType.class))).thenReturn(dietType);
            when(DietTypeMapper.toDto(dietType)).thenReturn(dietTypeDTO);

            DietTypeDTO result = dietTypeService.createDietType(dietTypeDTO);

            assertNotNull(result);
            assertEquals(dietTypeDTO, result);
            verify(dietTypeRepository).save(dietType);
        }
    }

    @Test
    void testUpdateDietType() {
        long id = 1L;
        DietTypeDTO dietTypeDTO = podamFactory.manufacturePojo(DietTypeDTO.class);
        DietType dietType = easyRandom.nextObject(DietType.class);

        try (MockedStatic<DietTypeMapper> mockedMapper = mockStatic(DietTypeMapper.class)) {
            when(dietTypeRepository.findById(id)).thenReturn(Optional.of(dietType));
            when(dietTypeRepository.save(any(DietType.class))).thenReturn(dietType);
            when(DietTypeMapper.toDto(any(DietType.class))).thenReturn(dietTypeDTO);

            DietTypeDTO result = dietTypeService.updateDietType(id, dietTypeDTO);

            assertNotNull(result);
            assertEquals(dietTypeDTO, result);
            verify(dietTypeRepository).findById(id);
            verify(dietTypeRepository).save(dietType);
        }
    }

    @Test
    void testDeleteDietType() {
        long id = 1L;

        dietTypeService.deleteDietType(id);

        verify(dietTypeRepository).deleteById(id);
    }

    @Test
    void testGetDietTypeById() {
        long id = 1L;
        DietType dietType = easyRandom.nextObject(DietType.class);
        DietTypeDTO dietTypeDTO = podamFactory.manufacturePojo(DietTypeDTO.class);

        try (MockedStatic<DietTypeMapper> mockedMapper = mockStatic(DietTypeMapper.class)) {
            when(dietTypeRepository.findById(id)).thenReturn(Optional.of(dietType));
            when(DietTypeMapper.toDto(dietType)).thenReturn(dietTypeDTO);

            DietTypeDTO result = dietTypeService.getDietTypeById(id);

            assertNotNull(result);
            assertEquals(dietTypeDTO, result);
            verify(dietTypeRepository).findById(id);
        }
    }

    @Test
    void testGetAllDietTypes() {
        List<DietType> dietTypes = easyRandom.objects(DietType.class, 5).toList();

        try (MockedStatic<DietTypeMapper> mockedMapper = mockStatic(DietTypeMapper.class)) {
            when(dietTypeRepository.findAll()).thenReturn(dietTypes);
            when(DietTypeMapper.toDto(any(DietType.class))).thenReturn(podamFactory.manufacturePojo(DietTypeDTO.class));

            List<DietTypeDTO> result = dietTypeService.getAllDietTypes();

            assertNotNull(result);
            assertEquals(5, result.size());
            verify(dietTypeRepository).findAll();
        }
    }

    @Test
    void testGetDietTypeEntityByType() {
        String dietType = "VEGAN";
        DietType dietTypeEntity = easyRandom.nextObject(DietType.class);

        when(dietTypeRepository.findDietTypeByDietTypeIgnoreCase(dietType)).thenReturn(Optional.of(dietTypeEntity));

        DietType result = dietTypeService.getDietTypeEntityByType(dietType);

        assertNotNull(result);
        assertEquals(dietTypeEntity, result);
        verify(dietTypeRepository).findDietTypeByDietTypeIgnoreCase(dietType);
    }

    @Test
    void testGetDietTypeEntityById() {
        long id = 1L;
        DietType dietType = easyRandom.nextObject(DietType.class);

        when(dietTypeRepository.findById(id)).thenReturn(Optional.of(dietType));

        DietType result = dietTypeService.getDietTypeEntityById(id);

        assertNotNull(result);
        assertEquals(dietType, result);
        verify(dietTypeRepository).findById(id);
    }
}
