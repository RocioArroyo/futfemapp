package com.rocioarroyo.futfemapp.dto;

import java.io.Serializable;
import java.util.Date;

public class PartidoDTO implements Serializable {

    private String parId;
    private Date parFechaHora;
    private int parGolesLocal;
    private int parGolesVisitante;

    public PartidoDTO() {
    }

    public PartidoDTO(String parId, Date parFechaHora, int parGolesLocal, int parGolesVisitante) {
        this.parId = parId;
        this.parFechaHora = parFechaHora;
        this.parGolesLocal = parGolesLocal;
        this.parGolesVisitante = parGolesVisitante;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public Date getParFechaHora() {
        return parFechaHora;
    }

    public void setParFechaHora(Date parFechaHora) {
        this.parFechaHora = parFechaHora;
    }

    public int getParGolesLocal() {
        return parGolesLocal;
    }

    public void setParGolesLocal(int parGolesLocal) {
        this.parGolesLocal = parGolesLocal;
    }

    public int getParGolesVisitante() {
        return parGolesVisitante;
    }

    public void setParGolesVisitante(int parGolesVisitante) {
        this.parGolesVisitante = parGolesVisitante;
    }
}
