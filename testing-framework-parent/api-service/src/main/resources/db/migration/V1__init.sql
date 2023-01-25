create table customer
(
    customer_id      serial,
    surname          varchar(20),
    givenname        varchar(20),
    dob              DATE,
    gender           varchar(20),
    customer_profile varchar(20),
    primary key (customer_id)
);
