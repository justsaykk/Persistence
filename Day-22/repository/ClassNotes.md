# HTTP Methods

| HTTP Method | Purpose | SQL Operation |
| ------------| --------| --------------|
| POST        | KIV     | INSERT        |
| GET         | KIV     | SELECT        |
| DELETE      | KIV     | DELETE        |
| PUT         | KIV     | UPDATE        |
| PATCH       | KIV     | UPDATE        |

PUT method requires the entire record to be provideed.

PATCH method differs from PUT whereby only a segment of data needs to be provided.

```SQL

--- primary key
username varchar(128) not null,
password varchar(256) not null
email varchar(128) not null,
dob date not null,
phone varchar(128) not null,

primary key(username)

```
