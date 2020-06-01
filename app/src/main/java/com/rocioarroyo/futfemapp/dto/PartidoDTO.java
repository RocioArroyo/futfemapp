package com.rocioarroyo.futfemapp.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class PartidoDTO implements Parcelable {

    private String parId;
    private String parFechaHora;
    private int parGolesLocal;
    private int parGolesVisitante;
    private int parJornada;
    private String parEquLocal;
    private String parEquVisitante;

    protected PartidoDTO(Parcel in) {
        parId = in.readString();
        parGolesLocal = in.readInt();
        parGolesVisitante = in.readInt();
        parJornada = in.readInt();
        parEquLocal = in.readString();
        parEquVisitante = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(parId);
        dest.writeInt(parGolesLocal);
        dest.writeInt(parGolesVisitante);
        dest.writeInt(parJornada);
        dest.writeString(parEquLocal);
        dest.writeString(parEquVisitante);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PartidoDTO> CREATOR = new Creator<PartidoDTO>() {
        @Override
        public PartidoDTO createFromParcel(Parcel in) {
            return new PartidoDTO(in);
        }

        @Override
        public PartidoDTO[] newArray(int size) {
            return new PartidoDTO[size];
        }
    };

    public int getParJornada() {
        return parJornada;
    }

    public PartidoDTO(String parId, String parFechaHora, int parGolesLocal, int parGolesVisitante, int parJornada, String parEquLocal, String parEquVisitante) {
        this.parId = parId;
        this.parFechaHora = parFechaHora;
        this.parGolesLocal = parGolesLocal;
        this.parGolesVisitante = parGolesVisitante;
        this.parJornada = parJornada;
        this.parEquLocal = parEquLocal;
        this.parEquVisitante = parEquVisitante;
    }

    public void setParJornada(int parJornada) {
        this.parJornada = parJornada;
    }

    public PartidoDTO() {
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParFechaHora() {
        return parFechaHora;
    }

    public void setParFechaHora(String parFechaHora) {
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

    public String getParEquLocal() {
        return parEquLocal;
    }

    public void setParEquLocal(String parEquLocal) {
        this.parEquLocal = parEquLocal;
    }

    public String getParEquVisitante() {
        return parEquVisitante;
    }

    public void setParEquVisitante(String parEquVisitante) {
        this.parEquVisitante = parEquVisitante;
    }
}
