package com.culinary.userservice.user.model;

import com.culinary.userservice.ingredient.model.Specific;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.model.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Builder
@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "photo_url", nullable = false, columnDefinition = "clob")
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
    @JsonManagedReference
    private Set<GeneralRecipe> favoriteRecipes = new HashSet<>();

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "modified_recipe",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    @JsonManagedReference
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

    @Override
    public String toString() {
        return "User{" +
                "createDate=" + createDate +
                ", birthdate=" + birthdate +
                ", locked=" + locked +
                ", accountNonExpired=" + accountNonExpired +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", photoUrl='" + photoUrl + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && credentialsNonExpired == user.credentialsNonExpired
                && accountNonExpired == user.accountNonExpired && locked == user.locked
                && Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(userName, user.userName) && Objects.equals(password, user.password)
                && Objects.equals(photoUrl, user.photoUrl) && Objects.equals(birthdate, user.birthdate)
                && Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, userName, password, photoUrl, enabled,
                credentialsNonExpired, accountNonExpired, locked, birthdate, createDate);
    }
}