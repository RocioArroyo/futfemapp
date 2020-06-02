package com.rocioarroyo.futfemapp.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;

import java.util.ArrayList;

public class ClasificacionAdapter extends ArrayAdapter {

    Activity activity;
    private ArrayList<EquipoDTO> datos;

    public ClasificacionAdapter(Context context, ArrayList<EquipoDTO> datos) {
        super(context, R.layout.activity_item_clasificacion, datos);
        this.activity = (Activity) context;
        this.datos = datos;
    }

    public View getView(final int posicion, View convertView, ViewGroup parent) {
        View view = convertView;
        VistaTagClasificacion vistaTagClasificacion;
        if (view == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.activity_item_clasificacion, null);
            vistaTagClasificacion = new VistaTagClasificacion();
            vistaTagClasificacion.posicion = view.findViewById(R.id.tvPosicionE);
            vistaTagClasificacion.icono = view.findViewById(R.id.idIconoEquipo);
            vistaTagClasificacion.nombre = view.findViewById(R.id.tvNombreE);
            vistaTagClasificacion.puntos = view.findViewById(R.id.tvPuntosE);
            vistaTagClasificacion.partidosJugados = view.findViewById(R.id.tvPartidosJugadosE);
            vistaTagClasificacion.partidosGanados = view.findViewById(R.id.tvPartidosGanadosE);
            vistaTagClasificacion.partidosEmpatados = view.findViewById(R.id.tvPartidosEmpatadosE);
            vistaTagClasificacion.partidoPerdidos = view.findViewById(R.id.tvPartidosPerdidosE);
            vistaTagClasificacion.golesFavor = view.findViewById(R.id.tvGolesFavorE);
            vistaTagClasificacion.golesContra = view.findViewById(R.id.tvGolesContraE);
            vistaTagClasificacion.golesDiferencia = view.findViewById(R.id.tvGolesDiferenciaE);
            vistaTagClasificacion.listado = view.findViewById(R.id.llhEquipo1);
            view.setTag(vistaTagClasificacion);
        } else {
            vistaTagClasificacion = (VistaTagClasificacion) view.getTag();
        }
        if (posicion == 0) {
            vistaTagClasificacion.posicion.setText("  ");
            vistaTagClasificacion.icono.setImageResource(ponerIconoAdecuadoMipmap(posicion));
            vistaTagClasificacion.icono.setVisibility(ImageView.INVISIBLE);
            vistaTagClasificacion.nombre.setText(getContext().getString(R.string.nombre));
            vistaTagClasificacion.puntos.setText(getContext().getString(R.string.puntos));
            vistaTagClasificacion.partidosJugados.setText(getContext().getString(R.string.partidos_jugados));
            vistaTagClasificacion.partidosGanados.setText(getContext().getString(R.string.partidos_ganados));
            vistaTagClasificacion.partidosEmpatados.setText(getContext().getString(R.string.partidos_empatados));
            vistaTagClasificacion.partidoPerdidos.setText(getContext().getString(R.string.partidos_perdidos));
            vistaTagClasificacion.golesFavor.setText(getContext().getString(R.string.goles_favor));
            vistaTagClasificacion.golesContra.setText(getContext().getString(R.string.goles_contra));
            vistaTagClasificacion.golesDiferencia.setText(getContext().getString(R.string.goles_diferencia));
            darColorCabecera(posicion, vistaTagClasificacion);
        } else {
            vistaTagClasificacion.posicion.setText(Integer.toString(posicion));
            vistaTagClasificacion.icono.setImageResource(ponerIconoAdecuadoMipmap(posicion));
            vistaTagClasificacion.nombre.setText(datos.get(posicion).getEquNombre().split("-")[1]);
            vistaTagClasificacion.puntos.setText(Integer.toString(datos.get(posicion).getEquPuntos()));
            vistaTagClasificacion.partidosJugados.setText(Integer.toString(partidosJugados(datos.get(posicion).getEquParGanado(), datos.get(posicion).getEquParEmpatados(), datos.get(posicion).getEquParPerdidos())));
            vistaTagClasificacion.partidosGanados.setText(Integer.toString(datos.get(posicion).getEquParGanado()));
            vistaTagClasificacion.partidosEmpatados.setText(Integer.toString(datos.get(posicion).getEquParEmpatados()));
            vistaTagClasificacion.partidoPerdidos.setText(Integer.toString(datos.get(posicion).getEquParPerdidos()));
            vistaTagClasificacion.golesFavor.setText(Integer.toString(datos.get(posicion).getEquGolesFavor()));
            vistaTagClasificacion.golesContra.setText(Integer.toString(datos.get(posicion).getEquGolesContra()));
            vistaTagClasificacion.golesDiferencia.setText(Integer.toString(golesDiferencia(datos.get(posicion).getEquGolesFavor(), datos.get(posicion).getEquGolesContra())));
        }
        return (view);
    }

    private int golesDiferencia(int golesFavor, int golesContra) {
        return golesFavor-golesContra;
    }

    private int partidosJugados(int partidosGnados, int partidosEmpatados, int partidosPerdidos) {
        return partidosGnados+partidosEmpatados+partidosPerdidos;
    }

    private void darColorCabecera(int posicion, VistaTagClasificacion vistaTagClasificacion) {
        if (posicion==0) {
            vistaTagClasificacion.listado.setBackgroundColor(Color.parseColor("#F9F7B2"));
        }
    }

    private int ponerIconoAdecuadoMipmap(int posicion) {
        if(posicion==0){
            return R.mipmap.ic_equipos128_foreground;
        }else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.barcelona))) {
            return R.mipmap.ic_barcelona128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.at_madrid))) {
            return R.mipmap.ic_atmadrid128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.at_bilbao))) {
            return R.mipmap.ic_bilbao128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.betis))) {
            return R.mipmap.ic_betis128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.deportivo))) {
            return R.mipmap.ic_depor128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.espanyol))) {
            return R.mipmap.ic_espanyol128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.levante))) {
            return R.mipmap.ic_levante128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.logrono))) {
            return R.mipmap.ic_logrono128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.madrid))) {
            return R.mipmap.ic_madrid128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.rayo))) {
            return R.mipmap.ic_rayo128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.sevilla))) {
            return R.mipmap.ic_sevilla128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.sociedad))) {
            return R.mipmap.ic_sociedad128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.sporting))) {
            return R.mipmap.ic_sporting128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.tacones))) {
            return R.mipmap.ic_tacon128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.tenerife))) {
            return R.mipmap.ic_tenerife128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.valencia))) {
            return R.mipmap.ic_valencia128_foreground;
        } else {
            return R.mipmap.ic_equipos128_foreground;
        }
    }

}

    class VistaTagClasificacion {
        TextView posicion;
        ImageView icono;
        TextView nombre;
        TextView puntos;
        TextView partidosJugados;
        TextView partidosGanados;
        TextView partidosEmpatados;
        TextView partidoPerdidos;
        TextView golesFavor;
        TextView golesContra;
        TextView golesDiferencia;
        LinearLayout listado;
    }
