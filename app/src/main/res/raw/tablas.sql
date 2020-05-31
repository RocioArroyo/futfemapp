CREATE TABLE entrenador (
    ent_id           TEXT PRIMARY KEY,
    ent_nombre       TEXT NOT NULL,
    ent_apellido1    TEXT NOT NULL,
    ent_apellido2    TEXT,
    ent_fec_inicio   DATE,
    ent_equ          TEXT NOT NULL,
    FOREIGN KEY (ent_equ) REFERENCES equipo (equ_id)
);

CREATE TABLE equipo (
    equ_id             TEXT PRIMARY KEY,
    equ_nombre         TEXT NOT NULL,
    equ_posicion       INTEGER NOT NULL,
    equ_puntos         INTEGER NOT NULL,
    equ_par_ganados    INTEGER NOT NULL,
    equ_par_empatado   INTEGER NOT NULL,
    equ_par_perdidos   INTEGER NOT NULL,
    equ_goles_favor    INTEGER NOT NULL,
    equ_goles_contra   INTEGER NOT NULL
);

CREATE TABLE estadio (
    est_id          TEXT PRIMARY KEY,
    est_nombre      TEXT NOT NULL,
    est_com_auto    TEXT NOT NULL,
    est_ciudad      TEXT NOT NULL,
    est_direccion   TEXT,
    est_equ         TEXT NOT NULL,
    FOREIGN KEY (est_equ) REFERENCES equipo (est_id)
);

CREATE TABLE favoritos (
    fav_id     TEXT PRIMARY KEY,
    fav_equ    TEXT NOT NULL,
    fav_usu    TEXT NOT NULL,
    FOREIGN KEY (fav_equ) REFERENCES equipo (est_id),
    FOREIGN KEY (fav_usu) REFERENCES usuario (usu_id)
);

CREATE TABLE jugadora (
    jug_id           TEXT PRIMARY KEY,
    jug_nombre       TEXT NOT NULL,
    jug_apellido1    TEXT NOT NULL,
    jug_apeelido2    TEXT,
    jug_fec_nac      DATE NOT NULL,
    jug_fec_inicio   DATE,
    jug_posicion     TEXT NOT NULL,
    jug_dorsal       INTEGER NOT NULL,
    jug_equ          TEXT NOT NULL.
    FOREIGN KEY (jug_equ) REFERENCES equipo (equ_id)
);

CREATE TABLE partido (
    par_id                TEXT PRIMARY KEY,
    par_fecha_hora        DATE NOT NULL,
    par_goles_local       INTEGER NOT NULL,
    par_goles_visitante   INTEGER NOT NULL,
    par_equ_loc           TEXT NOT NULL,
    par_equ_vis           TEXT NOT NULL,
    FOREIGN KEY (par_equ_loc) REFERENCES equipo (equ_id),
    FOREIGN KEY (par_equ_vis) REFERENCES equipo (equ_id)
);

CREATE TABLE usuario (
    usu_id         TEXT PRIMARY KEY AUTOINCREMENT,
    usu_email      TEXT NOT NULL,
    usu_password   TEXT NOT NULL,
    usu_admin      INTEGER NOT NULL
);