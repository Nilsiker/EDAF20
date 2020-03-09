use Krusty;

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS Pallets;
DROP TABLE IF EXISTS Transports;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Cookies;
DROP TABLE IF EXISTS RawMaterials;
DROP TABLE IF EXISTS Recipes;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE Pallets (
    id  INTEGER NOT NULL AUTO_INCREMENT,
    prod_date DATE,
    blocked TINYINT NOT NULL,
    current_location VARCHAR(100) NOT NULL,
    cookie_name VARCHAR(20),

    PRIMARY KEY (id),
    FOREIGN KEY (cookie_name) references Cookies(name)
);

CREATE TABLE Cookies(
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE Recipes(
    cookie_name VARCHAR(20),
    material_name VARCHAR(20),
    amount INTEGER NOT NULL,
    PRIMARY KEY (cookie_name, material_name),
    FOREIGN KEY (cookie_name) references Cookies(name),
    FOREIGN KEY (material_name) references RawMaterials(name)
);

CREATE TABLE RawMaterials(
    name VARCHAR(20) NOT NULL,
    amount INTEGER NOT NULL,
    unit VARCHAR(3) NOT NULL,
    last_delivery_date DATE NOT NULL,
    last_delivery_amount INTEGER NOT NULL
);

CREATE TABLE Transports (
    pallet_id INTEGER,
    order_id INTEGER,
    delivered TINYINT NOT NULL,
    PRIMARY KEY (pallet_id),
    PRIMARY KEY (order_id),
    FOREIGN KEY (pallet_id) REFERENCES Pallets(pallet_id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

CREATE TABLE Orders(
    order_id INTEGER NOT NULL AUTO_INCREMENT, 
    customer_name VARCHAR(20) NOT NULL,
    order_date DATE NOT NULL,
    delivery_date DATE NOT NULL,
    delivered TINYINT NOT NULL,
    PRIMARY KEY (order_id)
);