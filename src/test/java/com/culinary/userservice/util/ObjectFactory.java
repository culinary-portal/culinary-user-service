package com.culinary.userservice.util;

import com.culinary.userservice.user.model.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;

public class ObjectFactory {

    public static User createTestUser() {
        User user = new User();
        user.setUserName("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setBirthdate(Date.valueOf("1995-07-08"));
        user.setCreateDate(Date.valueOf("1995-07-08"));
        user.setPhotoUrl("http://example.com/photo.jpg");
        user.setSpecifics(new ArrayList<>());
        user.setFavoriteRecipes(new HashSet<>());
        user.setPreferredDiets(new HashSet<>());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setLocked(false);
        user.setCredentialsNonExpired(true);
        return user;
    }
}
