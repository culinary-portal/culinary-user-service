package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.contains.ContainsDTO;
import com.culinary.userservice.recipe.service.ContainsService;
import com.culinary.userservice.user.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ActiveProfiles("test")
public class ContainsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContainsService containsService;

    @InjectMocks
    private ContainsController containsController;
    private PodamFactory podamFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(new ContainsController(containsService)).build();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testGetAllContains() throws Exception {
        List<ContainsDTO> containsList = IntStream.range(0, 5)
                .mapToObj(i -> podamFactory.manufacturePojo(ContainsDTO.class))
                .collect(Collectors.toList());
        when(containsService.getAllContains()).thenReturn(containsList);

        mockMvc.perform(get("/api/contains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ingredientId").isNotEmpty())
                .andExpect(jsonPath("$[1].recipeId").isNotEmpty());

        verify(containsService, times(1)).getAllContains();
    }

    @Test
    public void testGetContainsById() throws Exception {
        ContainsDTO containsDTO = podamFactory.manufacturePojo(ContainsDTO.class);
        Long containsId = containsDTO.containsId();
        when(containsService.getContainsById(containsId)).thenReturn(containsDTO);

        mockMvc.perform(get("/api/contains/{id}", containsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.containsId").value(containsId));

        verify(containsService, times(1)).getContainsById(containsId);
    }

    @Test
    public void testGetContainsByIdNotFound() throws Exception {
        Long containsId = 1L;
        when(containsService.getContainsById(containsId)).thenThrow(new NotFoundException("Contains not found"));

        mockMvc.perform(get("/api/contains/{id}", containsId))
                .andExpect(status().isNotFound());

        verify(containsService, times(1)).getContainsById(containsId);
    }

    @Test
    public void testCreateContains() throws Exception {
        ContainsDTO containsDTO = podamFactory.manufacturePojo(ContainsDTO.class);
        when(containsService.createContains(any())).thenReturn(containsDTO);

        mockMvc.perform(post("/api/contains")
                        .contentType("application/json")
                        .content("{\"name\":\"New Contains\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.containsId").isNotEmpty());

        verify(containsService, times(1)).createContains(any());
    }

    @Test
    public void testUpdateContains() throws Exception {
        ContainsDTO containsDTO = podamFactory.manufacturePojo(ContainsDTO.class);
        Long containsId = containsDTO.containsId();
        when(containsService.updateContains(eq(containsId), any())).thenReturn(containsDTO);

        mockMvc.perform(put("/api/contains/{id}", containsId)
                        .contentType("application/json")
                        .content("{\"name\":\"Updated Contains\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.containsId").value(containsId));

        verify(containsService, times(1)).updateContains(eq(containsId), any());
    }

    @Test
    public void testDeleteContains() throws Exception {
        Long containsId = 1L;
        doNothing().when(containsService).deleteContains(containsId);

        mockMvc.perform(delete("/api/contains/{id}", containsId))
                .andExpect(status().isNoContent());

        verify(containsService, times(1)).deleteContains(containsId);
    }
}
