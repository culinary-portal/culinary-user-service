package com.culinary.userservice.user.service;

import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.recipe.mapper.DietTypeMapper;
import com.culinary.userservice.recipe.mapper.GeneralRecipeMapper;
import com.culinary.userservice.recipe.service.DietTypeService;
import com.culinary.userservice.recipe.service.GeneralRecipeService;
import com.culinary.userservice.user.dto.PutUserDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.exception.UserNotFoundException;
import com.culinary.userservice.user.mapper.UserMapper;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GeneralRecipeService generalRecipeService;
    private final DietTypeService dietTypeService;


    public User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        return user.get();

    }

    @Transactional
    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public UserDetailsDTO getUserDetails(long id) {
        return UserMapper.toUserDetailsDTO(getUserEntityById(id));

    }

    public List<UserNoDetailsDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserNoDetailsDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Set<GeneralRecipeViewDTO> getFavoriteRecipes(long userId) {
        User user = getUserEntityById(userId);
        return user.getFavoriteRecipes().stream()
                .map(GeneralRecipeMapper::toGeneralRecipeViewDTO)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    public Set<DietTypeDTO> getFavoriteDiets(long userId) {
        User user = getUserEntityById(userId);
        return user.getPreferredDiets().stream()
                .map(DietTypeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    private User getUserEntityById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
    }

    @Transactional(readOnly = true)
    public UserDetailsDTO getUserByUserName(String userName) {
        User user = userRepository.findByUserNameRegex(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + userName));
        return UserMapper.toUserDetailsDTO(user);
    }

    @Transactional
    public Set<GeneralRecipeViewDTO> addFavoriteRecipe(long id, long recipeId) {
        User user = getUserEntityById(id);
        user.getFavoriteRecipes().add(generalRecipeService.getGeneralRecipeEntityById(recipeId));
        userRepository.save(user);

        return user.getFavoriteRecipes().stream()
                .map(GeneralRecipeMapper::toGeneralRecipeViewDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<DietTypeDTO> addFavoriteDiet(long userId, long dietId) {
        User user = getUserEntityById(userId);
        user.getPreferredDiets().add(dietTypeService.getDietTypeEntityById(dietId));
        userRepository.save(user);

        return user.getPreferredDiets().stream()
                .map(DietTypeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<GeneralRecipeViewDTO> deleteFavoriteRecipe(long userId, long recipeId) {
        User user = getUserEntityById(userId);
        user.getFavoriteRecipes().remove(generalRecipeService.getGeneralRecipeEntityById(recipeId));
        userRepository.save(user);

        return user.getFavoriteRecipes().stream()
                .map(GeneralRecipeMapper::toGeneralRecipeViewDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<DietTypeDTO> deleteFavoriteDiet(long userId, long dietId) {
        User user = getUserEntityById(userId);
        user.getPreferredDiets().remove(dietTypeService.getDietTypeEntityById(dietId));
        userRepository.save(user);

        return user.getPreferredDiets().stream()
                .map(DietTypeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public UserDetailsDTO updateUser(long userId, PutUserDTO dto) {
        User user = getUserEntityById(userId);
        UserMapper.updateUser(user, dto);
        userRepository.save(user);
        return UserMapper.toUserDetailsDTO(user);
    }
}
