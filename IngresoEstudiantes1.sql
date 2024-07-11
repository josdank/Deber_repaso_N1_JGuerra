CREATE DATABASE Escuela;
USE Escuela;

CREATE TABLE Estudiante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    curso VARCHAR(50) NOT NULL,
    nota1 DECIMAL(5, 2) NOT NULL,
    nota2 DECIMAL(5, 2) NOT NULL
);

select * from Estudiante;