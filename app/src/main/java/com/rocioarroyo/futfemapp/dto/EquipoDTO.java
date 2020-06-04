package com.rocioarroyo.futfemapp.dto;

import android.os.Build;
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

    public EquipoDTO() {
    }

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

    public String getEquId() {
        return equId;
    }

    public void setEquId(String equId) {
        this.equId = equId;
    }

    public String getEquNombre() {
        return equNombre;
    }

    public void setEquNombre(String equNombre) {
        this.equNombre = equNombre;
    }

    public int getPosicion() {return posicion;}

    public void setPosicion(int posicion) {this.posicion = posicion;}

    public int getEquPuntos() {
        return equPuntos;
    }

    public void setEquPuntos(int equPuntos) {
        this.equPuntos = equPuntos;
    }

    public int getEquParGanado() {
        return equParGanado;
    }

    public void setEquParGanado(int equParGanado) {
        this.equParGanado = equParGanado;
    }

    public int getEquParEmpatados() {
        return equParEmpatados;
    }

    public void setEquParEmpatados(int equParEmpatados) {
        this.equParEmpatados = equParEmpatados;
    }

    public int getEquParPerdidos() {
        return equParPerdidos;
    }

    public void setEquParPerdidos(int equParPerdidos) {
        this.equParPerdidos = equParPerdidos;
    }

    public int getEquGolesFavor() {
        return equGolesFavor;
    }

    public void setEquGolesFavor(int equGolesFavor) {
        this.equGolesFavor = equGolesFavor;
    }

    public int getEquGolesContra() {
        return equGolesContra;
    }

    public void setEquGolesContra(int equGolesContra) {
        this.equGolesContra = equGolesContra;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public ArrayList<EquipoDTO> getListaFavoritos() {
        return listaFavoritos;
    }

    public void setListaFavoritos(ArrayList<EquipoDTO> listaFavoritos) {
        this.listaFavoritos = listaFavoritos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

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
