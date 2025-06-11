-- Limpiar tablas si existen (opcional)
-- DELETE FROM tasks;
-- DELETE FROM proyectos;

-- Insertar proyectos
INSERT INTO proyectos (id, name, description) VALUES
(1, 'Rediseño de Sitio Web', 'Renovación completa del sitio web corporativo'),
(2, 'App Móvil de Clientes', 'Desarrollo de aplicación para iOS y Android'),
(3, 'Migración a la Nube', 'Traslado de infraestructura a AWS'),
(4, 'Campaña Navideña', 'Promociones de fin de año'),
(5, 'Capacitación DevOps', 'Formación en herramientas CI/CD');

-- Insertar tareas relacionadas a proyectos
INSERT INTO tasks (id, title, description, due_date, created_at, updated_at, project_id) VALUES
-- Tareas para Rediseño de Sitio Web (ID 1)
(1, 'Diseño UI/UX', 'Crear wireframes y prototipos', '2024-07-15', '2024-06-01', '2024-06-10', 1),
(2, 'Desarrollo Frontend', 'Implementar con React', '2024-08-20', '2024-06-01', '2024-06-05', 1),
(3, 'Pruebas de Usabilidad', 'Test con usuarios reales', '2024-09-05', '2024-06-15', '2024-06-15', 1),

-- Tareas para App Móvil (ID 2)
(4, 'Análisis de Requisitos', 'Reuniones con stakeholders', '2024-06-30', '2024-05-20', '2024-06-01', 2),
(5, 'API Backend', 'Desarrollar servicios REST', '2024-07-25', '2024-06-01', '2024-06-10', 2),
(6, 'Pruebas en Dispositivos', 'QA en diferentes móviles', '2024-08-30', '2024-06-15', '2024-06-15', 2),

-- Tareas para Migración a la Nube (ID 3)
(7, 'Inventario de Servidores', 'Documentar recursos actuales', '2024-06-20', '2024-05-15', '2024-05-20', 3),
(8, 'Configurar VPC', 'Redes en AWS', '2024-07-10', '2024-06-01', '2024-06-05', 3),
(9, 'Migración de Datos', 'Transferir bases de datos', '2024-08-01', '2024-06-15', '2024-06-15', 3),

-- Tareas para Campaña Navideña (ID 4)
(10, 'Diseño Creativo', 'Banners y materiales gráficos', '2024-10-15', '2024-09-01', '2024-09-10', 4),
(11, 'Plan de Medios', 'Comprar espacios publicitarios', '2024-11-01', '2024-09-15', '2024-09-20', 4),

-- Tareas para Capacitación DevOps (ID 5)
(12, 'Selección de Proveedor', 'Evaluar opciones de formación', '2024-07-05', '2024-06-01', '2024-06-10', 5),
(13, 'Agendar Sesiones', 'Coordinar con equipos', '2024-07-15', '2024-06-10', '2024-06-12', 5);

-- Reiniciar secuencias para autoincremento
ALTER SEQUENCE IF EXISTS proyectos_id_seq RESTART WITH 6;
ALTER SEQUENCE IF EXISTS tasks_id_seq RESTART WITH 14;