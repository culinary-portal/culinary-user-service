package com.culinary.userservice.user.model;


import com.culinary.userservice.ingredient.model.Specific;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.model.Review;
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
    @Lob
    @Column(name = "photo_url", nullable = false)
    private String photoUrl;
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
    private List<Specific> specifics;
    @ManyToMany
    @JoinTable(
            name = "user_diet_type",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "diet_type_id")
    )
    private Set<DietType> preferredDiets = new HashSet<>();
    @JsonIgnore
    @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER, mappedBy = "user", orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "general_recipe_id")
    )
    private Set<GeneralRecipe> favoriteRecipes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "modified_recipe",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> modifiedRecipes = new HashSet<>();



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
