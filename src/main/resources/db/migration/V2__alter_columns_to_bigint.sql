ALTER TABLE roles ALTER COLUMN id TYPE BIGINT;
ALTER TABLE users ALTER COLUMN id TYPE BIGINT;
ALTER TABLE user_roles ALTER COLUMN user_id TYPE BIGINT;
ALTER TABLE user_roles ALTER COLUMN role_id TYPE BIGINT;
ALTER TABLE permission ALTER COLUMN id TYPE BIGINT;
ALTER TABLE role_permission ALTER COLUMN role_id TYPE BIGINT;
ALTER TABLE role_permission ALTER COLUMN permission_id TYPE BIGINT;
ALTER TABLE service ALTER COLUMN id TYPE BIGINT;
ALTER TABLE appointment ALTER COLUMN id TYPE BIGINT;
ALTER TABLE appointment ALTER COLUMN service_id TYPE BIGINT;
ALTER TABLE appointment ALTER COLUMN user_id TYPE BIGINT;
ALTER TABLE appointment ADD COLUMN email VARCHAR(255) NOT NULL DEFAULT 'placeholder@example.com';
ALTER TABLE schedule ALTER COLUMN id TYPE BIGINT;
INSERT INTO roles (name) VALUES ('CLIENT');
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('EMPLOYEE');

-- Insertar datos inventados en la tabla de servicios
INSERT INTO service (name, duration, price) VALUES ('Corte de Pelo Básico', 30, 15.00);
INSERT INTO service (name, duration, price) VALUES ('Peinado y Estilizado', 45, 25.00);
INSERT INTO service (name, duration, price) VALUES ('Coloración Completa', 120, 80.00);
INSERT INTO service (name, duration, price) VALUES ('Manicura', 60, 20.00);
INSERT INTO service (name, duration, price) VALUES ('Pedicura', 75, 30.00);

-- Insertar datos inventados en la tabla de horarios (schedule)
INSERT INTO schedule (day, start_time, end_time) VALUES ('MONDAY', '09:00:00', '17:00:00');
INSERT INTO schedule (day, start_time, end_time) VALUES ('TUESDAY', '09:00:00', '17:00:00');
INSERT INTO schedule (day, start_time, end_time) VALUES ('WEDNESDAY', '09:00:00', '17:00:00');
INSERT INTO schedule (day, start_time, end_time) VALUES ('THURSDAY', '09:00:00', '17:00:00');
INSERT INTO schedule (day, start_time, end_time) VALUES ('FRIDAY', '09:00:00', '17:00:00');
INSERT INTO schedule (day, start_time, end_time) VALUES ('SATURDAY', '10:00:00', '15:00:00');
INSERT INTO schedule (day, start_time, end_time) VALUES ('SUNDAY', '11:00:00', '15:00:00');

