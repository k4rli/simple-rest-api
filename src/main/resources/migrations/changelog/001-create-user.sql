--liquibase formatted sql
--changeset karli_udso:001-create-customer.sql

create table customers (
  id SERIAL PRIMARY KEY,
  password VARCHAR(128) NOT NULL,
  primary_email VARCHAR(255) UNIQUE NOT NULL,
  validated BOOLEAN NOT NULL DEFAULT FALSE,
  enabled BOOLEAN NOT NULL DEFAULT FALSE,
  created_on TIMESTAMP NOT NULL,
  last_login TIMESTAMP
);