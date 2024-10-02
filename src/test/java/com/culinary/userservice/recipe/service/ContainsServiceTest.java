package com.culinary.userservice.recipe.service;

import com.culinary.userservice.ingredient.service.IngredientService;
import com.culinary.userservice.recipe.dto.contains.ContainsDTO;
import com.culinary.userservice.recipe.mapper.ContainsMapper;
import com.culinary.userservice.recipe.model.Contains;
import com.culinary.userservice.recipe.repository.ContainsRepository;
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

class ContainsServiceTest {

    @Mock
    private ContainsRepository containsRepository;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private ContainsService containsService;

    private EasyRandom easyRandom;
    private PodamFactoryImpl podamFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        easyRandom = new EasyRandom();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void testCreateContains() {
        ContainsDTO containsDTO = podamFactory.manufacturePojo(ContainsDTO.class);
        Contains contains = easyRandom.nextObject(Contains.class);

        try (MockedStatic<ContainsMapper> mockedMapper = mockStatic(ContainsMapper.class)) {
            when(ContainsMapper.toEntity(eq(containsDTO), any(), any())).thenReturn(contains);
            when(recipeService.getRecipeEntityById(containsDTO.recipeId())).thenReturn(contains.getRecipe());
            when(ingredientService.findEntityById(containsDTO.ingredientId())).thenReturn(contains.getIngredient());
            when(containsRepository.save(any(Contains.class))).thenReturn(contains);
            when(ContainsMapper.toDto(contains)).thenReturn(containsDTO);

            ContainsDTO result = containsService.createContains(containsDTO);

            assertNotNull(result);
            assertEquals(containsDTO, result);
            verify(containsRepository).save(contains);
        }
    }

    @Test
    void testUpdateContains() {
        long id = 1L;
        ContainsDTO containsDTO = podamFactory.manufacturePojo(ContainsDTO.class);
        Contains contains = easyRandom.nextObject(Contains.class);

        try (MockedStatic<ContainsMapper> mockedMapper = mockStatic(ContainsMapper.class)) {
            when(containsRepository.findById(id)).thenReturn(Optional.of(contains));
            when(recipeService.getRecipeEntityById(containsDTO.recipeId())).thenReturn(contains.getRecipe());
            when(ingredientService.findEntityById(containsDTO.ingredientId())).thenReturn(contains.getIngredient());
            when(containsRepository.save(any(Contains.class))).thenReturn(contains);
            when(ContainsMapper.toDto(contains)).thenReturn(containsDTO);

            ContainsDTO result = containsService.updateContains(id, containsDTO);

            assertNotNull(result);
            assertEquals(containsDTO, result);
            verify(containsRepository).findById(id);
            verify(containsRepository).save(contains);
        }
    }

    @Test
    void testDeleteContains() {
        long id = 1L;

        containsService.deleteContains(id);

        verify(containsRepository).deleteById(id);
    }

    @Test
    void testGetContainsById() {
        long id = 1L;
        Contains contains = easyRandom.nextObject(Contains.class);
        ContainsDTO containsDTO = podamFactory.manufacturePojo(ContainsDTO.class);

        try (MockedStatic<ContainsMapper> mockedMapper = mockStatic(ContainsMapper.class)) {
            when(containsRepository.findById(id)).thenReturn(Optional.of(contains));
            when(ContainsMapper.toDto(contains)).thenReturn(containsDTO);

            ContainsDTO result = containsService.getContainsById(id);

            assertNotNull(result);
            assertEquals(containsDTO, result);
            verify(containsRepository).findById(id);
        }
    }

    @Test
    void testGetAllContains() {
        List<Contains> containsList = easyRandom.objects(Contains.class, 5).toList();

        try (MockedStatic<ContainsMapper> mockedMapper = mockStatic(ContainsMapper.class)) {
            when(containsRepository.findAll()).thenReturn(containsList);
            when(ContainsMapper.toDto(any(Contains.class))).thenReturn(podamFactory.manufacturePojo(ContainsDTO.class));

            List<ContainsDTO> result = containsService.getAllContains();

            assertNotNull(result);
            assertEquals(5, result.size());
            verify(containsRepository).findAll();
        }
    }
}
