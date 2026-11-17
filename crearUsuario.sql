CREATE TABLE USUARIOS (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR2(50) NOT NULL UNIQUE,
    correo VARCHAR2(100) NOT NULL,
    password VARCHAR2(100) NOT NULL,
    rol VARCHAR2(30) NOT NULL 
);

INSERT INTO USUARIOS (username, correo, password, rol)
VALUES ('admin_duoc', 'admin@duoc.cl', 'admin123', 'ADMIN');

INSERT INTO USUARIOS (username, correo, password, rol)
VALUES ('carlitos_dev', 'carlitos@duoc.cl', 'java2026', 'DOCENTE');

COMMIT;

SELECT * FROM USUARIOS;