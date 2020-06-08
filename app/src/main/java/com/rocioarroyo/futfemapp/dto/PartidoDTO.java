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

    /**
     * Método para leer la clase
     * @param in
     */
    private PartidoDTO(Parcel in) {
        parId = in.readString();
        parFechaHora = in.readString();
        parGolesLocal = in.readInt();
        parGolesVisitante = in.readInt();
        parJornada = in.readInt();
        parEquLocal = in.readString();
        parEquVisitante = in.readString();
    }

    /**
     * Método para escribir la clase
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(parId);
        dest.writeString(parFechaHora);
        dest.writeInt(parGolesLocal);
        dest.writeInt(parGolesVisitante);
        dest.writeInt(parJornada);
        dest.writeString(parEquLocal);
        dest.writeString(parEquVisitante);
    }

    /**
     * Método apra describir el contenido
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Meotod apra hacer Creator de la clase
     */
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

    /**
     * getParJornada
     * @return parJornada
     */
    public int getParJornada() {
        return parJornada;
    }

    /**
     * Constructor con todos los campos de la calse
     * @param parId
     * @param parFechaHora
     * @param parGolesLocal
     * @param parGolesVisitante
     * @param parJornada
     * @param parEquLocal
     * @param parEquVisitante
     */
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

    /**
     * Constructor vacio
     */
    public PartidoDTO() {
    }

    /**
     * getParId
     * @return parId
     */
    public String getParId() {
        return parId;
    }

    /**
     * setParId
     * @param parId
     */
    public void setParId(String parId) {
        this.parId = parId;
    }

    /**
     * getParFechaHora
     * @return parFechaHora
     */
    public String getParFechaHora() {
        return parFechaHora;
    }

    /**
     * setParFechaHora
     * @param parFechaHora
     */
    public void setParFechaHora(String parFechaHora) {
        this.parFechaHora = parFechaHora;
    }

    /**
     * getParGolesLocal
     * @return parGolesLocal
     */
    public int getParGolesLocal() {
        return parGolesLocal;
    }

    /**
     * setParGolesLocal
     * @param parGolesLocal
     */
    public void setParGolesLocal(int parGolesLocal) {
        this.parGolesLocal = parGolesLocal;
    }

    /**
     * getParGolesVisitante
     * @return parGolesVisitante
     */
    public int getParGolesVisitante() {
        return parGolesVisitante;
    }

    /**
     * setParGolesVisitante
     * @param parGolesVisitante
     */
    public void setParGolesVisitante(int parGolesVisitante) {
        this.parGolesVisitante = parGolesVisitante;
    }

    /**
     * getParEquLocal
     * @return parEquLocal
     */
    public String getParEquLocal() {
        return parEquLocal;
    }

    /**
     * setParEquLocal
     * @param parEquLocal
     */
    public void setParEquLocal(String parEquLocal) {
        this.parEquLocal = parEquLocal;
    }

    /**
     * getParEquVisitante
     * @return parEquVisitante
     */
    public String getParEquVisitante() {
        return parEquVisitante;
    }

    /**
     * setParEquVisitante
     * @param parEquVisitante
     */
    public void setParEquVisitante(String parEquVisitante) {
        this.parEquVisitante = parEquVisitante;
    }
}
