package com.rocioarroyo.futfemapp.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class EquipoDTO implements Parcelable {

    private String equId;
    private String equNombre;
    private int posicion;
    private int equPuntos;
    private int equParGanado;
    private int equParEmpatados;
    private int equParPerdidos;
    private int equGolesFavor;
    private int equGolesContra;
    private int fav;
    private ArrayList<EquipoDTO> listaFavoritos;

    /**
     * Constructor vaccio
     */
    public EquipoDTO() {
    }

    /**
     * Contructor con todos loc campos de la clase
     * @param equId
     * @param equNombre
     * @param posicion
     * @param equPuntos
     * @param equParGanado
     * @param equParEmpatados
     * @param equParPerdidos
     * @param equGolesFavor
     * @param equGolesContra
     * @param fav
     */
    public EquipoDTO(String equId, String equNombre, int posicion, int equPuntos, int equParGanado, int equParEmpatados, int equParPerdidos, int equGolesFavor, int equGolesContra, int fav) {
        this.equId = equId;
        this.equNombre = equNombre;
        this.posicion = posicion;
        this.equPuntos = equPuntos;
        this.equParGanado = equParGanado;
        this.equParEmpatados = equParEmpatados;
        this.equParPerdidos = equParPerdidos;
        this.equGolesFavor = equGolesFavor;
        this.equGolesContra = equGolesContra;
        this.fav=fav;
    }

    /**
     * Método de lectura de la clase
     * @param in
     */
    private EquipoDTO(Parcel in) {
        equId = in.readString();
        equNombre = in.readString();
        posicion = in.readInt();
        equPuntos = in.readInt();
        equParGanado = in.readInt();
        equParEmpatados = in.readInt();
        equParPerdidos = in.readInt();
        equGolesFavor = in.readInt();
        equGolesContra = in.readInt();
        fav = in.readInt();
    }

    /**
     * Método de para hacer un Creator de la clase
     */
    public static final Creator<EquipoDTO> CREATOR = new Creator<EquipoDTO>() {
        @Override
        public EquipoDTO createFromParcel(Parcel in) {
            return new EquipoDTO(in);
        }

        @Override
        public EquipoDTO[] newArray(int size) {
            return new EquipoDTO[size];
        }
    };

    /**
     * getEquId
     * @return equId
     */
    public String getEquId() {
        return equId;
    }

    /**
     * setEquId
     * @param equId
     */
    public void setEquId(String equId) {
        this.equId = equId;
    }

    /**
     * getEquNombre
     * @return equNombre
     */
    public String getEquNombre() {
        return equNombre;
    }

    /**
     * setEquNombre
     * @param equNombre
     */
    public void setEquNombre(String equNombre) {
        this.equNombre = equNombre;
    }

    /**
     * getPosicion
     * @return posicion
     */
    public int getPosicion() {return posicion;}

    /**
     * setPosicion
     * @param posicion
     */
    public void setPosicion(int posicion) {this.posicion = posicion;}

    /**
     * getEquPuntos
     * @return equPuntos
     */
    public int getEquPuntos() {
        return equPuntos;
    }

    /**
     * setEquPuntos
     * @param equPuntos
     */
    public void setEquPuntos(int equPuntos) {
        this.equPuntos = equPuntos;
    }

    /**
     * getEquParGanado
     * @return equParGanado
     */
    public int getEquParGanado() {
        return equParGanado;
    }

    /**
     * setEquParGanado
     * @param equParGanado
     */
    public void setEquParGanado(int equParGanado) {
        this.equParGanado = equParGanado;
    }

    /**
     * getEquParEmpatados
     * @return equParEmpatados
     */
    public int getEquParEmpatados() {
        return equParEmpatados;
    }

    /**
     * setEquParEmpatados
     * @param equParEmpatados
     */
    public void setEquParEmpatados(int equParEmpatados) {
        this.equParEmpatados = equParEmpatados;
    }

    /**
     * getEquParPerdidos
     * @return equParPerdidos
     */
    public int getEquParPerdidos() {
        return equParPerdidos;
    }

    /**
     * setEquParPerdidos
     * @param equParPerdidos
     */
    public void setEquParPerdidos(int equParPerdidos) {
        this.equParPerdidos = equParPerdidos;
    }

    /**
     * getEquGolesFavor
     * @return equGolesFavor
     */
    public int getEquGolesFavor() {
        return equGolesFavor;
    }

    /**
     * setEquGolesFavor
     * @param equGolesFavor
     */
    public void setEquGolesFavor(int equGolesFavor) {
        this.equGolesFavor = equGolesFavor;
    }

    /**
     * getEquGolesContra
     * @return equGolesContra
     */
    public int getEquGolesContra() {
        return equGolesContra;
    }

    /**
     * setEquGolesContra
     * @param equGolesContra
     */
    public void setEquGolesContra(int equGolesContra) {
        this.equGolesContra = equGolesContra;
    }

    /**
     * getFav
     * @return fav
     */
    public int getFav() {
        return fav;
    }

    /**
     * setFav
     * @param fav
     */
    public void setFav(int fav) {
        this.fav = fav;
    }

    public ArrayList<EquipoDTO> getListaFavoritos() {
        return listaFavoritos;
    }

    /**
     * setListaFavoritos
     * @param listaFavoritos
     */
    public void setListaFavoritos(ArrayList<EquipoDTO> listaFavoritos) {
        this.listaFavoritos = listaFavoritos;
    }

    /**
     * Método para describir el contendio
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Método para escribir los campos de la calse
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(equId);
        dest.writeString(equNombre);
        dest.writeInt(posicion);
        dest.writeInt(equPuntos);
        dest.writeInt(equParGanado);
        dest.writeInt(equParEmpatados);
        dest.writeInt(equParPerdidos);
        dest.writeInt(equGolesFavor);
        dest.writeInt(equGolesContra);
        dest.writeInt(fav);
    }
}
