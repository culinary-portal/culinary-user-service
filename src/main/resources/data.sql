INSERT INTO diet_type (diet_type)
VALUES ('Vegetarian'),
       ('Low-Carb'),
       ('Keto');

INSERT INTO general_recipe (name, photo_url, meal_type, description, steps)
VALUES ('Pancakes', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwTiXV0M8mopHLJb04RX9mYhbEYyb4HMrXHA&s', 'BREAKFAST', 'Fluffy pancakes with maple syrup', '1. Mix flour, sugar, baking powder, and salt. 2. Add milk, egg, and butter. 3. Cook on a hot griddle.'),
       ('Chicken Salad', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQg4ZN2YYCK7GI7haQOrVFzCMMHvG5_5wuXvg&s', 'LUNCH', 'Chicken salad with lettuce and dressing', '1. Grill chicken. 2. Mix with lettuce and dressing.'),
       ('Steak Dinner', 'https://jesspryles.com/wp-content/uploads/2020/04/untitled-2.jpg', 'DINNER', 'Juicy grilled steak with herbs', '1. Season steak. 2. Grill to desired doneness.');

INSERT INTO "user" (user_name, email, password, birthdate, create_date, photo_url, account_enabled, account_expired,
                  account_locked, credentials_expired)
VALUES ('JohnDoe', 'john.doe@example.com', 'password123', '1990-05-15', NOW(), 'N/A', TRUE,
        FALSE, FALSE, FALSE),
       ('JaneSmith', 'jane.smith@example.com', 'securePass!', '1985-10-20', NOW(), 'N/A', TRUE,
        FALSE, FALSE, FALSE),
       ('AliceJones', 'alice.jones@example.com', 'alice1234', '1995-07-08', NOW(), 'N/A', TRUE,
        FALSE, FALSE, FALSE);

INSERT INTO user_diet_type (user_id, diet_type_id)
VALUES ((SELECT user_id FROM "user" WHERE user_name = 'JohnDoe'),
        (SELECT diet_type_id FROM diet_type WHERE diet_type = 'Low-Carb')),
       ((SELECT user_id FROM "user" WHERE user_name = 'JaneSmith'),
        (SELECT diet_type_id FROM diet_type WHERE diet_type = 'Vegetarian')),
       ((SELECT user_id FROM "user" WHERE user_name = 'JaneSmith'),
        (SELECT diet_type_id FROM diet_type WHERE diet_type = 'Keto')),
       ((SELECT user_id FROM "user" WHERE user_name = 'AliceJones'),
        (SELECT diet_type_id FROM diet_type WHERE diet_type = 'Vegetarian'));

INSERT INTO roles (user_id, role)
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'USER');

INSERT INTO ingredient (name, fat, protein, carbohydrate, kcal, is_vegan, is_gluten_free)
VALUES ('Butter', 81.0, 0.85, 0.06, 717, FALSE, TRUE),
       ('Chicken Breast', 3.6, 31, 0, 165, FALSE, TRUE),
       ('Lettuce', 0.2, 1.4, 2.9, 15, TRUE, TRUE);

INSERT INTO recipe (general_recipe_id, name, description, diet_type_id)
VALUES (1, 'Pancakes with Syrup', 'Fluffy pancakes with maple syrup', 1),
       (2, 'Classic Chicken Salad', 'Chicken salad with lettuce and dressing', 2),
       (3, 'Grilled Steak', 'Juicy grilled steak with herbs', 3);

INSERT INTO contains (amount, measure, recipe_id, ingredient_id)
VALUES (123, 'grams', 1, 1),
       (125, 'grams', 2, 2),
       (185, 'millilitre', 3, 3);

INSERT INTO substitute (ingredient1_id, ingredient2_id, proportion_i1_i2)
VALUES (1, 3, 0.5),
       (2, 1, 1.5),
       (3, 2, 1.0);

INSERT INTO review (user_id, general_recipe_id, rating, opinion)
VALUES (1, 1, 5, 'Delicious pancakes!'),
       (2, 2, 4, 'Healthy and tasty.'),
       (3, 3, 5, 'Perfectly cooked steak.');

INSERT INTO favorite (user_id, general_recipe_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO modified_recipe (user_id, recipe_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO specific (user_id, id_ingredient, likes)
VALUES (1, 1, TRUE),
       (2, 2, FALSE),
       (3, 3, TRUE);


UPDATE general_recipe
SET base_recipe_id = 1
WHERE general_recipe_id = 1;


UPDATE general_recipe
SET base_recipe_id = 2
WHERE general_recipe_id = 2;


UPDATE general_recipe
SET base_recipe_id = 3
WHERE general_recipe_id = 3;

