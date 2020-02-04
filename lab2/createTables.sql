drop table if exists Shows;
drop table if exists Users;
drop table if exists Theaters;
drop table if exists Reservations;
drop table if exists Tickets;

CREATE TABLE Shows(
	movieName varchar(100),
	showDate DATE,
	index (showDate),
	primary key(movieName, showDate)
);
	
CREATE TABLE Users(
	username varchar(100) primary key,
	name varchar(100),
	phoneNbr varchar(20),
	address varchar(200) NULL
);

CREATE TABLE Theaters(
	name varchar(100) primary key ,
	nbrOfSeats INTEGER
);

create table TheaterShows(
	theater varchar(100),
	movie varchar(100),
	showDate DATE,
	foreign key (theater) references Theaters(name),
	foreign key (movie) references Shows(movieName),
	foreign key (showDate) references Shows(showDate)
);

CREATE TABLE Reservations(
	reservationId INTEGER primary key  AUTO_INCREMENT,
	movieName varchar(100),
	showDate DATE,
	foreign key (movieName) references Shows(movieName),
	foreign key (showDate) references Shows(showDate)
);

CREATE TABLE Tickets(
	username varchar(20),
	id INTEGER,
	foreign key (username) references Users(username),
	foreign key (id) references Reservations(reservationId)
);

