-- Provide SQL scripts here
SET SCHEMA demo;

INSERT INTO demo.users (email, first_name, last_name, password, external_id)
VALUES ('johnny.supplier@example.com', 'John', 'Supplier',
        '$2a$10$yM0l.5zB.y8MuImV.VITqORDpDvN4QCTS1wa3EYlu3bVpDeK1CiSe', 'a8f5f167-8477-4e36-a715-bc5b4d8f5661'),
       ('alice.buyer@example.com', 'Alice', 'Buyer', '$2a$10$yM0l.5zB.y8MuImV.VITqORDpDvN4QCTS1wa3EYlu3bVpDeK1CiSe',
        'd5c58d9e-1a4d-4c2e-9a97-014ea23bb8f6');

INSERT INTO products (name, description, price, available_quantity, external_id)
VALUES ('Engine Oil', 'Premium synthetic engine oil for high-performance engines.',
        29.99, 150, 'a3e6d3a4-01e8-4d88-a6b3-5b1f3f1c0e4b'),
       ('Brake Pads', 'High-quality brake pads for all weather conditions.', 49.99,
        300, 'b7f6e5a1-4d93-4e35-9179-6e27e6e9f7da'),
       ('Spark Plugs', 'Durable spark plugs that improve engine performance.', 15.99,
        500, 'c2a4f8e2-13d7-4c1b-8a23-9e0c7f1a8c2e'),
       ('Car Battery', 'Long-lasting car battery with a 5-year warranty.', 89.99,
        75, 'd9b1c3e7-2a5c-4b7d-b6e4-1d4f3a6e2f8b'),
       ('Tire Set', 'All-season tire set with excellent traction.', 399.99, 50,
        'e8d2c7b4-3e7a-4f9d-9a6b-2f5e7b8c9d2e');

INSERT INTO demo.roles(id, name)
VALUES (1, 'SHOP_OWNER'),
       (2, 'BUYER');

INSERT INTO demo.user_roles(user_id, role_id)
VALUES (1, 1),
       (2, 2);