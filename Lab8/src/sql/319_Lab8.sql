create table if not exists Group8_users(
username varchar(10),
Password varchar(50),
email varchar(25),
phone varchar(25),
librarian varchar(5),
firstname varchar(15),
lastname varchar(15),
primary key(username)
);

insert into Group8_users(username, password, email, phone, librarian, firstname, lastname)
values('ztwild', '48fa4c38043122c03a61b1fb03d378ee', 'ztwild@iastate.edu', '319-210-9955', 'false', 'Zach', 'Wild'),
('mdweems', '079e5b13ca51e122c5588b5913a78367', 'mdweems@iastate.edu', '319-555-5555', 'false', 'Michael', 'Weems'),
('student', '0cc175b9c0f1b6a831c399e269772661','stu@dent.edu','111-111-1111','false','stu','dent'),
('librarian', '0cc175b9c0f1b6a831c399e269772661','lib@rarian.edu','111-111-1111','true','lib','rarian');

create table if not exists Group8_books(
bookId int(3) not null AUTO_INCREMENT,
bookTitle varchar(50),
author varchar(25),
primary key(bookId)
);

insert into Group8_books(bookTitle, author)
values('To Kill A Mocking Bird', 'Harper Lee'),
('The Catcher in the Rye', 'J.D. Salinger'),
('Gone With the Wind', 'Margaret Mitchell'),
('The Girl With the Dragon Tatoo', 'Stieg Larsson'),
('Harry Potter and the Philosophers Stone', 'J.K. Rowling'),
('Gone Girl', 'Gillian Flynn'),
('The Lord of the Rings', 'J.R.R Tolkien'),
('Charlottes Web', 'E.B. White'),
('The Great Gatsby', 'F. Scott Fitzgerald'),
('The Godfather', 'Mario Puzo');

create table if not exists Group8_bookCopy(
copyId int(3) not null AUTO_INCREMENT,
bookId int(3) not null,
primary key(copyId),
foreign key(bookId) references Group8_books(bookId)
);

insert into Group8_bookCopy(bookId)
values(01),(01),(01),
(02),(02),
(03),(03),(03),
(04),(04),
(05),(05),(05),
(06),(06),
(07),(07),(07),
(08),(08),
(09),(09),
(10),(10),(10);

create table if not exists Group8_loanHistory(
username varchar(10),
copyId int(3) not null,
duedate date,
returned date,
foreign key(username) references Group8_users(username),
foreign key(copyId) references Group8_bookCopy(copyId)
);

create table if not exists Group8_shelves(
shelfId int,
copyId int(3) not null,
foreign key(copyId) references Group8_bookCopy(copyId) );

insert into Group8_shelves(shelfId, copyId)
values(1, 1),(1, 2),(1, 3),(1, 4),(1, 5),(1, 6),(1, 7),(1, 8),(1, 9),
(2, 10),(2, 11),(2, 12),(2, 13),(2, 14),(2, 15),(2, 16),(2, 17),(2, 18),(2, 19),
(3, 20),(3, 21),(3, 22),(3, 23),(3, 24),(3, 25);