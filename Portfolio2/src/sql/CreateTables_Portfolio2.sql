create table if not exists Group8_users(
username varchar(10),
Password varchar(50),
admin varchar(5),
securityquestion varchar(50),
securityanswer varchar(50),
imageId int,
primary key(username),
foreign key(imageId) references Group8_image(imageId));

insert into Group8_users(username, password, admin)
values('ztwild', '48fa4c38043122c03a61b1fb03d378ee', 'false'),
('mdweems', '079e5b13ca51e122c5588b5913a78367', 'false'),
('user', '0cc175b9c0f1b6a831c399e269772661', 'false'),
('admin', '0cc175b9c0f1b6a831c399e269772661', 'true');

create table if not exists Group8_apps(
name varchar(30) not null,
description varchar(50),
filepath varchar(250) not null,
primary key(name)
);

insert into Group8_apps(name, filepath)
values('SightReader', 'src\\apps\\SightReader');

create table if not exists Group8_userposts(
postId int(4) not null AUTO_INCREMENT,
username varchar(10) not null,
primary key(postId),
foreign key(username) references Group8_users(username)
);

insert into Group8_userposts(username)
values('mdweems'),('user'),('ztwild');

create table if not exists Group8_posts(
title varchar(50),
text varchar(4000),
postId int(4) not null,
imageId int,
foreign key(postId) references Group8_userposts(postId),
foreign key(imageId) references Group8_image(imageId));

insert into Group8_posts(postId, title)
values(1, 'Jazz'),(2, 'Uh Huh...'),(3, 'Yezzir');

create table if not exists Group8_image(
imageId int not null AUTO_INCREMENT,
imagepath varchar(250),
imagefilename varchar(50),
primary key(imageId));
