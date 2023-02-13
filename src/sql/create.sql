create database if not exists shoppingApp;
use shoppingApp;
drop table if exists User;
create table if not exists User
(
    user_id int primary key auto_increment,
    email varchar(20),
    username varchar(20),
    password varchar(20),
    is_admin boolean
    );


drop table if exists Product;
create table if not exists Product
(
    product_id int primary key auto_increment,
    name varchar(20),
    description varchar(200),
    wholesale_price double,
    retail_price double,
    stock_quantity int
    );

drop table if exists `Order`;
create table if not exists `Order`
(
    order_id int primary key auto_increment,
    user_id int,
    order_status varchar(20),
    date_placed timestamp,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
    );

drop table if exists OrderItem;
create table if not exists OrderItem
(
    order_item_id int primary key auto_increment,
    product_id int,
    order_id int,
    wholesale_price double,
    purchased_price double,
    purchased_quantity int,
    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (order_id) REFERENCES `Order`(order_id)
    );

drop table if exists Watch;
create table if not exists Watch
(
    product_id int,
    user_id int,
    PRIMARY KEY (product_id, user_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
    );