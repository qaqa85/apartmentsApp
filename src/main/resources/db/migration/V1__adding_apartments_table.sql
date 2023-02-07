CREATE TABLE apartments (
    id UUID NOT NULL,
    title varchar(50)  NOT NULL,
    city varchar(25)  NOT NULL,
    street varchar(50)  NOT NULL,
    postcode varchar(10)  NOT NULL,
    price numeric(10,2)  NOT NULL,
    status varchar(20)  NOT NULL,
    description text,
    PRIMARY KEY(id)
);