create database if not exists Tangshi;
use Tangshi;
create table if not exists poetry_info (
  title   varchar(64)   not null,
  dynasty varchar(32)   not null,
  author  varchar(32)   not null,
  content varchar(2048) not null
);
