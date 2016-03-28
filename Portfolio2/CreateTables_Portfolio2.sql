create table if not exists Group8_users(
username varchar(10),
Password varchar(50),
admin varchar(5),
securityquestion varchar(50),
securityanswer varchar(50),
picPath varchar(50),
primary key(username)
);

insert into Group8_users(username, password, admin, picPath)
values('ztwild', '48fa4c38043122c03a61b1fb03d378ee', 'false', 'src/img/ztwild_pic.jpg'),
('mdweems', '079e5b13ca51e122c5588b5913a78367', 'false', 'src/img/ztwild_pic.jpg'),
('user', '0cc175b9c0f1b6a831c399e269772661', 'false', 'src/img/ztwild_pic.jpg'),
('admin', '0cc175b9c0f1b6a831c399e269772661', 'true', 'src/img/ztwild_pic.jpg');


create table if not exists Group8_friends(
username varchar(10),
friend varchar(10),
foreign key(username) references Group8_users(username));

insert into Group8_friends(username, friend)
values('mdweems', 'ztwild'),
('ztwild', 'mdweems'),
('mdweems', 'jazz');


create table if not exists Group8_posts(
title varchar(50),
text varchar(4000),
data varchar(250),    
postId int(4) not null AUTO_INCREMENT,
username varchar(10),
primary key(postId),
foreign key(username) references Group8_users(username));

insert into Group8_posts(title, text, username)
values('Jazz', 'McGee', 'mdweems'),('wooooo hoooooooo', 'Uh Huh...', 'mdweems'),
('juicy', 'J', 'mdweems'),('its me', 'zach', 'ztwild');

create table if not exists Group8_images(
imageId int(4) not null AUTO_INCREMENT,
path varchar(50),
title varchar(50),
username varchar(10),
primary key(imageId),
unique key(path),
foreign key(username) references Group8_users(username));

insert into Group8_images(path, title, username)
values('src/img/test.jpg', 'Panda', 'ztwild'),
('src/img/test2.jpg', 'test 2', 'ztwild'),
('src/img/test3.jpg', 'test 3', 'ztwild'),
('src/img/test4.jpg', 'test 4', 'ztwild'),
('src/img/test5.jpg', 'test 5', 'ztwild'),
('src/img/test6.jpg', 'test 6', 'ztwild');


create table if not exists Group8_comments(
commentId int not null AUTO_INCREMENT,
text varchar(250),
username varchar(10) not null,
postId int(4) not null,
primary key(commentId),
foreign key(postId) references Group8_posts(postId),
foreign key(username) references Group8_users(username));

insert into Group8_comments(username, postId, text)
values('mdweems', 1, 'jazzity jazzin jazz'),('mdweems', 2 ,  'Uh Huh...'),
('mdweems',3, 'Yezzir'), ('ztwild',1, 'blaha'), ('ztwild',2, 'poooooooooooop');


create table if not exists Group8_likes(
likeId int not null AUTO_INCREMENT,
username varchar(10),
postId int(4) not null,
primary key(likeId),
foreign key(postId) references Group8_posts(postId),
foreign key(username) references Group8_users(username));

insert into Group8_likes(username, postId)
values('mdweems', 1),('ztwild', 1),('ztwild', 2),
('mdweems', 3);


create table if not exists Group8_apps(
name varchar(30) not null,
description varchar(50),
location varchar(250) not null,
html_location varchar(200),
primary key(name)
);

insert into Group8_apps(name, description, location, html_location)
values('SightReader', 'helps you sight read', 'src\\apps\\SightReader', 'src\\apps\\SightReader\\pages\\sightreader_app.html');