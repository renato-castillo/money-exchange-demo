drop table exchanges if exists;
drop table currencies if exists;
drop table users if exists;

create table if not exists currencies (
    id integer auto_increment  primary key,
    name varchar(50),
    symbol varchar(5),
    created_at timestamp not null,
    modified_at timestamp,
    created_by  varchar(50) not null,
    modified_by varchar(50)
);

INSERT INTO currencies(name, symbol, created_at, created_by) VALUES('Euro', '€', now(), 'rcastillo');
INSERT INTO currencies(name, symbol, created_at, created_by) VALUES('Dolar', '$', now(), 'rcastillo');
INSERT INTO currencies(name, symbol, created_at, created_by) VALUES('Nuevo Sol', 'S/', now(), 'rcastillo');



create table if not exists exchanges (
    id integer auto_increment  primary key,
    currency_origin_id int not null,
    currency_target_id  int not null,
    exchange decimal(10,5) not null,
    created_at timestamp not null,
    modified_at timestamp,
    created_by  varchar(50) not null,
    modified_by varchar(50),
    foreign key (currency_origin_id) references currencies(id),
    foreign key (currency_target_id) references currencies(id)
);

INSERT INTO exchanges(currency_origin_id, currency_target_id, exchange, created_at, created_by) VALUES(1, 2, 1.06, now(), 'rcastillo');
INSERT INTO exchanges(currency_origin_id, currency_target_id, exchange, created_at, created_by) VALUES(2, 1, 0.94, now(), 'rcastillo');
INSERT INTO exchanges(currency_origin_id, currency_target_id, exchange, created_at, created_by) VALUES(1, 3, 3.95, now(), 'rcastillo');
INSERT INTO exchanges(currency_origin_id, currency_target_id, exchange, created_at, created_by) VALUES(3, 1, 0.25, now(), 'rcastillo');
INSERT INTO exchanges(currency_origin_id, currency_target_id, exchange, created_at, created_by) VALUES(2, 3, 3.71, now(), 'rcastillo');
INSERT INTO exchanges(currency_origin_id, currency_target_id, exchange, created_at, created_by) VALUES(3, 2, 0.27, now(), 'rcastillo');



create table if not exists users(
    id integer auto_increment primary key,
    username varchar(20) not null,
    password varchar(100) not null,
    rol varchar(20) not null
);

INSERT INTO users(id, username, password, rol) VALUES(1, 'rcastillo', '$2a$10$1AKUvEGctgFl4pLPA7vAMeu5CrqOWt.NEye.f2xbpLMw9NZwbFrx6', 'admin');

create table if not exists user_exchanges(
  id integer auto_increment primary key,
  exchange_id integer not null,
  amount decimal(10,5) not null,
  exchange_rate decimal(10,5) not null,
  exchange decimal(10,5) not null,
  created_at timestamp not null,
  modified_at timestamp,
  created_by  varchar(50) not null,
  modified_by varchar(50)
);


