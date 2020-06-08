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

    private Activity activity;
    private ArrayList<EquipoDTO> datos;
    private String user_name;

    /**
     * Constructor del adaptador con los datos de la apliacaion y la lista a mostrar
     * @param context
     * @param datos
     * @param user_name
     */
    public ClasificacionAdapter(Context context, ArrayList<EquipoDTO> datos, String user_name) {
        super(context, R.layout.activity_item_clasificacion, datos);
        this.activity = (Activity) context;
        this.datos = datos;
        this.user_name = user_name;
    }

    /**
     * Método para llamar a la vista a crear
     * @param posicion
     * @param convertView
     * @param parent
     * @return
     */
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
        vistaTagClasificacion.posicion.setText(Integer.toString(datos.get(posicion).getPosicion()));
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
        return (view);
    }

    /**
     * Método que calcula la diferencia de goles
     * @param golesFavor
     * @param golesContra
     * @return
     */
    private int golesDiferencia(int golesFavor, int golesContra) {
        return golesFavor-golesContra;
    }

    /**
     * Método que clacula el total de partidos ya jugados
     * @param partidosGnados
     * @param partidosEmpatados
     * @param partidosPerdidos
     * @return
     */
    private int partidosJugados(int partidosGnados, int partidosEmpatados, int partidosPerdidos) {
        return partidosGnados+partidosEmpatados+partidosPerdidos;
    }

    /**
     * Método que saca qué icono sacar en cada fila de la lista a mostrar
     * @param posicion
     * @return
     */
    private int ponerIconoAdecuadoMipmap(int posicion) {
        if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.barcelona))) {
            return R.mipmap.ic_barcelona128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.at_madrid))) {
            return R.mipmap.ic_atmadrid128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.at_bilbao))) {
            return R.mipmap.ic_bilbao128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.betis))) {
            return R.mipmap.ic_betis128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.deportivo))) {
            return R.mipmap.ic_depor128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.espanyol))) {
            return R.mipmap.ic_espanyol128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.levante))) {
            return R.mipmap.ic_levante128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.logrono))) {
            return R.mipmap.ic_logrono128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.madrid))) {
            return R.mipmap.ic_madrid128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.rayo))) {
            return R.mipmap.ic_rayo128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.sevilla))) {
            return R.mipmap.ic_sevilla128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.sociedad))) {
            return R.mipmap.ic_sociedad128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.sporting))) {
            return R.mipmap.ic_sporting128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.tacones))) {
            return R.mipmap.ic_tacon128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.tenerife))) {
            return R.mipmap.ic_tenerife128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.separador) + activity.getString(R.string.valencia))) {
            return R.mipmap.ic_valencia128_foreground;
        } else {
            return R.mipmap.ic_equipos128_foreground;
        }
    }

}

/**
 * Class de todos los campos a mostrar en la vista
 */
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
