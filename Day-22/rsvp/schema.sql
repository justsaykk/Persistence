create table RSVP
(
    id int auto_increment not null,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    date DATE not null,
    comments varchar(255),
    
    primary key (id)
);