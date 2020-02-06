SET FOREIGN_KEY_CHECKS=0;
drop table if exists Shows;
drop table if exists Theaters;
drop table if exists Users;
drop table if exists Reservations;
drop table if exists Tickets;
drop table if exists Movies;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE Theaters(
	name varchar(100) primary key,
	nbrOfSeats INTEGER not null check (nbrOfSeats > 0)
);

CREATE TABLE Movies(
	name varchar(100) primary key
	);

CREATE TABLE Shows(
	movieName varchar(100),
	showDate DATE,
	theaterName varchar(100),
	index (showDate),
	primary key (showDate, movieName),
	foreign key (movieName) references Movies(name),
	foreign key (theaterName) references Theaters(name)
);
	
CREATE TABLE Users(
	username varchar(100) primary key,
	name varchar(100),
	phoneNbr varchar(20),
	address varchar(200) NULL
);


CREATE TABLE Reservations(
	reservationId INTEGER primary key  AUTO_INCREMENT,
	username varchar(100),
	movieName varchar(100),
	showDate DATE,
	foreign key (username) references Users(username),
	foreign key (movieName) references Shows(movieName),
	foreign key (showDate) references Shows(showDate)
);

-- Sätt in alla värden

insert into Movies values ('Shawshank Reduction');
insert into Movies values ('The Bunker Brothers');
insert into Movies values ('Edge-case Edgar');
insert into Movies values ('Kim Goes To Hell');
insert into Movies values ('Formal Pine Forests');
insert into Movies values ('Kalla Tårar');
insert into Movies values ('What Sky?!');
insert into Movies values ('Horseshoe Horatio');

insert into Theaters values ('Salong Kalsong', 20);
insert into Theaters values ('Bunker Brothers Biograf och Cirkus', 30);
insert into Theaters values ('Sweden Filmbolag AB', 45);

insert into Shows values ('Shawshank Reduction', '2020-02-05', 'Salong Kalsong');
insert into Shows values ('The Bunker Brothers', '2020-02-23', 'Salong Kalsong');
insert into Shows values ('Edge-case Edgar', '2020-02-23', 'Bunker Brothers Biograf och Cirkus');
insert into Shows values ('Kim Goes To Hell', '2020-02-05','Sweden Filmbolag AB');
insert into Shows values ('Formal Pine Forests', '2020-03-30', 'Sweden Filmbolag AB');
insert into Shows values ('Kalla Tårar', '2020-04-02', 'Sweden Filmbolag AB');
insert into Shows values ('What Sky?!', '2020-04-05', 'Sweden Filmbolag AB');
insert into Shows values ('Horseshoe Horatio', '2020-04-05', 'Bunker Brothers Biograf och Cirkus');

insert into Shows values ('What Sky?!', '2020-04-21', 'Bunker Brothers Biograf');

insert into Users values('abc123', 'Anders Bo Carlsson', '112', null);
insert into Reservations values(0, 'abc123', 'The Bunker Brothers', '2020-02-23');

