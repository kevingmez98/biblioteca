-- Datos semilla: ejemplares y prestamos que respetan las reglas de negocio.
-- Reglas aplicadas:
--   * Un usuario no puede tener mas de un prestamo ACTIVO.
--   * Un ejemplar solo puede estar PRESTADO si tiene un prestamo ACTIVO o VENCIDO.
--   * Los prestamos DEVUELTOS devuelven el ejemplar a DISPONIBLE.
--   * El codigo del ejemplar sigue el formato <ISBN>-<secuencial por libro>.
--   * Un ejemplar PRESTADO no puede darse de baja (no se incluyen en ese estado).

INSERT INTO ejemplares (codigo, estado, libro_id) VALUES
('9788437604947-001', 'PRESTADO',   1),
('9788437604947-002', 'DISPONIBLE', 1),
('9788437604947-003', 'DISPONIBLE', 1),
('9788467029797-001', 'PRESTADO',   2),
('9788467029797-002', 'DISPONIBLE', 2),
('9788417241126-001', 'DISPONIBLE', 3),
('9788417241126-002', 'DISPONIBLE', 3),
('9788499890944-001', 'PRESTADO',   4),
('9788499890944-002', 'DISPONIBLE', 4),
('9788420471839-001', 'DISPONIBLE', 5),
('9788401341025-001', 'DISPONIBLE', 6),
('9788401341025-002', 'DISPONIBLE', 6),
('9788420674270-001', 'DETERIORADO', 7),
('9788420674270-002', 'DISPONIBLE', 7),
('9788491050069-001', 'DISPONIBLE', 8);

INSERT INTO prestamos (fecha_prestamo, fecha_devolucion, estado_prestamo, ejemplar_id, usuario_id) VALUES
('2026-07-10', NULL, 'ACTIVO',   (SELECT id FROM ejemplares WHERE codigo = '9788437604947-001'), UNHEX('eaf688819634474196bba34f11980c71')),
('2026-07-15', NULL, 'ACTIVO',   (SELECT id FROM ejemplares WHERE codigo = '9788499890944-001'), UNHEX('8f29d145212744508d1da8de241df187')),
('2026-05-01', NULL, 'VENCIDO',  (SELECT id FROM ejemplares WHERE codigo = '9788467029797-001'), UNHEX('b8e769b75295404b886d83de82e055e1')),
('2026-06-01', '2026-06-15', 'DEVUELTO', (SELECT id FROM ejemplares WHERE codigo = '9788417241126-001'), UNHEX('1bf277600dd14f69b3a9b7c95b89801d')),
('2026-06-05', '2026-06-20', 'DEVUELTO', (SELECT id FROM ejemplares WHERE codigo = '9788420471839-001'), UNHEX('f7a1e3bd7beb4c8796648457fba8e5d5')),
('2026-06-10', '2026-06-24', 'DEVUELTO', (SELECT id FROM ejemplares WHERE codigo = '9788401341025-001'), UNHEX('81f9fa78c1e64009aa921286ce3a0400'));
