create table Users
(
    id int
    auto_increment not null,
    name varchar
    (255) not null,
    
    primary key
    (id)
);

    create table likes
    (
        user_id int not null,
        likes varchar(255) not null,

        FOREIGN KEY (user_id) REFERENCES Users(id)
    );

    INSERT INTO Users
        (name)
    values('user01');
    INSERT INTO Users
        (name)
    values('user02');
    INSERT INTO Users
        (name)
    values('user03');
    INSERT INTO Users
        (name)
    values('user04');
    INSERT INTO Users
        (name)
    values('user05');

    INSERT INTO likes
        (user_id, likes)
    values('3', 'Star Gazing');
    INSERT INTO likes
        (user_id, likes)
    values('1', 'Climbing');
    INSERT INTO likes
        (user_id, likes)
    values('1', 'Coding');
    INSERT INTO likes
        (user_id, likes)
    values('6', 'Sports');
    INSERT INTO likes
        (user_id, likes)
    values('4', 'Fruits');

    SELECT Users.name, likes.likes
    FROM Users JOIN likes
        ON Users.id = likes.user_id