package com.culinary.userservice.user.model;


import com.culinary.userservice.recipe.diet.Favorite;
import com.culinary.userservice.recipe.diet.Review;
import com.culinary.userservice.ingridient.Specific;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Table(name = "user")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "user_name")
    private String userName;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "account_enabled")
    private boolean enabled;
    @Column(name = "credentials_expired")
    private boolean credentialsNonExpired;
    @Column(name = "account_expired")
    private boolean accountNonExpired;
    @Column(name = "account_locked")
    private boolean locked;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column(name = "create_date")
    private Date createDate;
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;
    @OneToMany(mappedBy = "user")
    private List<Specific> specifics;
    @Column(name = "pref_is_vegan")
    private Boolean prefIsVegan;
    @Column(name = "pref_is_gluten_free")
    private Boolean prefIsGlutenFree;
    @JsonIgnore
    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER, mappedBy = "user", orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();


    public void addRole(Role role) {
        this.roles.add(role);
        role.setUser(this);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this
                .roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleEnum().toString()))
                .collect(Collectors.toSet());
    }

}
