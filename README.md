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

    If there is an existing `.sql` file that has a bunch of `INSERT INTO` statements, we can open the file in workbench and run that file.

    Otherwise, there's no other elegant way to seed a database. Good 'ol manual inserts.

---

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

Dependency Code dump:

```xml
<!-- From Maven Repositories -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.31</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish</groupId>
        <artifactId>jakarta.json</artifactId>
        <version>2.0.1</version>
    </dependency>
    <dependency>
        <groupId>joda-time</groupId>
        <artifactId>joda-time</artifactId>
        <version>2.12.2</version>
    </dependency>
```

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

---

### SQL Queries

#### Operators & Structure of Queries

##### Operators

When Querying data from the table, most of the time, you would not want the entire table's data as it might contain thousand of rows. You just want the data that you need.
To achieve this, we use operators to query for specific data. Lets take a look at some of the available operators:

Logical

1. `AND`
2. `OR`
3. `NOT`

Comparisons

1. `>` Greater than
2. `<` Less than
3. `<=` Less than or equal to
4. `>=` Greater than or equal to
5. `<>` Not equal to
6. `=` Equal to

##### Structure of a Query (mySQL)

Every version/iteration of a SQL language is like a dialect if SQL. They follow a certain pattern but it all leads to the same outcome. In this note, we are exploring mySQL. Other SQL platforms may have other synthax requirements.

To start the ball rolling, first is always to select your required data:

```SQL
    SELECT rating
```

Next is to tell the program where to get the data:

```SQL
    SELECT rating FROM tv_shows
```

And then add the constrains of the search using the `WHERE` keyword:

```SQL
    SELECT rating FROM tv_shows
    WHERE lang LIKE 'Mandarin'
```

The above query will return 1 column with all the ratings that was associated to a Mandarin show.
But its pretty usesless. We want some good data. We want to know what is the average rating for mandarin shows.

```SQL
    SELECT avg(rating) FROM tv_shows
    WHERE lang LIKE 'Mandarin'
```

Ok, this is great. Now we want to know what is the distribution across each rating.

```SQL
    SELECT rating, count(rating)
        FROM tv_shows
        WHERE lang LIKE 'Mandarin'
        GROUP BY rating
        ORDER BY count(rating) desc
```

This query will give us a list of 2 columns that looks like this:

| rating | count(rating) |
| ------ | ------------- |
| 3      | 10            |
| 4      | 8             |
| 5      | 5             |
| 2      | 2             |
| 1      | 1             |

A table with the ratings grouped together and in descending order by the number of rated shows.

Nice, nice. Now we want to know how our top reviewed shows are doing.

```SQL
    SELECT rating, count(rating)
        FROM tv_shows
        -- No `WHERE` cuz we want everything
        GROUP BY rating
        HAVING count(rating) > 100
        ORDER BY count(rating) desc
```

This should give us a similar table as above but we can be assured that the program only includes shows with a minimum of 101 reviews.

Notice the use of `WHERE` and `HAVING`. To put it simply, `WHERE` tells the script the constrains of the search. `HAVING` tells the script what to show. We can think of it like a pre-search filter vs a results filter.

Oh, and to make the data more presentable, there's an `AS` keyword and it can be used like this:

```SQL
    SELECT rating AS stars, count(rating) AS reviews
        FROM tv_shows
        WHERE lang LIKE 'Mandarin'
        GROUP BY rating
        ORDER BY count(rating) desc
```

And its column name will be changed to this:

| stars | reviews |
| ----- | ------- |
| 3     | 10      |
| 4     | 8       |
| 5     | 5       |
| 2     | 2       |
| 1     | 1       |

#### Types of Joins

There are 4 types of basic joins.

1. Inner Join
2. Left Join
3. Right Join
4. Outer Join (Full Join)

In this explanation section, we will be working with 2 tables:

`Users` table
| ID  | Name   |
| --- | ------ |
| 1   | user01 |
| 2   | user02 |
| 3   | user03 |
| 4   | user04 |
| 5   | user05 |

`Likes` table
| user_id | Like        |
| ------- | ----------- |
| 1       | Climbing    |
| 1       | Coding      |
| 3       | Star Gazing |
| 4       | Fruits      |
| 6       | Sports      |

> A great resource is <https://joins.spathon.com>

##### Inner Join

Inner join is the default join. -- It need not be specified in the query.

It gives back a set of data that is common to both tables and leaves the rest out. There should not be any `null` values. If it returns `null` means the tables have nothing related to each other.

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

##### Left Join, Right Join & Outer Join

Outer Join or Full Outer Join is used when all the data is needed. No matter if it results in a `NULL` field or not.
Left Join includes all the values from the left table and if no value is found on the right table, it will populate the result with `NULL`.
Right Join is the exact opposite of left join. Throwing `NULL` values when the corresponding values on the left table cannot be found.

Unfortunately, mySQL does not natively support `FULL OUTER JOIN`. Therefore, a workaround with `UNION` is required. Here's how it looks like:

```SQL
-- This is the left outer join query
SELECT Users.name, likes.likes FROM Users LEFT OUTER JOIN likes ON Users.id = likes.user_id
UNION
-- This is the right outer join query
SELECT Users.name, likes.likes FROM Users RIGHT OUTER JOIN likes ON Users.id = likes.user_id
```

This is the `OUTER JOIN` query result:

| name   | likes       |
| ------ | ----------- |
| user01 | Coding      |
| user01 | Climbing    |
| user02 | `NULL`      |
| user03 | Star Gazing |
| user04 | Fruits      |
| user05 | `NULL`      |
| `NULL` | Sports      |

Notice that values from both the likes and the name columns that do not have a corresponding value is shown as `NULL`.

This is the `LEFT JOIN` query result:

| name   | likes       |
| ------ | ----------- |
| user01 | Coding      |
| user01 | Climbing    |
| user02 | `NULL`      |
| user03 | Star Gazing |
| user04 | Fruits      |
| user05 | `NULL`      |

And this is the `RIGHT JOIN` query results:

| name   | likes       |
| ------ | ----------- |
| user01 | Coding      |
| user01 | Climbing    |
| user03 | Star Gazing |
| user04 | Fruits      |
| `NULL` | Sports      |

### Deployment Notes

Deployment on Railway goes like this:

#### NoSQL

##### Set up Database on railway (MongoDB)

1. Go to Dashboard and create a new project
2. Click "Provision MongoDB" and wait for the database to be created
3. Click on the box in the middle of the screen
4. Connect to the railway database using your preferred data explorer
5. Seed data using data explorer
6. Check back at railway to see if data has been successfully seeded
7. Take note of the "Variables" tab.

##### Configure application.properties file

application.properties

```xml
spring.data.mongodb.host=${MONGODB_HOST}
spring.data.mongodb.port=${MONGODB_PORT}
spring.data.mongodb.username=${MONGODB_USER}
spring.data.mongodb.password=${MONGODB_PASSWORD}
spring.data.mongodb.database=BoardGames
spring.data.mongodb.authentication-database=admin
```

Take note of the last line. Its especially important.
Commit and push to github.

#### SQL

#### Set up Database on railway (MySQL)

1. Go to Dashboard and create a new project
2. Click "Provision MySQL" and wait for the database to be created
3. Click on the box in the middle of the screen
4. Connect to the railway database using your preferred data explorer
5. Seed data using data explorer (Take note to `use railway` to select railway database)
6. (Optional) Could also create user profiles
7. Check back at railway to see if data has been successfully seeded
8. Take note of the "Variables" tab

##### Configure application.yml file

application.yml

```yml
spring:
    datasource:
        url: ${MYSQL_URL}     # jdbc:mysql://localhost:3306/bgg
        username: ${MYSQL_USERNAME}
        password: ${MYSQL_PASSWORD}
```

Take note that `jdbc:` needs to be in front of the connection string in the environment variables.

#### Create application container on Railway

1. Go to Dashboard and create a new project
2. Click Deploy from GitHub repo
3. Ensure that the correct root folder is selected
4. Add environment variables to match the application.properties file
5. Copy the corresponding values from the railway database "Variables" tab
6. (IMPORTANT) Check that all queries are pointing to the correct database
7. Deploy

And this should be it!
