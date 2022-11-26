CREATE TABLE shop (
  id int auto_increment primary key,
  img_shop varchar(255),
  shop_name varchar(255) not null,
);

CREATE TABLE user (
  id int auto_increment primary key,
  first_name varchar(255),
  last_name varchar(255) not null,
  email varchar(255) unique,
  hash_password varchar(255),
  phone_number varchar(255) unique,
  address varchar(255),
  img_user varchar(255),
  user_id_facebook bigint unique,
  shop int default null,
  FOREIGN KEY (shop) REFERENCES shop(id)
);

CREATE TABLE code (
  id int auto_increment primary key,
  phone_number varchar(32) unique,
  email varchar(255) unique,
  code varchar(32) not null,
  time_send_code bigint not null
);

CREATE TABLE request_authenticate_code (
  id int auto_increment primary key,
  phone_number varchar(32) unique,
  email varchar(255) unique,
  code varchar(32) not null
);

CREATE TABLE category (
  id int auto_increment primary key,
  category_name varchar(255) unique not null,
  category_image varchar(255) not null
);

CREATE TABLE shop_category (
  shop_id int not null,
  category_id int not null,
  FOREIGN KEY (shop_id) REFERENCES shop(id),
  FOREIGN KEY (category_id) REFERENCES category(id)
);

INSERT INTO
  shop
values
  (null, "", "fff");

INSERT INTO
  user
values
  (
    null,
    "bui",
    "hieu",
    "hieubuict96@gmail.com",
    "",
    "+84383207498",
    null,
    null,
    null,
    1,
    1
  );

INSERT INTO
  category
values
  ();