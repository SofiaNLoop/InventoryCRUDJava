CREATE DATABASE Reto5;
USE Reto5;

CREATE TABLE Productos(
	codigo int auto_increment,
    nombre varchar(75),
    precio int,
    inventario int,
    primary key (codigo)
);

INSERT INTO Productos VALUES
(1,	'Manzanas',	5000.0,	25),
(2,	'Limones',	2300.0,	15),
(3,	'peras',	2700.0,	33),
(4,	'Arandanos',	9300.0,	5),
(5,	'Tomates',	2100.0,	42),
(6,	'Fresas',	4100.0,	3),
(7,	'helado',	4500.0,	41),
(8,	'galletitas',	500.0,	8),
(9,	'chocolatinas',	3500.0,	80),
(10,'jamón',	15000.0,	10);

SELECT * FROM Productos;