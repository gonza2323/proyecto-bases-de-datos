
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
    (1000, 'McDonald''s Palmares', 1000, 1003, true),
    (1001, 'McDonald''s Terminal', 1000, 1001, true),
    (1002, 'McDonald''s Las Heras 200', 1000, 1002, false),
    (1003, 'McDonald''s San Martin 20', 1000, 1000, true),
    (1004, 'Burger King San Martín 400', 1001, 1004, true),
    (1005, 'Burger King Barraca Mall', 1001, 1005, false),
    (1006, 'Burger King Palmares', 1001, 1006, true),
    (1007, 'Mostaza San Martín 100', 1002, 1007, true);


INSERT INTO menu_item_category(id, name) VALUES
      (0, 'Hamburguesa'),
      (1, 'Pizza'),
      (2, 'Pasta'),
      (3, 'Bebida s/ alcohol'),
      (4, 'Bebida alcohólica'),
      (5, 'Comida china'),
      (6, 'Comida árabe'),
      (7, 'Sushi'),
      (8, 'Postre'),
      (9, 'Acompañamiento'),
      (10, 'Vegetariana'),
      (11, 'Vegana');


INSERT INTO menu_item(
    id,
    location_id,
    category_id,
    name,
    description,
    price,
    available
) VALUES
      (1000, 1000, 0, 'BigMac', 'Hamburguesa con pepino, queso y lechuga', 6000, true),
      (1001, 1000, 0, 'Cuarto de Libra', 'Hamburguesa con queso', 5000, true),
      (1002, 1000, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 3000, true),
      (1003, 1000, 3, 'Coca Cola Mediana', 'Bebida azucarada', 2000, true),

      (1004, 1001, 0, 'BigMac', 'Hamburguesa con pepino, queso y lechuga', 6000, true),
      (1005, 1001, 0, 'Cuarto de Libra', 'Hamburguesa con queso', 5000, true),
      (1006, 1001, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 3000, true),
      (1007, 1001, 3, 'Coca Cola Mediana', 'Bebida azucarada', 2000, true),

      (1008, 1002, 0, 'BigMac', 'Hamburguesa con pepino, queso y lechuga', 6000, true),
      (1009, 1002, 0, 'Cuarto de Libra', 'Hamburguesa con queso', 5000, true),
      (1010, 1002, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 3000, true),
      (1011, 1002, 3, 'Coca Cola Mediana', 'Bebida azucarada', 2000, true),

      (1012, 1003, 0, 'BigMac', 'Hamburguesa con pepino, queso y lechuga', 6000, true),
      (1013, 1003, 0, 'Cuarto de Libra', 'Hamburguesa con queso', 5000, true),
      (1014, 1003, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 3000, true),
      (1015, 1003, 3, 'Coca Cola Mediana', 'Bebida azucarada', 2000, true),
      (1016, 1003, 3, 'Café Mediano', 'Café Americano Mediano', 1500, true),
      (1017, 1003, 8, 'Medialuna', 'Medialuna con dulce', 800, true),

      (1018, 1004, 0, 'Whooper', 'Hamburguesa con pepino, tomate y lechuga', 8000, true),
      (1019, 1004, 0, 'Triple Bacon', 'Hamburguesa con queso y panceta', 9000, true),
      (1020, 1004, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 3500, true),
      (1021, 1004, 3, 'Pepsi Mediana', 'Bebida azucarada', 2100, true),

      (1022, 1005, 0, 'Whooper', 'Hamburguesa con pepino, tomate y lechuga', 8000, true),
      (1023, 1005, 0, 'Triple Bacon', 'Hamburguesa con queso y panceta', 9000, true),
      (1024, 1005, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 3500, true),
      (1025, 1005, 3, 'Pepsi Mediana', 'Bebida azucarada', 2100, true),

      (1026, 1006, 0, 'Whooper', 'Hamburguesa con pepino, tomate y lechuga', 8000, true),
      (1027, 1006, 0, 'Triple Bacon', 'Hamburguesa con queso y panceta', 9000, true),
      (1028, 1006, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 3500, true),
      (1029, 1006, 3, 'Pepsi Mediana', 'Bebida azucarada', 2100, true),

      (1030, 1007, 0, 'Dibu', 'La hamburguesa del Dibu', 6000, true),
      (1031, 1007, 0, 'Hamburguesa con queso', 'Hamburguesa con queso y panceta', 5000, true),
      (1032, 1007, 9, 'Papas Fritas Medianas', 'Papas fritas medianas', 2900, true),
      (1033, 1007, 3, 'Pepsi Mediana', 'Bebida azucarada', 1900, true);


