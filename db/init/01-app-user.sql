-- Crea el usuario de la aplicacion con permisos unicamente DML (SELECT, INSERT, UPDATE, DELETE).
-- El esquema (DDL) lo gestiona Flyway usando el usuario MYSQL_USER (admin).
CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'App_D3v_2026!';
GRANT SELECT, INSERT, UPDATE, DELETE ON biblioteca.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
