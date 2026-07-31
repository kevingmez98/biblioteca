-- Datos semilla: 10 usuarios y 8 libros de ejemplo.
-- Los IDs de usuario se insertan con UNHEX para que coincidan con el formato binary(16) de Hibernate.

INSERT INTO usuarios (id, apellido, email, fecha_nacimiento, nombre, activo) VALUES
(UNHEX('eaf688819634474196bba34f11980c71'), 'García Ruiz', 'anagarcia@gmail.com', '1992-04-15', 'Ana', b'1'),
(UNHEX('8f29d145212744508d1da8de241df187'), 'Fernández Pérez', 'luisfernandez@hotmail.com', '1988-11-02', 'Luis', b'1'),
(UNHEX('b8e769b75295404b886d83de82e055e1'), 'Torres Gómez', 'mariatorres@yahoo.com', '1995-07-23', 'María', b'1'),
(UNHEX('1bf277600dd14f69b3a9b7c95b89801d'), 'Martínez López', 'carlosmartinez@gmail.com', '1990-01-30', 'Carlos', b'1'),
(UNHEX('f7a1e3bd7beb4c8796648457fba8e5d5'), 'Hernández Castro', 'luciahernandez@outlook.com', '1998-09-12', 'Lucía', b'1'),
(UNHEX('81f9fa78c1e64009aa921286ce3a0400'), 'Ramírez Silva', 'jorgeramirez@gmail.com', '1985-03-07', 'Jorge', b'1'),
(UNHEX('8f8fa6fa77cd43cf8da98f14e63b23c9'), 'Díaz Molina', 'sofiadiaz@hotmail.com', '2000-12-19', 'Sofía', b'1'),
(UNHEX('d91fb0f981d84f31aee983f41b21d8cd'), 'Morales Vega', 'andresmorales@yahoo.com', '1993-06-25', 'Andrés', b'1'),
(UNHEX('db9759a42d004fb0ad1b9af402169170'), 'Rojas Peña', 'valentinarojas@gmail.com', '1996-08-14', 'Valentina', b'1'),
(UNHEX('02381f3d61d54652a4b223c52d88f160'), 'Navarro Ortiz', 'diegonavarro@outlook.com', '1989-02-11', 'Diego', b'1');

INSERT INTO libros (titulo, isbn, edicion, fecha_publicacion, autor) VALUES
('Cien años de soledad', '9788437604947', '1ª', '1967-05-30', 'Gabriel García Márquez'),
('Don Quijote de la Mancha', '9788467029797', '4ª', '1605-01-16', 'Miguel de Cervantes'),
('El Principito', '9788417241126', '3ª', '1943-04-06', 'Antoine de Saint-Exupéry'),
('1984', '9788499890944', '2ª', '1949-06-08', 'George Orwell'),
('Rayuela', '9788420471839', '1ª', '1963-06-28', 'Julio Cortázar'),
('La casa de los espíritus', '9788401341025', '5ª', '1982-01-01', 'Isabel Allende'),
('Crimen y castigo', '9788420674270', '2ª', '1866-01-01', 'Fiódor Dostoievski'),
('Orgullo y prejuicio', '9788491050069', '1ª', '1813-01-28', 'Jane Austen');
