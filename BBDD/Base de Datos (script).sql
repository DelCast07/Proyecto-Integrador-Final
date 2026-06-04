DROP TABLE IF EXISTS COLABORA_EN;
DROP TABLE IF EXISTS CITA;
DROP TABLE IF EXISTS TRAJE;
DROP TABLE IF EXISTS EMPLEADO;
DROP TABLE IF EXISTS TALLER;
DROP TABLE IF EXISTS CLIENTE;

CREATE TABLE CLIENTE (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY, -- Para que el id empiece en 1 y cambie en cada creación
    nombre VARCHAR(100) NOT NULL,
    superpoder VARCHAR(100),
    colores VARCHAR(100)
    ) AUTO_INCREMENT = 1;

CREATE TABLE TALLER (
    id_taller INT AUTO_INCREMENT PRIMARY KEY,
    nombre_sala VARCHAR(50) NOT NULL,
    tipo_sala ENUM('diseño', 'costura', 'pruebas') NOT NULL -- ENUM para que solo puedan ser esas opciones
);

CREATE TABLE EMPLEADO (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apodo VARCHAR(50) UNIQUE NOT NULL,
    categoria ENUM('Aprendiz', 'Oficial', 'Maestro') NOT NULL,
    contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE TRAJE (
    id_traje INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    estado ENUM('en diseño', 'costura', 'taller') NOT NULL,
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente) ON DELETE CASCADE
);

CREATE TABLE CITA (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    dia DATE NOT NULL,
    hora TIME NOT NULL,
    duracion INT DEFAULT 1, -- Por defecto dura 1 hora
    id_cliente INT,
    id_taller INT,
    id_traje INT,
    id_empleado_responsable INT,
    id_aprendiz1 INT, -- ¡NUEVA COLUMNA PARA JAVA!
    id_aprendiz2 INT, -- ¡NUEVA COLUMNA PARA JAVA!
    FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente) ON DELETE CASCADE,
    FOREIGN KEY (id_taller) REFERENCES TALLER(id_taller),
    FOREIGN KEY (id_empleado_responsable) REFERENCES EMPLEADO(id_empleado),
    FOREIGN KEY (id_traje) REFERENCES TRAJE(id_traje) ON DELETE CASCADE,
    FOREIGN KEY (id_aprendiz1) REFERENCES EMPLEADO(id_empleado), -- RELACIÓN APRENDIZ 1
    FOREIGN KEY (id_aprendiz2) REFERENCES EMPLEADO(id_empleado)  -- RELACIÓN APRENDIZ 2
);
-- Relación
CREATE TABLE COLABORA_EN (
    id_empleado INT,
    id_cita INT,
    PRIMARY KEY (id_empleado, id_cita),
    FOREIGN KEY (id_empleado) REFERENCES EMPLEADO(id_empleado),
    FOREIGN KEY (id_cita) REFERENCES CITA(id_cita) ON DELETE CASCADE
);

-- Inserción de datos (3 de momento)
INSERT INTO TALLER (nombre_sala, tipo_sala) VALUES 
('Milán', 'diseño'),
('París', 'costura'),
('Berlin', 'costura'),
('Florencia', 'pruebas'),
('Barcelona', 'diseño'),
('Estambul', 'pruebas'),
('Londres', 'diseño'),
('Lisboa', 'costura'),
('Lyon', 'diseño'),
('Madrid', 'costura');

INSERT INTO EMPLEADO (nombre, apodo, categoria, contrasena) VALUES 
('Diego', 'Abuelo', 'Maestro', '1111'),
('Pablo', 'LaTen', 'Aprendiz', '2222'),
('Roberto', 'Bobby', 'Oficial', '3333'),
('Victor', 'TungTung', 'Aprendiz', '4444'),
('Diego', 'Yeyo', 'Oficial', '5555'),
('Guille', 'Will', 'Oficial', '6666'),
('Enzo', 'RRR', 'Aprendiz', '7777'),
('Manuel', 'Patriarca', 'Maestro', '8888'),
('Alvaro', 'Leyenda', 'Oficial', '9999'),
('Lucas', 'Lobo', 'Maestro', '0000');

INSERT INTO CLIENTE (nombre, superpoder, colores) VALUES 
('Mr. Increíble', 'Superfuerza', 'Rojo y Negro'),
('Spiderman', 'Trepar', 'Rojo y Azul'),
('Iron Man', 'Armadura Tecnológica', 'Rojo y Dorado'),
('Batman', 'Gadgets', 'Negro y gris'),
('Superman', 'Vuelo', 'Azul, rojo y amarillo'),
('Flash', 'Súper velocidad', 'Rojo y amarillo'),
('Thor', 'Control del trueno', 'Rojo, plateado y negro'),
('Capitán América', 'Escudo indestructible', 'Azul, rojo y blanco'),
('Black Panther', 'Agilidad', 'Negro y plateado'),
('Síndrome', 'Tecnología', 'Blanco y Negro');

INSERT INTO TRAJE (nombre, estado, id_cliente) VALUES 
('Traje Increíble', 'en diseño', 1),
('Traje Arácnido', 'en diseño', 2),
('Armadura Reactor', 'costura', 3),
('Traje Nocturno', 'taller', 4),
('Traje Krypton', 'taller', 5),
('Traje Velocidad', 'en diseño', 6),
('Armadura Asgardiana', 'costura', 7),
('Traje Patriota', 'costura', 8),
('Traje Wakanda', 'taller', 9),
('Traje Tecnológico', 'costura', 10);

INSERT INTO CITA (dia, hora, duracion, id_cliente, id_taller, id_traje, id_empleado_responsable) VALUES 
('2026-05-01', '09:00:00', 1, 1, 1, 1, 1), 
('2026-05-01', '11:00:00', 2, 2, 2, 2, 2),  
('2026-05-02', '10:30:00', 1, 3, 3, 3, 10), 
('2026-05-02', '16:00:00', 1, 4, 4, 4, 1),  
('2026-05-03', '12:00:00', 3, 5, 5, 5, 2),  
('2026-05-04', '08:30:00', 1, 6, 6, 6, 5),  
('2026-05-04', '17:30:00', 2, 7, 7, 7, 3), 
('2026-05-05', '09:00:00', 1, 8, 8, 8, 3), 
('2026-05-05', '13:00:00', 1, 9, 9, 9, 3),
('2026-05-06', '10:00:00', 2, 10, 10, 10, 3);
