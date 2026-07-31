CREATE TABLE usuarios (
    id binary(16) NOT NULL,
    apellido varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    fecha_nacimiento date NOT NULL,
    nombre varchar(255) NOT NULL,
    activo bit(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UKkfsp0s1tflm1cwlj8idhqsad0 (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE libros (
    id bigint NOT NULL AUTO_INCREMENT,
    autor varchar(255) NOT NULL,
    edicion varchar(255) NOT NULL,
    fecha_publicacion date NOT NULL,
    isbn varchar(255) NOT NULL,
    titulo varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UK278ekmuxh4b6i98mqypvacdcu (isbn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE ejemplares (
    id bigint NOT NULL AUTO_INCREMENT,
    codigo varchar(255) NOT NULL,
    estado enum('BAJA','DETERIORADO','DISPONIBLE','PRESTADO') NOT NULL,
    libro_id bigint NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UKiht5xdr061w7g4qj3vvjt1839 (codigo),
    KEY FKa02r61vhwvk2papq1ifg5jmom (libro_id),
    CONSTRAINT FKa02r61vhwvk2papq1ifg5jmom FOREIGN KEY (libro_id) REFERENCES libros (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE prestamos (
    id bigint NOT NULL AUTO_INCREMENT,
    estado_prestamo enum('ACTIVO','DEVUELTO','VENCIDO') NOT NULL,
    fecha_devolucion date DEFAULT NULL,
    fecha_prestamo date NOT NULL,
    ejemplar_id bigint NOT NULL,
    usuario_id binary(16) NOT NULL,
    PRIMARY KEY (id),
    KEY FK19ckv1qyj2uxdqf6is5wp9kaq (ejemplar_id),
    KEY FKeqd1t799y0x5ck9mdeltepy1w (usuario_id),
    CONSTRAINT FK19ckv1qyj2uxdqf6is5wp9kaq FOREIGN KEY (ejemplar_id) REFERENCES ejemplares (id),
    CONSTRAINT FKeqd1t799y0x5ck9mdeltepy1w FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
