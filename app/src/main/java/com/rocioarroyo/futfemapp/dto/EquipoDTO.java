package com.rocioarroyo.futfemapp.dto;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;


public class EquipoDTO implements Parcelable {

    private String equId;
    private String equNombre;
    private int equPuntos;
    private int equParGanado;
    private int equParEmpatados;
    private int equParPerdidos;
    private int equGolesFavor;
    private int equGolesContra;
    private boolean fav;

    public EquipoDTO() {
    }

    public EquipoDTO(String equId, String equNombre, int equPuntos, int equParGanado, int equParEmpatados, int equParPerdidos, int equGolesFavor, int equGolesContra, boolean fav) {
        this.equId = equId;
        this.equNombre = equNombre;
        this.equPuntos = equPuntos;
        this.equParGanado = equParGanado;
        this.equParEmpatados = equParEmpatados;
        this.equParPerdidos = equParPerdidos;
        this.equGolesFavor = equGolesFavor;
        this.equGolesContra = equGolesContra;
        this.fav=fav;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private EquipoDTO(Parcel in) {
        equId = in.readString();
        equNombre = in.readString();
        equPuntos = in.readInt();
        equParGanado = in.readInt();
        equParEmpatados = in.readInt();
        equParPerdidos = in.readInt();
        equGolesFavor = in.readInt();
        equGolesContra = in.readInt();
        fav = in.readBoolean();
    }

    public static final Creator<EquipoDTO> CREATOR = new Creator<EquipoDTO>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(equId);
        dest.writeString(equNombre);
        dest.writeInt(equPuntos);
        dest.writeInt(equParGanado);
        dest.writeInt(equParEmpatados);
        dest.writeInt(equParPerdidos);
        dest.writeInt(equGolesFavor);
        dest.writeInt(equGolesContra);
        dest.writeBoolean(fav);
    }
}
