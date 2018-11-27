--liquibase formatted sql
--changeset kaur_laanemäe:001-create-user.sql

create table users (
  id SERIAL PRIMARY KEY,
  password VARCHAR(128) NOT NULL,
  primary_email VARCHAR(255) UNIQUE NOT NULL,
  validated BOOLEAN NOT NULL DEFAULT FALSE,
  enabled BOOLEAN NOT NULL DEFAULT FALSE,
  created_on TIMESTAMP NOT NULL,
  last_login TIMESTAMP
);