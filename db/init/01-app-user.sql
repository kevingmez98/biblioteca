-- ----------------------------------------------------------------------------
-- Inicializacion de MySQL (contenedor oficial: docker-entrypoint-initdb.d)
--
-- Este script lo ejecuta el contenedor de MySQL SOLO en la primera
-- inicializacion del volumen (cuando /var/lib/mysql esta vacio). En arranques
-- posteriores no se vuelve a ejecutar; el usuario creado persiste en la tabla
-- mysql.user del volumen.
--
-- Que hace: crea el usuario de la aplicacion con permisos unicamente DML
-- (SELECT, INSERT, UPDATE, DELETE). El esquema (DDL) NO lo toca esta app:
-- lo gestiona Flyway como MYSQL_USER (admin) al arrancar el backend.
-- ----------------------------------------------------------------------------
CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'App_D3v_2026!';
GRANT SELECT, INSERT, UPDATE, DELETE ON biblioteca.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
