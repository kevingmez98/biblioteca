# Biblioteca - Backend

API REST de un Sistema de Gestión de Biblioteca.

## Stack

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- Flyway
- MySQL 8.4
- Maven
- Docker / Docker Compose

## Inicio rápido (Docker)

1. Copia `.env.example` a `.env` y ajusta las credenciales si es necesario.
2. Levanta el stack:

```bash
docker compose up -d
```

La API queda disponible en `http://localhost:8080`.

## Configuración

Las credenciales se definen en `.env` (ver `.env.example`). Se usan dos usuarios
de MySQL con permisos separados:

| Variable | Usuario | Permisos | Propósito |
|---|---|---|---|
| `MYSQL_USER` / `MYSQL_PASSWORD` | admin | DDL | Ejecuta las migraciones de Flyway |
| `APP_USER` / `APP_PASSWORD` | app_user | DML (SELECT, INSERT, UPDATE, DELETE) | Conexión de la aplicación |

La aplicación se conecta con `app_user` (mínimo privilegio) y nunca modifica el esquema. El esquema lo gestiona exclusivamente Flyway, que se conecta como `admin` a través de un datasource separado (`spring.flyway.*`). Hibernate corre con `ddl-auto=validate` para solo detectar desfases.

### Credenciales y variables de entorno

| Variable | Valor de ejemplo | Función |
|---|---|---|
| `MYSQL_DATABASE` | `biblioteca` | Nombre de la base de datos |
| `MYSQL_USER` | `admin` | Usuario administrador; crea el esquema y aplica las migraciones de Flyway |
| `MYSQL_PASSWORD` | `D3v_2026!` | Contraseña de `MYSQL_USER` |
| `MYSQL_ROOT_PASSWORD` | `R00t_S3gur0_2026!` | Contraseña del usuario `root` de MySQL (solo uso administrativo) |
| `APP_USER` | `app_user` | Usuario de la aplicación; permisos solo DML |
| `APP_PASSWORD` | `App_D3v_2026!` | Contraseña de `APP_USER` |
| `DB_HOST` | `localhost` | Host de la base de datos (`mysql` dentro de Docker Compose) |
| `DB_PORT` | `3306` | Puerto de la base de datos |
| `DB_URL` | `jdbc:mysql://...` | URL completa de conexión (no se usa; se construye desde `DB_HOST`/`DB_PORT`) |
| `SERVER_PORT` | `8080` | Puerto donde escucha la API |

## Gestión de la base de datos

### Esquema y datos semilla (Flyway)

Las migraciones viven en `backend/src/main/resources/db/migration/` y se aplican
al arrancar el backend:

- `V1__init.sql` — esquema inicial.
- `V2__seed.sql` — usuarios y libros de ejemplo (10 usuarios, 8 libros).
- `V3__seed_ejemplares_prestamos.sql` — ejemplares y préstamos de ejemplo (15 ejemplares, 6 préstamos).

### Provisionamiento del usuario MySQL (solo primer arranque)

`db/init/01-app-user.sql` crea al usuario `app_user` (solo DML). El contenedor
oficial de MySQL ejecuta todo lo que esté en `/docker-entrypoint-initdb.d`
(montado desde `./db/init`) **solo en la primera inicialización del volumen**
(cuando `/var/lib/mysql` está vacío). No se ejecuta en arranques posteriores;
el usuario persiste en el volumen.

Para reconstruir todo desde cero (reproduce un entorno limpio):

```bash
docker compose down -v
docker compose up -d
```

### Backup de la base de datos (dump)

`backend/db/dumps/biblioteca.dump` es un backup completo de la base de datos
(esquema + datos de prueba + historial de Flyway) que el evaluador puede
restaurar en su propio entorno:

```bash
# Desde la raíz del repositorio, con el contenedor de MySQL corriendo
docker exec -i biblioteca-mysql mysql -u root -p < backend/db/dumps/biblioteca.dump
```

El archivo incluye la tabla `flyway_schema_history`, por lo que restaurarlo en
una base de datos vacía deja la aplicación funcionando sin que Flyway intente
re-aplicar las migraciones.

## Compilar y ejecutar (local)

```bash
mvn clean install
mvn spring-boot:run
```

Asegúrate de que el contenedor de MySQL (o una instancia local) esté corriendo y
de que exista `app_user`, o levanta el stack completo con `docker compose up -d`.

## Rutas de la API

### Usuarios (`/api/usuarios`)

| Método | Ruta | Función |
|---|---|---|
| `POST` | `/api/usuarios` | Registrar un usuario |
| `GET` | `/api/usuarios` | Listar los usuarios activos |
| `GET` | `/api/usuarios/{id}` | Obtener un usuario por id (UUID) |
| `PUT` | `/api/usuarios/{id}` | Actualizar un usuario |
| `PATCH` | `/api/usuarios/{id}/desactivar` | Desactivar un usuario (baja lógica) |

### Libros (`/api/libros`)

| Método | Ruta | Función |
|---|---|---|
| `POST` | `/api/libros` | Registrar un libro |
| `GET` | `/api/libros` | Listar los libros |
| `GET` | `/api/libros/{id}` | Obtener un libro por id |
| `PUT` | `/api/libros/{id}` | Actualizar un libro |
| `DELETE` | `/api/libros/{id}` | Eliminar un libro |

### Ejemplares (`/api/ejemplares`)

| Método | Ruta | Función |
|---|---|---|
| `POST` | `/api/ejemplares` | Registrar un ejemplar (código `<ISBN>-<secuencial>` autogenerado) |
| `GET` | `/api/ejemplares` | Listar los ejemplares |
| `GET` | `/api/ejemplares/libro/{libroId}` | Listar los ejemplares de un libro |
| `GET` | `/api/ejemplares/disponibles/{isbn}` | Listar los ejemplares disponibles de un libro por ISBN |
| `GET` | `/api/ejemplares/{id}` | Obtener un ejemplar por id |
| `PATCH` | `/api/ejemplares/{id}/dar-de-baja` | Dar de baja un ejemplar (bloqueado si está `PRESTADO`) |

### Préstamos (`/api/prestamos`)

| Método | Ruta | Función |
|---|---|---|
| `POST` | `/api/prestamos` | Registrar un préstamo (valida usuario sin préstamo activo y ejemplar disponible) |
| `GET` | `/api/prestamos/usuario/{usuarioId}` | Listar los préstamos de un usuario |
| `GET` | `/api/prestamos/libro/{libroId}` | Listar los préstamos de un libro |
| `PATCH` | `/api/prestamos/{id}/devolver` | Devolver un préstamo (el ejemplar vuelve a `DISPONIBLE`) |
