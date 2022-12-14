DROP DATABASE IF EXISTS PosSystem;
CREATE DATABASE IF NOT EXISTS PosSystem;
USE PosSystem;
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    cusID VARCHAR (6),
    cusTitle VARCHAR (5),
    cusName VARCHAR (30),
    cusAddress VARCHAR (30),
    cusContact VARCHAR (20),
    CONSTRAINT PRIMARY KEY (cusID)
);
SHOW TABLES;
DESCRIBE Customer;

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    itemCode VARCHAR (6),
    description VARCHAR (50),
    packSize VARCHAR (20),
    unitPrice DECIMAL (6,2),
    qtyOnHand INT (5),
    CONSTRAINT PRIMARY KEY (itemCode)
);
SHOW TABLES;
DESCRIBE Item;

DROP TABLE IF EXISTS Orders;
CREATE TABLE IF NOT EXISTS Orders(
    orderID VARCHAR (6),
    orderDate DATE,
    cusID VARCHAR (6),
    amount DOUBLE,
    CONSTRAINT PRIMARY KEY (orderID),
    CONSTRAINT FOREIGN KEY (cusID) REFERENCES Customer (cusID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE Orders;

DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
    orderID VARCHAR (6),
    itemCode VARCHAR (6),
    orderQty INT (11),
    price DECIMAL (6,2),
    CONSTRAINT FOREIGN KEY (orderID) REFERENCES Orders (orderID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item (itemCode) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE OrderDetail;