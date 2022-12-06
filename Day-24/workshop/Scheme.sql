drop database if exists vttpday24workshop;

create database vttpday24workshop; 

use vttpday24workshop;

drop table if exists orders;
drop table if exists order_details;

create table orders(
	order_id int auto_increment,
    order_date date,
    customer_name varchar(128),
	ship_address varchar(128),
    notes text,
    tax decimal(2,2),
    
    primary key (order_id)
);

create table order_details(
	id int auto_increment,
    order_id int,
    product varchar(64),
    unit_price decimal(3,2),
	discount decimal(2,2),
    quantity int,
    
    primary key (id),
    foreign key (order_id) references orders(order_id)
)