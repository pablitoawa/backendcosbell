ALTER TABLE appointment
ADD COLUMN phone VARCHAR(255) NOT NULL DEFAULT '';

ALTER TABLE appointment
ADD COLUMN employee_id BIGINT NOT NULL DEFAULT 0;

ALTER TABLE appointment
ADD CONSTRAINT fk_employee
FOREIGN KEY (employee_id)
REFERENCES users(id);

-- Actualizar las citas existentes con un valor predeterminado para 'phone' y 'employee_id'
-- Esto es necesario si ya tienes datos en la tabla 'appointment' y las columnas se añaden como NOT NULL.
-- Puedes ajustar estos valores predeterminados según tu lógica de negocio.
UPDATE appointment SET phone = '', employee_id = (SELECT id FROM users LIMIT 1) WHERE phone = ''; -- Asigna el primer usuario como empleado predeterminado 