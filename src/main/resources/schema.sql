select current_database();

CREATE TABLE if not exists GeneralRecipe (
                               general_recipe_id SERIAL PRIMARY KEY,
                               name VARCHAR,
                               is_breakfast BOOLEAN,
                               is_dinner BOOLEAN,
                               is_lunch BOOLEAN,
                               is_supper BOOLEAN
                                        );

CREATE TABLE if not exists "User" (
                        user_id SERIAL PRIMARY KEY,
                        user_name VARCHAR,
                        email VARCHAR,
                        password VARCHAR,
                        birthdate TIMESTAMP,
                        create_date TIMESTAMP,
                        pref_is_vegan BOOLEAN,
                        pref_is_gluten_free BOOLEAN,
                        account_enable BOOLEAN,
                        account_expired BOOLEAN,
                        account_locked BOOLEAN,
                        credentials_expired BOOLEAN
                    );

CREATE TABLE if not exists Roles (
                    role_id  SERIAL PRIMARY KEY,
                    user_id INTEGER not null,
                    role varchar(255) check (role in ('ADMIN','ANONYMOUS','USER')),
                    CONSTRAINT role_customer_fk foreign key (user_id)  references "User"(user_id)
    );


CREATE TABLE if not exists Ingredient (
                            ingredient_id SERIAL PRIMARY KEY,
                            name VARCHAR,
                            fat FLOAT,
                            protein FLOAT,
                            carbohydrate FLOAT,
                            kcal FLOAT,
                            is_vegan BOOLEAN,
                            is_gluten_free BOOLEAN
);

CREATE TABLE if not exists Recipe (
                        recipe_id SERIAL PRIMARY KEY,
                        general_recipe_id INTEGER,
                        name VARCHAR,
                        description VARCHAR,
                        dietType VARCHAR,
                        CONSTRAINT fk_general_recipe FOREIGN KEY (general_recipe_id) REFERENCES GeneralRecipe(general_recipe_id)
);

CREATE TABLE if not exists Contains (
                          contains_id SERIAL PRIMARY KEY,
                          name VARCHAR,
                          recipe_id INTEGER,
                          ingredient_id INTEGER,
                          CONSTRAINT fk_recipe_contains FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id),
                          CONSTRAINT fk_contains_ingredient FOREIGN KEY (ingredient_id) REFERENCES Ingredient(ingredient_id)
);

CREATE TABLE if not exists Substitute (
                            substitude_id SERIAL PRIMARY KEY,
                            ingredient1_id INTEGER,
                            ingredient2_id INTEGER,
                            proportion_i1_i2 FLOAT,
                            CONSTRAINT fk_ingredient1 FOREIGN KEY (ingredient1_id) REFERENCES Ingredient(ingredient_id),
                            CONSTRAINT fk_ingredient2 FOREIGN KEY (ingredient2_id) REFERENCES Ingredient(ingredient_id)
);

CREATE TABLE if not exists Review (
                        review_id SERIAL PRIMARY KEY,
                        user_id INTEGER,
                        recipe_id INTEGER,
                        rating INTEGER,
                        opinion VARCHAR,
                        CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES "User"(user_id),
                        CONSTRAINT fk_review_recipe FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id)
);


CREATE TABLE if not exists Favorites (
                           favourites_id SERIAL PRIMARY KEY,
                           user_id INTEGER,
                           recipe_id INTEGER,
                           CONSTRAINT fk_favorites_user FOREIGN KEY (user_id) REFERENCES "User"(user_id),
                           CONSTRAINT fk_favorites_recipe FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id)
);



CREATE TABLE if not exists Specific (
                          specific_id SERIAL PRIMARY KEY,
                          user_id INTEGER,
                          id_ingredient INTEGER,
                          likes BOOLEAN,
                          CONSTRAINT fk_specific_user FOREIGN KEY (user_id) REFERENCES "User"(user_id),
                          CONSTRAINT fk_specific_ingredient FOREIGN KEY (id_ingredient) REFERENCES Ingredient(ingredient_id)
);


CREATE TABLE if not exists Diet_type (
                          diet_type_id SERIAL PRIMARY KEY,
                          diet_type VARCHAR
);



