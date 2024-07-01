package com.culinary.userservice.security.service;

import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.user.enumeration.RoleEnum;
import com.culinary.userservice.user.exception.UserAlreadyExistsException;
import com.culinary.userservice.user.model.Role;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.time.Instant;
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
    @Value(value = "${custom.max.session}")
    private int maxSession;
    @Value(value = "${admin.email}")
    private String adminEmail;

    public AuthService(PasswordEncoder passwordEncoder,
                       SecurityContextRepository securityContextRepository,
                       AuthenticationManager authManager,
                       RedisIndexedSessionRepository redisIndexedSessionRepository,
                       SessionRegistry sessionRegistry,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.securityContextRepository = securityContextRepository;
        this.authManager = authManager;
        this.redisIndexedSessionRepository = redisIndexedSessionRepository;
        this.sessionRegistry = sessionRegistry;
        this.userRepository = userRepository;
        this.securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    }

    public String register(AuthDTO dto) {
        String email = dto.email().trim();

        Optional<User> exists = userRepository
                .findByEmail(email);

        if (exists.isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        var user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.addRole(new Role(RoleEnum.USER));
        user.setBirthdate(Date.valueOf(String.valueOf(Instant.now())));
        user.setCreateDate(Date.valueOf(String.valueOf(Instant.now())));
        user.setReviews(new ArrayList<>());
        user.setFavorites(new ArrayList<>());
        user.setSpecifics(new ArrayList<>());
        user.setPrefIsVegan(false);
        user.setPrefIsGlutenFree(false);

        if (adminEmail != null && adminEmail.equals(email)) {
            user.addRole(new Role(RoleEnum.ADMIN));
        }

        userRepository.save(user);
        return "Registered successfully!";
    }


    public String login(AuthDTO dto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                dto.email().trim(), dto.password()));

        validateMaxSession(authentication);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        String sessionId = request.getSession().getId();

        return sessionId;
    }


    private void validateMaxSession(Authentication authentication) {
        if (maxSession <= 0) {
            return;
        }

        var principal = (UserDetails) authentication.getPrincipal();
        List<SessionInformation> sessions = this.sessionRegistry.getAllSessions(principal, false);

        if (sessions.size() >= maxSession) {
            sessions.stream() //
                    .min(Comparator.comparing(SessionInformation::getLastRequest)) //
                    .ifPresent(sessionInfo -> this.redisIndexedSessionRepository.deleteById(sessionInfo.getSessionId()));
        }
    }

}
