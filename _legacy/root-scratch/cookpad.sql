CREATE TABLE Customer (
'CustomerId' INT NOT NULL PRIMARY KEY,
'Name' VARCHAR(30)
'Password' VARCHAR(100)
'Created' DATE
  ...more Information
);

CREATE TABLE Product (
'ProductId' INT NOT NULL PRIMARY KEY,
'Amount' VARCHAR(40),
'Price' INT,
'Seller' VARCHAR(100),
'UpdateDate' DATE,
'Instruction' VARCHAR(500),
  ...more Information
);

CREATE TABLE Cart (
'CustomerId' INT NOT NULL,
'ProductId' INT NOT NULL,
  PRIMARY KEY('CustomerId', 'ProductId')
);