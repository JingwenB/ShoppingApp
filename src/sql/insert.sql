use shoppingApp;

INSERT INTO User ( email, username, password)
VALUES
    ('anna@mail.com','anna', '1234'),
    ('bob@mail.com', 'bob', '1234');


INSERT INTO Product ( name, description, wholesale_price, retail_price,stock_quantity)
VALUES

    ('iPhone 13', 'iPhone 13 128G',  10.00, 20.00, 5),
    ('iPhone 13 Pro', 'iPhone 13 Pro 256G', 10.00, 20.00, 5),
    ('iPad Air', 'iPad Air 512G', 10.00, 20.00, 5),
    ('iPad Pro', 'iPad Pro 1TB', 10.00, 20.00, 5),
    ('Apple Watch', 'Apple Watch Series 8', 10.00, 20.00, 5);


INSERT INTO `Order` ( user_id, order_status)
VALUES
 (1, 'placed'),
 (2, 'processed'),
 (2, 'processing');


INSERT INTO OrderItem ( order_id, product_id, wholesale_price, purchased_price, purchased_quantity)
VALUES
    (1, 1, 10.00, 20.00, 2),
    (1, 3, 10.00, 20.00, 2),
    (2, 2, 10.00, 20.00, 2),
    (2, 4, 10.00, 20.00, 2),
    (3, 5, 10.00, 20.00, 2);


INSERT INTO Watch ( user_id, product_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1);
