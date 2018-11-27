--liquibase formatted sql
--changeset karli_udso:001-create-customer.sql

CREATE TABLE customers (
  id SERIAL PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  address VARCHAR(255) NOT NULL,
  balance DECIMAL(13, 4) NOT NULL,
  birthday DATE NOT NULL,
  created_on TIMESTAMP DEFAULT NOW()
);