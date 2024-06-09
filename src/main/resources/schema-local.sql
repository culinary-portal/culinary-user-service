CREATE TABLE IF NOT EXISTS general_recipe
(
    general_recipe_id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR,
    is_breakfast
    BOOLEAN,
    is_dinner
    BOOLEAN,
    is_lunch
    BOOLEAN,
    is_supper
    BOOLEAN
);

CREATE TABLE IF NOT EXISTS user
(
    user_id
    SERIAL
    PRIMARY
    KEY,
    user_name
    VARCHAR,
    email
    VARCHAR,
    password
    VARCHAR,
    birthdate
    TIMESTAMP,
    create_date
    TIMESTAMP,
    pref_is_vegan
    BOOLEAN,
    pref_is_gluten_free
    BOOLEAN,
    account_enabled
    BOOLEAN,
    account_expired
    BOOLEAN,
    account_locked
    BOOLEAN,
    credentials_expired
    BOOLEAN
);

CREATE TABLE IF NOT EXISTS roles
(
    role_id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INTEGER
    NOT
    NULL,
    role
    VARCHAR
(
    255
) CHECK
(
    role
    IN
(
    'ADMIN',
    'ANONYMOUS',
    'USER'
)),
    CONSTRAINT fk_user_roles FOREIGN KEY
(
    user_id
) REFERENCES user
(
    user_id
)
    );

CREATE TABLE IF NOT EXISTS ingredient
(
    ingredient_id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR,
    fat
    FLOAT,
    protein
    FLOAT,
    carbohydrate
    FLOAT,
    kcal
    FLOAT,
    is_vegan
    BOOLEAN,
    is_gluten_free
    BOOLEAN
);

CREATE TABLE IF NOT EXISTS recipe
(
    recipe_id
    SERIAL
    PRIMARY
    KEY,
    general_recipe_id
    INTEGER,
    name
    VARCHAR,
    description
    VARCHAR,
    diet_type
    VARCHAR,
    CONSTRAINT
    fk_general_recipe
    FOREIGN
    KEY
(
    general_recipe_id
) REFERENCES general_recipe
(
    general_recipe_id
)
    );

CREATE TABLE IF NOT EXISTS contains
(
    contains_id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR,
    recipe_id
    INTEGER,
    ingredient_id
    INTEGER,
    CONSTRAINT
    fk_recipe_contains
    FOREIGN
    KEY
(
    recipe_id
) REFERENCES recipe
(
    recipe_id
),
    CONSTRAINT fk_contains_ingredient FOREIGN KEY
(
    ingredient_id
) REFERENCES ingredient
(
    ingredient_id
)
    );

CREATE TABLE IF NOT EXISTS substitute
(
    substitute_id
    SERIAL
    PRIMARY
    KEY,
    ingredient1_id
    INTEGER,
    ingredient2_id
    INTEGER,
    proportion_i1_i2
    FLOAT,
    CONSTRAINT
    fk_ingredient1
    FOREIGN
    KEY
(
    ingredient1_id
) REFERENCES ingredient
(
    ingredient_id
),
    CONSTRAINT fk_ingredient2 FOREIGN KEY
(
    ingredient2_id
) REFERENCES ingredient
(
    ingredient_id
)
    );

CREATE TABLE IF NOT EXISTS review
(
    review_id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INTEGER,
    recipe_id
    INTEGER,
    rating
    INTEGER,
    opinion
    VARCHAR,
    CONSTRAINT
    fk_review_user
    FOREIGN
    KEY
(
    user_id
) REFERENCES user
(
    user_id
),
    CONSTRAINT fk_review_recipe FOREIGN KEY
(
    recipe_id
) REFERENCES recipe
(
    recipe_id
)
    );

CREATE TABLE IF NOT EXISTS favorites
(
    favorites_id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INTEGER,
    recipe_id
    INTEGER,
    CONSTRAINT
    fk_favorites_user
    FOREIGN
    KEY
(
    user_id
) REFERENCES user
(
    user_id
),
    CONSTRAINT fk_favorites_recipe FOREIGN KEY
(
    recipe_id
) REFERENCES recipe
(
    recipe_id
)
    );

CREATE TABLE IF NOT EXISTS specific
(
    specific_id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INTEGER,
    id_ingredient
    INTEGER,
    likes
    BOOLEAN,
    CONSTRAINT
    fk_specific_user
    FOREIGN
    KEY
(
    user_id
) REFERENCES user
(
    user_id
),
    CONSTRAINT fk_specific_ingredient FOREIGN KEY
(
    id_ingredient
) REFERENCES ingredient
(
    ingredient_id
)
    );

CREATE TABLE IF NOT EXISTS diet_type
(
    diet_type_id
    SERIAL
    PRIMARY
    KEY,
    diet_type
    VARCHAR
);
