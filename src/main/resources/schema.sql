CREATE TABLE Recipe (
                        recipe_id SERIAL PRIMARY KEY,
                        general_recipe_id INTEGER,
                        name VARCHAR,
                        description VARCHAR,
                        dietType VARCHAR,
                        CONSTRAINT fk_general_recipe FOREIGN KEY (general_recipe_id) REFERENCES GeneralRecipe(id)
);

CREATE TABLE Contains (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR,
                          recipe_id INTEGER,
                          ingredient_id INTEGER,
                          CONSTRAINT fk_recipe_contains FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id),
                          CONSTRAINT fk_contains_ingredient FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id_ingredient)
);

CREATE TABLE Ingredient (
                            id_ingredient SERIAL PRIMARY KEY,
                            name VARCHAR,
                            fat FLOAT,
                            protein FLOAT,
                            carbohydrate FLOAT,
                            kcal FLOAT,
                            is_vegan BOOLEAN,
                            is_glutenfree BOOLEAN
);

CREATE TABLE Substitute (
                            id SERIAL PRIMARY KEY,
                            id_ingredient1 INTEGER,
                            id_ingredient2 INTEGER,
                            proportion_i1_i2 FLOAT,
                            CONSTRAINT fk_ingredient1 FOREIGN KEY (id_ingredient1) REFERENCES Ingredient(id_ingredient),
                            CONSTRAINT fk_ingredient2 FOREIGN KEY (id_ingredient2) REFERENCES Ingredient(id_ingredient)
);

CREATE TABLE Review (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER,
                        recipe_id INTEGER,
                        rating INTEGER,
                        opinion VARCHAR,
                        CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES "User"(id),
                        CONSTRAINT fk_review_recipe FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id)
);

CREATE TABLE "User" (
                        id SERIAL PRIMARY KEY,
                        user_name VARCHAR,
                        email VARCHAR,
                        password VARCHAR,
                        birthdate TIMESTAMP,
                        create_date TIMESTAMP,
                        pref_is_vegan BOOLEAN,
                        pref_is_glutenfree BOOLEAN
);

CREATE TABLE Favorites (
                           id SERIAL PRIMARY KEY,
                           user_id INTEGER,
                           recipe_id INTEGER,
                           CONSTRAINT fk_favorites_user FOREIGN KEY (user_id) REFERENCES "User"(id),
                           CONSTRAINT fk_favorites_recipe FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id)
);

CREATE TABLE Specific (
                          id SERIAL PRIMARY KEY,
                          user_id INTEGER,
                          ingredient_id INTEGER,
                          likes BOOLEAN,
                          CONSTRAINT fk_specific_user FOREIGN KEY (user_id) REFERENCES "User"(id),
                          CONSTRAINT fk_specific_ingredient FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id_ingredient)
);

CREATE TABLE GeneralRecipe (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR,
                               is_breakfast BOOLEAN,
                               is_dinner BOOLEAN,
                               is_lunch BOOLEAN,
                               is_supper BOOLEAN
);

CREATE TABLE DietType (
                          id SERIAL PRIMARY KEY,
                          diet_type VARCHAR
);