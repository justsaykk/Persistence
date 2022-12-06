# Persistence

## SQL Database (mySQL)

### Creating a Database using mySqlWorkbench

1. Create a connection to the database by supplying the following info:
    - Hostname
    - Port
    - Username (if connecting for the first time, consider using "root")
    - Password of supplied user
    - (if possible) Schema name
</br>

2. Create a schema (also known as "database". i know, it gets confusing)

    ```SQL
    create database rsvp
    ```  

    At this point, the schema is empty.
</br>

3. Create tables to hold the data.
    If the dataset is unknown, you can do this later. However, we are assuming that the dataset is known and therefore a table can be created via:

    ```SQL
    use rsvp;

    create table responses (
    id int auto_increment not null,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    date DATE not null,
    comments varchar(255),
    
    primary key (id));
    ```

    In mySQL, `auto_increment` automatically sets the column as the primary key.
</br>

4. Creating a non-root user (Optional)
    There are 2 ways to create non-root users. We will examine both of them.
    </br>
    - Using the GUI to create the user:
      - As the root user, switch to the "Administration" tab
      - Select "Users and Priviledges"
      - Somewhere at the bottom of the page, click the button "Add Account"
      - Set a Login Name (username)
      - Set the password & confirm the password
      - (Optional) Designate a host
      - Select the "Schema Privileges" tab
      - Click "Add Entry" and select appropriate schemas
      - Modify priviledges as needed
      - Click "Apply"
    </br>
    - (Not Recommended) Using a query to create the user:

        ```SQL
        CREATE DATABASE northwind;
        CREATE USER 'fred'@'localhost' IDENTIFIED BY 'password@123456';
        GRANT ALL privileges ON *.* TO 'fred'@'localhost' with grant option;
        ```

    </br>

5. Seeding existing data into tables

    Now the newly created `responses` table in the `rsvp` schema looks like this:

    responses
    | id  | name         | email        | phone        | date | comments     |
    | --- | ------------ | ------------ | ------------ | ---- | ------------ |
    | int | varchar(255) | varchar(255) | varchar(255) | DATE | varchar(255) |

    Now, the database is up and ready to receive information!

    ---------------

### Connecting the application to the database

To connect the application to the database, a few things are needed:

1. Database URL (a.k.a hostname) + port
2. User's username & password
3. Database connection driver

These are the project dependencies that are needed:

1. SpringBoot Dev Tools
2. Spring Web
3. JDBC API
4. MySQL Connector Java (from maven repo)
5. JSON-P (from maven repo)

Optionals:

1. Thymeleaf -- To handle HTML pages
2. Joda Time (from maven repo) -- To handle DateTime

#### Configurations

In the application.properties file:

```xml
spring.datasource.url: jdbc:mysql://<hostname>:<port>/<schema>
spring.datasource.username: ${SPRING_DATASOURCE_USERNAME}
spring.datasource.password: ${SPRING_DATASOURCE_PASSWORD}
```

Notice the use of `jdbc` prior to the url string. This is for the JDBC API.

When deploying to railway, in the environment variables, `jdbc` has to be stated as well.

Essentially, we're done! The application will now be reading environment properties to connect to the database, be it via railway or on localhost:3306.

#### Using the JDBC API

If the `pom.xml` was loaded proeprly, the following package would be available:
`org.springframework.jdbc`.

In this package, the `JdbcTemplate` class can be found. This template class will hold most of our query methods.

Most commonly, we will be using the method `.queryForFowSet()` which takes in a SQL string and its parameters. Lets see how it's being used.

In another file, we can list out all the queries we want. It looks something like this:

```java
public class Queries {
    public static final String SQL_GET_ALL = "SELECT * FROM rsvp.RSVP";
    public static final String SQL_GET_NAME = "SELECT * FROM rsvp.RSVP WHERE name LIKE ?";
    public static final String SQL_ADD_RSVP = "INSERT into rsvp.RSVP (name, email, phone, date, comments) values (?,?,?,?,?)";
    public static final String SQL_UPDATE_RSVP = "UPDATE rsvp.RSVP SET name = ?, phone = ?, date = ?, comments = ? WHERE email = ?";
}
```

In the Data Access Object (or @Repository annotated class), we will need to import the queries class via a static import:
`import static example.rsvp.repository.Queries.*`.

Now, we need to write methods to access the data. It looks something like this:

```java
package example.rsvp.repository;

import static day22workshop.rsvp.repository.Queries.*;

@Repository
public class RsvpRepo {
    @Autowired
    private JdbcTemplate template;

    public Optional<List<Person>> getRsvpByName(String q) {
            List<Person> listOfPersons = new LinkedList<>();

            // The "q" here will be replacing the "?" in the query.
            // The injection is in order, therefore, supply the parameters in order.
            SqlRowSet rs = template.queryForRowSet(SQL_GET_NAME, q);

            // SqlRowSet returns a something like a table. It's reader cursor is now at the header row.
            // Therefore we need to move the cursor to the next row (i.e. first row) and read its data using the .first() method.
            if (!rs.first()) {
                return Optional.empty();
            }

            // This is to move the cursor back to the header row. Arguable it's not needed in this case but its good to know that it's available.
            rs.previous(); 

            while (rs.next()) {
                listOfPersons.add(new Person(rs));
            }

            return Optional.of(listOfPersons);
        }
}
```

Of course, more complicated queries can be constructed. It all depends on the dataset and it's storage complexity.

### SQL Queries

#### Types of Joins

There are 4 types of basic joins.

1. Inner Join
2. Left Join
3. Right Join
4. Outer Join (Full Join)

#### Inner Join

Inner join is the default join. -- It need not be specified in the query.

It gives back a set of data that is common to both tables and leaves the rest out. There should not be any `null` values. If it returns `null` means the tables have nothing related to each other.

> A great resource is [this sql doodler](https://joins.spathon.com/)

Lets say we have a 2 tables that looks like this:

Users
| ID  | Name   |
| --- | ------ |
| 1   | user01 |
| 2   | user02 |
| 3   | user03 |
| 4   | user04 |
| 5   | user05 |

Likes
| user_id | Like        |
| ------- | ----------- |
| 1       | Climbing    |
| 1       | Coding      |
| 3       | Star Gazing |
| 4       | Fruits      |
| 6       | Sports      |

The query looks like this:

```SQL
SELECT users.name, likes.like
FROM users JOIN likes
ON users.id = likes.user_id;
```

Breakdown:
`SELECT users.name, likes.like` : "I want data from the `name` column in the `users` table and the `like` column in the `likes` table"

`FROM users JOIN likes` : "From a table where `users` is joined with `likes`"

`ON users.id = likes.user_id` : "where the `id` of the user table matches the `user_id` of the likes table"

This is the query result:

| name   | likes       |
| ------ | ----------- |
| user03 | Star Gazing |
| user01 | Climbing    |
| user01 | Coding      |
| user04 | Fruits      |

Notice that `user02`, `user05` and `Sports` aren't part of the result. This is a property of inner joins.
Inner Joins drops all non-relatable data and only gives you whatever that matches.
