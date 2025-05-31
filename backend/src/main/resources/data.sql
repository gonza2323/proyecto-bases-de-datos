
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
    (1000, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    (1001, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    (1002, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    (1003, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- McDonald's
    (1004, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Burger King
    (1005, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Burger King
    (1006, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Burger King
    (1007, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Mostaza
    (1008, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Cliente
    (1009, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0), -- Cliente
    (1010, 'Mendoza', 'Capital', '1a sección', '9 de Julio', '1551', '4286495', 0, 0); -- Cliente


INSERT INTO restaurant(
    id,
    auth0_id,
    email,
    legal_address,
    name,
    registration_date
) VALUES
    (1000, 'auth0|683912632b6ad634b969db58', 'admin@mcdonalds.com', 'some address 0', 'McDonalds', NOW()),
    (1001, 'auth0|6839127897763c7ab8c97853', 'admin@burgerking.com', 'some address 1', 'Burger King', NOW()),
    (1002, 'auth0|6839128a2b6ad634b969db5b', 'admin@mostaza.com', 'some address 2', 'Mostaza', NOW());


INSERT INTO location(
    id,
    location_name,
    restaurant_id,
    address_id,
    is_open
) VALUES
    (1000, 'McDonald''s San Martin 20', 1000, 1000, true),
    (1001, 'McDonald''s Terminal', 1000, 1001, true),
    (1002, 'McDonald''s Las Heras 200', 1000, 1002, false),
    (1003, 'McDonald''s Palmares', 1000, 1003, true),
    (1004, 'Burger King San Martín 400', 1001, 1004, true),
    (1005, 'Burger King Barraca Mall', 1001, 1005, false),
    (1006, 'Burger King Palmares', 1001, 1006, true),
    (1007, 'Mostaza San Martín 100', 1002, 1007, true);

