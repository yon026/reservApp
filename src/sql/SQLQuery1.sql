Use ReservApp

CREATE TABLE Cancha(
numero INT PRIMARY KEY,
deporte VARCHAR(50) ,
precio MONEY,
disponible BIT
)

CREATE TABLE Cliente(
id INT NOT NULL PRIMARY KEY,
nombre VARCHAR(50),
telefono VARCHAR(50)
)

CREATE TABLE Horario(
id INT NOT NULL PRIMARY KEY,
horaInicio VARCHAR(50),
horaFin VARCHAR(50),
dia VARCHAR(50)
)

CREATE TABLE Reserva(
id INT NOT NULL PRIMARY KEY,
id_cliente INT,
cancha INT,
id_horario INT,
fecha DATETIME
FOREIGN KEY (id_cliente) REFERENCES Cliente(id),
FOREIGN KEY (cancha) REFERENCES Cancha(numero),
FOREIGN KEY (id_horario) REFERENCES Horario(id)
)

CREATE TABLE Pago(
id INT NOT NULL PRIMARY KEY,
id_reserva INT,
monto MONEY,
fecha DATETIME,
tipo VARCHAR(50),
FOREIGN KEY (id_reserva) REFERENCES Reserva(id)
)

