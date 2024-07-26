INSERT INTO diet_type (diet_type)
VALUES ('Vegetarian'),
       ('Low-Carb'),
       ('Keto');

INSERT INTO general_recipe (name, is_breakfast, is_dinner, is_lunch, is_supper)
VALUES ('Pancakes', TRUE, FALSE, TRUE, FALSE),
       ('Chicken Salad', FALSE, TRUE, TRUE, FALSE),
       ('Steak Dinner', FALSE, TRUE, FALSE, TRUE);

INSERT INTO user (user_name, email, password, birthdate, create_date, photo_url, pref_is_vegan, pref_is_gluten_free, account_enabled, account_expired, account_locked, credentials_expired)
VALUES ('JohnDoe', 'john.doe@example.com', 'password123', '1990-05-15', NOW(),'http://example.com/user.jpg', FALSE, TRUE, TRUE, FALSE, FALSE, FALSE),
       ('JaneSmith', 'jane.smith@example.com', 'securePass!', '1985-10-20', NOW(), 'http://example.com/user.jpg', TRUE, FALSE, TRUE, FALSE, FALSE, FALSE),
       ('AliceJones', 'alice.jones@example.com', 'alice1234', '1995-07-08', NOW(), 'http://example.com/user.jpg', FALSE, FALSE, TRUE, FALSE, FALSE, FALSE);

INSERT INTO roles (user_id, role)
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'USER');

INSERT INTO ingredient (name, fat, protein, carbohydrate, kcal, is_vegan, is_gluten_free)
VALUES ('Butter', 81.0, 0.85, 0.06, 717, FALSE, TRUE),
       ('Chicken Breast', 3.6, 31, 0, 165, FALSE, TRUE),
       ('Lettuce', 0.2, 1.4, 2.9, 15, TRUE, TRUE);

INSERT INTO recipe (general_recipe_id, name, photo_url, description, diet_type_id)
VALUES (1, 'Pancakes with Syrup', 'http://example.com/pancakes.jpg', 'Fluffy pancakes with maple syrup', 1),
       (2, 'Classic Chicken Salad', 'http://example.com/chicken_salad.jpg', 'Chicken salad with lettuce and dressing', 2),
       (3, 'Grilled Steak', 'http://example.com/steak.jpg', 'Juicy grilled steak with herbs', 3);

INSERT INTO contains (amount, measure, recipe_id, ingredient_id)
VALUES (123, 'grams', 1, 1),
       (125, 'grams', 2, 2),
       (185, 'millilitre', 3, 3);

INSERT INTO substitute (ingredient1_id, ingredient2_id, proportion_i1_i2)
VALUES (1, 3, 0.5),
       (2, 1, 1.5),
       (3, 2, 1.0);

INSERT INTO review (user_id, recipe_id, rating, opinion)
VALUES (1, 1, 5, 'Delicious pancakes!'),
       (2, 2, 4, 'Healthy and tasty.'),
       (3, 3, 5, 'Perfectly cooked steak.');

INSERT INTO favorites (user_id, recipe_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO specific (user_id, id_ingredient, likes)
VALUES (1, 1, TRUE),
       (2, 2, FALSE),
       (3, 3, TRUE);

