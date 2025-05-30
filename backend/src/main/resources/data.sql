
INSERT INTO address(
    id,
    province,
    municipio,
    localidad,
    street,
    number,
    phone_number,
    latitude,
    longitude
)
VALUES
    ( 0, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    ( 1, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    ( 2, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    ( 3, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    ( 4, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Burger King
    ( 5, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Burger King
    ( 6, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Burger King
    ( 7, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Mostaza
    ( 8, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Cliente
    ( 9, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Cliente
    (10, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0); -- Cliente


INSERT INTO restaurant(
    id,
    auth0_id,
    email,
    legal_address,
    name,
    registration_date
) VALUES
    (0, 'auth0|683912632b6ad634b969db58', 'admin@mcdonalds.com', 'some address 0', 'McDonalds', NOW()),
    (1, 'auth0|6839127897763c7ab8c97853', 'admin@burgerking.com', 'some address 1', 'Burger King', NOW()),
    (2, 'auth0|6839128a2b6ad634b969db5b', 'admin@mostaza.com', 'some address 2', 'Mostaza', NOW());


INSERT INTO location(
    id,
    location_name,
    restaurant_id,
    address_id,
    is_open
) VALUES
    (0, 'McDonald''s San Martin 20', 0, 0, true),
    (1, 'McDonald''s Terminal', 0, 1, true),
    (2, 'McDonald''s Las Heras 200', 0, 2, false),
    (3, 'McDonald''s Palmares', 0, 3, true),
    (4, 'Burger King San Martín 400', 1, 4, true),
    (5, 'Burger King Barraca Mall', 1, 5, false),
    (6, 'Burger King Palmares', 1, 6, true),
    (7, 'Mostaza San Martín 100', 2, 7, true);
