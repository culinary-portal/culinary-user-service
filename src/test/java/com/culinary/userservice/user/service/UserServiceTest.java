package com.culinary.userservice.user.service;

import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.service.DietTypeService;
import com.culinary.userservice.recipe.service.GeneralRecipeService;
import com.culinary.userservice.user.dto.PutUserDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.exception.UserNotFoundException;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    EasyRandom generator = new EasyRandom();
    @Mock
    private UserRepository userRepository;
    @Mock
    private GeneralRecipeService generalRecipeService;
    @Mock
    private DietTypeService dietTypeService;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserEntityById_UserExists() {
        User mockUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserEntityById(1L);

        assertNotNull(result);
        assertEquals(mockUser, result);
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserEntityById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserEntityById(1L));
    }

    @Test
    void getUserDetails_UserExists() {
        User mockUser = generator.nextObject(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        UserDetailsDTO userDetails = userService.getUserDetails(1L);

        assertNotNull(userDetails);
        verify(userRepository).findById(1L);
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(generator.nextObject(User.class), generator.nextObject(User.class));
        when(userRepository.findAll()).thenReturn(users);

        List<UserNoDetailsDTO> userDTOs = userService.getAllUsers();

        assertNotNull(userDTOs);
        assertEquals(2, userDTOs.size());
        verify(userRepository).findAll();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void getFavoriteRecipes() {
        User user = generator.nextObject(User.class);
        GeneralRecipe recipe = generator.nextObject(GeneralRecipe.class);
        user.setFavoriteRecipes(Set.of(recipe));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<GeneralRecipeViewDTO> favoriteRecipes = userService.getFavoriteRecipes(1L);

        assertNotNull(favoriteRecipes);
        assertEquals(1, favoriteRecipes.size());
        verify(userRepository).findById(1L);
    }

    @Test
    void getFavoriteDiets() {
        User user = new User();
        DietType diet = new DietType();
        user.setPreferredDiets(Set.of(diet));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<DietTypeDTO> favoriteDiets = userService.getFavoriteDiets(1L);

        assertNotNull(favoriteDiets);
        assertEquals(1, favoriteDiets.size());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserByUserName_UserExists() {
        User user = generator.nextObject(User.class);
        when(userRepository.findByUserNameRegex("testUser")).thenReturn(Optional.of(user));

        UserDetailsDTO userDetails = userService.getUserByUserName("testUser");

        assertNotNull(userDetails);
        verify(userRepository).findByUserNameRegex("testUser");
    }

    @Test
    void getUserByUserName_UserNotFound() {
        when(userRepository.findByUserNameRegex("testUser")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUserName("testUser"));
    }

    @Test
    void addFavoriteRecipe() {
        User user = new User();
        GeneralRecipe recipe = generator.nextObject(GeneralRecipe.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(generalRecipeService.getGeneralRecipeEntityById(2L)).thenReturn(recipe);

        Set<GeneralRecipeViewDTO> result = userService.addFavoriteRecipe(1L, 2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).save(user);
        verify(generalRecipeService).getGeneralRecipeEntityById(2L);
    }

    @Test
    void addFavoriteDiet() {
        User user = new User();
        DietType dietType = new DietType();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dietTypeService.getDietTypeEntityById(2L)).thenReturn(dietType);

        Set<DietTypeDTO> result = userService.addFavoriteDiet(1L, 2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).save(user);
        verify(dietTypeService).getDietTypeEntityById(2L);
    }

    @Test
    void deleteFavoriteRecipe() {
        User user = new User();
        GeneralRecipe recipe = new GeneralRecipe();
        user.getFavoriteRecipes().add(recipe);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(generalRecipeService.getGeneralRecipeEntityById(2L)).thenReturn(recipe);

        Set<GeneralRecipeViewDTO> result = userService.deleteFavoriteRecipe(1L, 2L);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userRepository).save(user);
        verify(generalRecipeService).getGeneralRecipeEntityById(2L);
    }

    @Test
    void deleteFavoriteDiet() {
        User user = new User();
        DietType dietType = new DietType();
        user.getPreferredDiets().add(dietType);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dietTypeService.getDietTypeEntityById(2L)).thenReturn(dietType);

        Set<DietTypeDTO> result = userService.deleteFavoriteDiet(1L, 2L);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userRepository).save(user);
        verify(dietTypeService).getDietTypeEntityById(2L);
    }

    @Test
    void updateUser() {
        User user = generator.nextObject(User.class);
        PutUserDTO dto = new PutUserDTO("test", "test", "test", Date.valueOf("1995-07-08")); // Assuming this has some user update information.
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDetailsDTO result = userService.updateUser(1L, dto);

        assertNotNull(result);
        verify(userRepository).save(user);
    }
}
