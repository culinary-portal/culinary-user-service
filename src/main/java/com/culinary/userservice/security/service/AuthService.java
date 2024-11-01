package com.culinary.userservice.security.service;

import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.security.dto.RegisterDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.enumeration.RoleEnum;
import com.culinary.userservice.user.exception.UserAlreadyExistsException;
import com.culinary.userservice.user.exception.UserNotFoundException;
import com.culinary.userservice.user.mapper.UserMapper;
import com.culinary.userservice.user.model.Role;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import com.culinary.userservice.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
@Setter
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final AuthenticationManager authManager;
    private final RedisIndexedSessionRepository redisIndexedSessionRepository;
    private final SessionRegistry sessionRegistry;
    private final UserRepository userRepository;
    private final UserService userService;
    @Value(value = "${custom.max.session}")
    private int maxSession;
    @Value(value = "${admin.email}")
    private String adminEmail;
    @Value(value = "${default.user.photo.url}")
    private String defaultUserPhotoUrl;

    public AuthService(PasswordEncoder passwordEncoder,
                       SecurityContextRepository securityContextRepository,
                       AuthenticationManager authManager,
                       RedisIndexedSessionRepository redisIndexedSessionRepository,
                       SessionRegistry sessionRegistry,
                       UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.securityContextRepository = securityContextRepository;
        this.authManager = authManager;
        this.redisIndexedSessionRepository = redisIndexedSessionRepository;
        this.sessionRegistry = sessionRegistry;
        this.userRepository = userRepository;
        this.securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        this.userService = userService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext context = securityContextHolderStrategy.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null) {
            securityContextHolderStrategy.clearContext();
            // Invalidate the session
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            securityContextRepository.saveContext(SecurityContextHolder.createEmptyContext(), request, response);
        }
    }


    public UserDetailsDTO register(RegisterDTO dto) {
        String email = dto.email().trim();

        Optional<User> exists = userRepository
                .findByEmail(email);

        Optional<User> existsByUsername = userRepository
                .findByUserNameRegex(dto.username().trim());

        if (exists.isPresent() || existsByUsername.isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        var user = new User();
        user.setEmail(email);
        user.setUserName(dto.username().trim());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setLocked(false);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.addRole(new Role(RoleEnum.USER));
        user.setBirthdate(new Date(System.currentTimeMillis()));
        user.setCreateDate(new Date(System.currentTimeMillis()));
        user.setReviews(new ArrayList<>());
        user.setFavoriteRecipes(new HashSet<>());
        user.setSpecifics(new ArrayList<>());
        user.setPreferredDiets(new HashSet<>());
        user.setPhotoUrl(defaultUserPhotoUrl);

        if (adminEmail != null && adminEmail.equals(email)) {
            user.addRole(new Role(RoleEnum.ADMIN));
        }

        userRepository.save(user);
        return UserMapper.toUserDetailsDTO(user);
    }

    @Transactional
    public UserDetailsDTO login(AuthDTO dto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                dto.email().trim(), dto.password()));

        validateMaxSession(authentication);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);

        return UserMapper.toUserDetailsDTO(userService.getLoggedUser());
    }

    public String changePassword(HttpServletRequest request, HttpServletResponse response,
                                 Long id, String password) {
        String newPassword = password.trim();

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
        logout(request, response);
        return "Password changed successfully!";
    }

    private void validateMaxSession(Authentication authentication) {
        if (maxSession <= 0) {
            return;
        }

        var principal = (UserDetails) authentication.getPrincipal();
        List<SessionInformation> sessions = this.sessionRegistry.getAllSessions(principal, false);

        if (sessions.size() >= maxSession) {
            sessions.stream()
                    .min(Comparator.comparing(SessionInformation::getLastRequest))
                    .ifPresent(sessionInfo -> this.redisIndexedSessionRepository.deleteById(sessionInfo.getSessionId()));
        }
    }

}
