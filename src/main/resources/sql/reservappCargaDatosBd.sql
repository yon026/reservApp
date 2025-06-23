--use ReservasDB;
-- Datos para Sexos
INSERT INTO Sexos (descripcion) VALUES ('masculino');
INSERT INTO Sexos (descripcion) VALUES ('femenino');
INSERT INTO Sexos (descripcion) VALUES ('otro');

-- Datos para Canchas
INSERT INTO Canchas (nombreCancha) VALUES ('futbol');
INSERT INTO Canchas (nombreCancha) VALUES ('padel');
INSERT INTO Canchas (nombreCancha) VALUES ('voley');

-- Datos para Horarios
INSERT INTO Horarios (hora) VALUES ('16:00:00');
INSERT INTO Horarios (hora) VALUES ('17:00:00');
INSERT INTO Horarios (hora) VALUES ('18:00:00');
INSERT INTO Horarios (hora) VALUES ('19:00:00');
INSERT INTO Horarios (hora) VALUES ('20:00:00');
INSERT INTO Horarios (hora) VALUES ('21:00:00');
INSERT INTO Horarios (hora) VALUES ('22:00:00');
INSERT INTO Horarios (hora) VALUES ('23:00:00');
INSERT INTO Horarios (hora) VALUES ('00:00:00');

--Datos de prueba para Reservas 19/06
INSERT INTO Reservas (nombres, apellidos, fksexo, edad, fecha, fkcancha, fkhorario) VALUES
('Juan', 'Perez', 1, 28, '2025-06-17', 1, 1),  --Masculino, 28, 17/06, Futbol, 16:00
('Maria', 'Gomez', 2, 35, '2025-06-17', 2, 2), --Femenino, 35, 17/06, Padel, 17:00
('Carlos', 'Lopez', 1, 22, '2025-06-17', 3, 3), --Masculino, 22, 17/06, Voley, 18:00
('Ana', 'Rodriguez', 2, 41, '2025-06-18', 1, 4), --Femenino, 41, 18/06, Futbol, 19:00
('Pedro', 'Martinez', 1, 30, '2025-06-18', 2, 5),--Masculino, 30, 18/06, Padel, 20:00
('Laura', 'Diaz', 2, 25, '2025-06-18', 3, 6),  --Femenino, 25, 18/06, Voley, 21:00
('Sofia', 'Hernandez', 2, 19, '2025-06-19', 1, 7),--Femenino, 19, 19/06, Futbol, 22:00
('Diego', 'Sanchez', 1, 33, '2025-06-19', 2, 8),--Masculino, 33, 19/06, Padel, 23:00
('Valeria', 'Torres', 2, 29, '2025-06-19', 3, 9),--Femenino, 29, 19/06, Voley, 00:00
('Gabriel', 'Ruiz', 1, 45, '2025-06-20', 1, 1); --Masculino, 45, 19/06, Futbol, 16:00

