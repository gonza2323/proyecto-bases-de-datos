
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


INSERT INTO customer(
     id,
     email,
     first_name,
     last_name,
     date_of_birth,
     registration_date
) VALUES
      (1000, 'Gonzalo@gmail.com', 'Gonzalo', 'Pérez', NOW(), NOW()),
      (1001, 'Agustin@gmail.com', 'Agustín', 'Fernandez', NOW(), NOW()),
      (1002, 'Tomas@gmail.com', 'Tomás', 'García', NOW(), NOW()),
      (1003, 'Pedro@gmail.com', 'Pedro', 'Lorca', NOW(), NOW()),
      (1004, 'Agustina@gmail.com', 'Agustina', 'Torres', NOW(), NOW()),
      (1005, 'Lucia@gmail.com', 'Lucía', 'Menendez', NOW(), NOW()),
      (1006, 'Jose@gmail.com', 'José', 'Nisman', NOW(), NOW()),
      (1007, 'John@gmail.com', 'John', 'Kennedy', NOW(), NOW()),
      (1008, 'Mirian@gmail.com', 'Mirian', 'Pereyra', NOW(), NOW()),
      (1009, 'Lucas@gmail.com', 'Lucas', 'García', NOW(), NOW());


INSERT INTO "order"(
    id,
    location_id,
    customer_id,
    state
) VALUES

-- 5 ordenes por restaurante, para tener suficientes reseñas

      (1000, 1000, 1000, 'DELIVERED'),
      (1001, 1000, 1000, 'DELIVERED'),
      (1002, 1000, 1000, 'DELIVERED'),
      (1003, 1000, 1000, 'DELIVERED'),
      (1004, 1000, 1000, 'DELIVERED'),

      (1005, 1001, 1000, 'DELIVERED'),
      (1006, 1001, 1000, 'DELIVERED'),
      (1007, 1001, 1000, 'DELIVERED'),
      (1008, 1001, 1000, 'DELIVERED'),
      (1009, 1001, 1000, 'DELIVERED'),

      (1010, 1002, 1000, 'DELIVERED'),
      (1011, 1002, 1000, 'DELIVERED'),
      (1012, 1002, 1000, 'DELIVERED'),
      (1013, 1002, 1000, 'DELIVERED'),
      (1014, 1002, 1000, 'DELIVERED'),

      (1015, 1003, 1000, 'DELIVERED'),
      (1016, 1003, 1000, 'DELIVERED'),
      (1017, 1003, 1000, 'DELIVERED'),
      (1018, 1003, 1000, 'DELIVERED'),
      (1019, 1003, 1000, 'DELIVERED'),

      (1020, 1004, 1000, 'DELIVERED'),
      (1021, 1004, 1000, 'DELIVERED'),
      (1022, 1004, 1000, 'DELIVERED'),
      (1023, 1004, 1000, 'DELIVERED'),
      (1024, 1004, 1000, 'DELIVERED'),

      (1025, 1005, 1000, 'DELIVERED'),
      (1026, 1005, 1000, 'DELIVERED'),
      (1027, 1005, 1000, 'DELIVERED'),
      (1028, 1005, 1000, 'DELIVERED'),
      (1029, 1005, 1000, 'DELIVERED'),

      (1030, 1006, 1000, 'DELIVERED'),
      (1031, 1006, 1000, 'DELIVERED'),
      (1032, 1006, 1000, 'DELIVERED'),
      (1033, 1006, 1000, 'DELIVERED'),
      (1034, 1006, 1000, 'DELIVERED'),

      (1035, 1007, 1000, 'DELIVERED'),
      (1036, 1007, 1000, 'DELIVERED'),
      (1037, 1007, 1000, 'DELIVERED'),
      (1038, 1007, 1000, 'DELIVERED'),
      (1039, 1007, 1000, 'DELIVERED'),

-- Ordenes en progreso
      (1040, 1000, 1000, 'PENDING'),
      (1041, 1000, 1000, 'PREPARING'),
      (1042, 1000, 1000, 'PREPARED'),
      (1043, 1000, 1000, 'TRANSIT'),
      (1044, 1000, 1000, 'TRANSIT'),

-- Carritos de los clientes
      (1045, null, 1000, 'CART'),
      (1046, null, 1001, 'CART'),
      (1047, null, 1002, 'CART'),
      (1048, null, 1003, 'CART'),
      (1049, null, 1004, 'CART'),
      (1050, null, 1005, 'CART'),
      (1051, null, 1006, 'CART'),
      (1052, null, 1007, 'CART'),
      (1053, null, 1008, 'CART'),
      (1054, null, 1009, 'CART');


INSERT INTO review(
                   id,
                   location_id,
                   rating,
                   customer_id,
                   order_id,
                   text
) VALUES
      (1000, 1000, 2, 1000, 1000, 'some text'),
      (1001, 1000, 3, 1000, 1001, 'some text'),
      (1002, 1000, 1, 1000, 1002, 'some text'),
      (1003, 1000, 2, 1000, 1003, 'some text'),
      (1004, 1000, 3, 1000, 1004, 'some text'),

      (1005, 1001, 5, 1000, 1005, 'some text'),
      (1006, 1001, 5, 1000, 1006, 'some text'),
      (1007, 1001, 4, 1000, 1007, 'some text'),
      (1008, 1001, 4, 1000, 1008, 'some text'),
      (1009, 1001, 5, 1000, 1009, 'some text'),

      (1010, 1002, 5, 1000, 1010, 'some text'),
      (1011, 1002, 4, 1000, 1011, 'some text'),
      (1012, 1002, 2, 1000, 1012, 'some text'),
      (1013, 1002, 5, 1000, 1013, 'some text'),
      (1014, 1002, 4, 1000, 1014, 'some text'),

      (1015, 1003, 1, 1000, 1015, 'some text'),
      (1016, 1003, 3, 1000, 1016, 'some text'),
      (1017, 1003, 5, 1000, 1017, 'some text'),
      (1018, 1003, 4, 1000, 1018, 'some text'),
      (1019, 1003, 3, 1000, 1019, 'some text'),

      (1020, 1004, 5, 1000, 1020, 'some text'),
      (1021, 1004, 4, 1000, 1021, 'some text'),
      (1022, 1004, 3, 1000, 1022, 'some text'),
      (1023, 1004, 5, 1000, 1023, 'some text'),
      (1024, 1004, 4, 1000, 1024, 'some text'),

      (1025, 1005, 1, 1000, 1025, 'some text'),
      (1026, 1005, 2, 1000, 1026, 'some text'),
      (1027, 1005, 1, 1000, 1027, 'some text'),
      (1028, 1005, 3, 1000, 1028, 'some text'),
      (1029, 1005, 3, 1000, 1029, 'some text'),

      (1030, 1006, 3, 1000, 1030, 'some text'),
      (1031, 1006, 5, 1000, 1031, 'some text'),
      (1032, 1006, 3, 1000, 1032, 'some text'),
      (1033, 1006, 5, 1000, 1033, 'some text'),
      (1034, 1006, 4, 1000, 1034, 'some text'),

      (1035, 1007, 5, 1000, 1035, 'some text'),
      (1036, 1007, 4, 1000, 1036, 'some text'),
      (1037, 1007, 1, 1000, 1037, 'some text'),
      (1038, 1007, 2, 1000, 1038, 'some text'),
      (1039, 1007, 3, 1000, 1039, 'some text');