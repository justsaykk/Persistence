# Class Notes

## Creating MySQL database

``` SQL

-- Can use this method or just use GUI

CREATE DATABASE northwind;

CREATE USER 'fred'@'localhost' IDENTIFIED BY 'password@123456';

GRANT ALL privileges ON *.* TO 'fred'@'localhost' with grant option;

```

```SQL

-- To log into sql, use the following cli commands:

`mysql -u <username> -p`

-- To point the database to a source:

`use <database_name>;`
`source <database_file>.sql;`

```
