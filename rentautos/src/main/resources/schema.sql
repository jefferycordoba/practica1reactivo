CREATE SCHEMA IF NOT EXISTS rent_autos;
SET SCHEMA rent_autos;
CREATE TABLE IF NOT EXISTS automoviles
    (placa VARCHAR(100) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100),
    marca VARCHAR(60),
    precio NUMERIC(11,2),
    peso_neto NUMERIC(4),
    PRIMARY KEY (placa));

CREATE TABLE IF NOT EXISTS detalle_alquileres
    (reserva_nro INT NOT NULL AUTO_INCREMENT,
     serial_producto INT,
     datosAdicionales JSON,
    FOREIGN KEY (placa_vehiculo) REFERENCES productos(serial),
    FOREIGN KEY (placa_vehiculo) REFERENCES productos(serial),
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS clientes
    (id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100),
    marca VARCHAR(60),
    precio NUMERIC(11,2),
    peso_neto NUMERIC(4),
    PRIMARY KEY (id));
