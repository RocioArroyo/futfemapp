-- Generado por Oracle SQL Developer Data Modeler 18.1.0.082.1035
--   en:        2020-05-30 16:58:47 CEST
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



CREATE TABLE entrenador (
    ent_id           VARCHAR(6) NOT NULL,
    ent_nombre       VARCHAR(100) NOT NULL,
    ent_apellido1    VARCHAR(100) NOT NULL,
    ent_apellido2    VARCHAR(100),
    ent_fec_inicio   DATE,
    ent_equ          VARCHAR(6) NOT NULL
);

ALTER TABLE entrenador ADD CONSTRAINT entrenador_pk PRIMARY KEY ( ent_id );

CREATE TABLE equipo (
    equ_id             VARCHAR(6) NOT NULL,
    equ_nombre         VARCHAR(100) NOT NULL,
    equ_posicion       NUMERIC(2) NOT NULL,
    equ_puntos         NUMERIC(3) NOT NULL,
    equ_par_ganados    NUMERIC(2) NOT NULL,
    equ_par_empatado   NUMERIC(2) NOT NULL,
    equ_par_perdidos   NUMERIC(2) NOT NULL,
    equ_goles_favor    NUMERIC(3) NOT NULL,
    equ_goles_contra   NUMERIC(3) NOT NULL
);

ALTER TABLE equipo ADD CONSTRAINT equipo_pk PRIMARY KEY ( equ_id );

CREATE TABLE estadio (
    est_id          VARCHAR(6) NOT NULL,
    est_nombre      VARCHAR(100) NOT NULL,
    est_com_auto    VARCHAR(100) NOT NULL,
    est_ciudad      VARCHAR(100) NOT NULL,
    est_direccion   VARCHAR(200),
    est_equ         VARCHAR(6) NOT NULL
);

ALTER TABLE estadio ADD CONSTRAINT estadio_pk PRIMARY KEY ( est_id );

CREATE TABLE favoritos (
    fav_id     VARCHAR(6) NOT NULL,
    fav_equ    VARCHAR(6) NOT NULL,
    fav_usu    VARCHAR(340) NOT NULL
);

ALTER TABLE favoritos ADD CONSTRAINT favoritos_pk PRIMARY KEY ( fav_id );

CREATE TABLE jugadora (
    jug_id           VARCHAR(6) NOT NULL,
    jug_nombre       VARCHAR(100) NOT NULL,
    jug_apellido1    VARCHAR(100) NOT NULL,
    jug_apeelido2    VARCHAR(100),
    jug_fec_nac      DATE NOT NULL,
    jug_fec_inicio   DATE,
    jug_posicion     VARCHAR(20) NOT NULL,
    jug_dorsal       NUMERIC(2) NOT NULL,
    jug_equ          VARCHAR(6) NOT NULL
);

ALTER TABLE jugadora ADD CONSTRAINT jugadora_pk PRIMARY KEY ( jug_id );

CREATE TABLE partido (
    par_id                VARCHAR(6) NOT NULL,
    par_fecha_hora        DATE NOT NULL,
    par_goles_local       NUMERIC(2) NOT NULL,
    par_goles_visitante   NUMERIC(2) NOT NULL,
    par_equ_loc           VARCHAR(6) NOT NULL,
    par_equ_vis           VARCHAR(6) NOT NULL
);

ALTER TABLE partido ADD CONSTRAINT partido_pk PRIMARY KEY ( par_id );

CREATE TABLE usuario (
    usu_id         VARCHAR (6) NOT NULL,
    usu_email      VARCHAR(340 ) NOT NULL,
    usu_password   VARCHAR(100) NOT NULL,
    usu_admin      NUMERIC(1) NOT NULL
);

ALTER TABLE usuario ADD CONSTRAINT usuario_pk PRIMARY KEY ( usu_id );

ALTER TABLE entrenador
    ADD CONSTRAINT ent_equ_fk FOREIGN KEY ( ent_equ )
        REFERENCES equipo ( equ_id );

ALTER TABLE estadio
    ADD CONSTRAINT est_equ_fk FOREIGN KEY ( est_equ )
        REFERENCES equipo ( equ_id );

ALTER TABLE favoritos
    ADD CONSTRAINT fav_equ_fk FOREIGN KEY ( fav_equ )
        REFERENCES equipo ( equ_id );

ALTER TABLE favoritos
    ADD CONSTRAINT fav_usu_fk FOREIGN KEY ( fav_usu )
        REFERENCES usuario ( usu_id );

ALTER TABLE jugadora
    ADD CONSTRAINT jug_equ_fk FOREIGN KEY ( jug_equ )
        REFERENCES equipo ( equ_id );

ALTER TABLE partido
    ADD CONSTRAINT par_equl_fk FOREIGN KEY ( par_equ_loc )
        REFERENCES equipo ( equ_id );

ALTER TABLE partido
    ADD CONSTRAINT par_equv_fk FOREIGN KEY ( par_equ_vis )
        REFERENCES equipo ( equ_id );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             7
-- CREATE INDEX                             0
-- ALTER TABLE                             14
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
