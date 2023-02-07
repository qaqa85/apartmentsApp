CREATE TABLE owners (
    id UUID NOT NULL,
    surname varchar(30),
    lastname varchar(30),
    phone_number varchar(9),
    city varchar(25)  NOT NULL,
    street varchar(50)  NOT NULL,
    postcode varchar(10)  NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE apartments ADD COLUMN owner_id UUID NOT NULL;
ALTER TABLE apartments ADD CONSTRAINT fk_apartments_owner_id FOREIGN KEY (owner_id) REFERENCES owners(id) ON DELETE CASCADE;