-- CREATE DATABASE ReservasDB;

CREATE TABLE Sexos (
    id INT PRIMARY KEY IDENTITY(1,1),
    descripcion NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Canchas (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombreCancha NVARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE Horarios (
    id INT PRIMARY KEY IDENTITY(1,1),
    hora TIME NOT NULL UNIQUE
);


CREATE TABLE Reservas (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombres NVARCHAR(100) NOT NULL,
    apellidos NVARCHAR(100) NOT NULL,
    fksexo INT NOT NULL,
    edad INT NOT NULL,
    fecha DATE NOT NULL,
    fkcancha INT NOT NULL,
    fkhorario INT NOT NULL
);

--Incorporacion de llaves foraneas
ALTER TABLE Reservas
ADD CONSTRAINT FK_Reservas_Sexos FOREIGN KEY (fksexo) REFERENCES Sexos(id);

ALTER TABLE Reservas
ADD CONSTRAINT FK_Reservas_Canchas FOREIGN KEY (fkcancha) REFERENCES Canchas(id);

ALTER TABLE Reservas
ADD CONSTRAINT FK_Reservas_Horarios FOREIGN KEY (fkhorario) REFERENCES Horarios(id);


--Incorporando Restriccion UNIQUE (para evitar sobre-reservas en una misma /fecha/cancha/hora)
ALTER TABLE Reservas
ADD CONSTRAINT UQ_FechaCanchaHorario UNIQUE (fecha, fkcancha, fkhorario);