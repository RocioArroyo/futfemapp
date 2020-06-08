package com.rocioarroyo.futfemapp.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dto.PartidoDTO;


import java.util.ArrayList;

public class JornadaAdapter extends ArrayAdapter {


    private Activity activity;
    private ArrayList<PartidoDTO> datos;
    private String user_name;

    /**
     * Constructor con las constantes de la apliacion y la lista de partidos a mostrar
     * @param context
     * @param datos
     * @param user_name
     */
    public JornadaAdapter(Context context, ArrayList<PartidoDTO> datos, String user_name) {
        super(context, R.layout.activity_item_jornada, datos);
        this.activity = (Activity) context;
        this.datos = datos;
        this.user_name = user_name;
    }

    /**
     * Método para llamar a la vista y generarla
     * @param posicion
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        View view = convertView;
        VistaTagJornada vistaTagJornada;
        if (view == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.activity_item_jornada, null);
            vistaTagJornada = new VistaTagJornada();
            vistaTagJornada.fecha = view.findViewById(R.id.tvFecha);
            vistaTagJornada.hora = view.findViewById(R.id.tvHora);
            vistaTagJornada.equLocal = view.findViewById(R.id.tvNombreLocal);
            vistaTagJornada.icoLocal = view.findViewById(R.id.ivLocal);
            vistaTagJornada.golLocal = view.findViewById(R.id.tvPuntosLocal);
            vistaTagJornada.separacion = view.findViewById(R.id.tvLineaSeparatoria);
            vistaTagJornada.equVisitante = view.findViewById(R.id.tvNombreVisitante);
            vistaTagJornada.icoVisitante = view.findViewById(R.id.ivVisitante);
            vistaTagJornada.golVisitante = view.findViewById(R.id.tvPuntosVisitante);
            view.setTag(vistaTagJornada);
        } else {
            vistaTagJornada = (VistaTagJornada) view.getTag();
        }
        vistaTagJornada.fecha.setText(datos.get(posicion).getParFechaHora().split(" ")[0]);
        vistaTagJornada.hora.setText(ponerHora(posicion));
        vistaTagJornada.equLocal.setText(datos.get(posicion).getParEquLocal().split("-")[1]);
        vistaTagJornada.icoLocal.setImageResource(ponerIconoAdecuadoMipmapLocal(posicion));
        vistaTagJornada.golLocal.setText(ponerGolesLocal(posicion));
        vistaTagJornada.separacion.setText(ponerSeparador(posicion));
        vistaTagJornada.equVisitante.setText(datos.get(posicion).getParEquVisitante().split("-")[1]);
        vistaTagJornada.icoVisitante.setImageResource(ponerIconoAdecuadoMipmapVisitante(posicion));
        vistaTagJornada.golVisitante.setText(ponerGolesVisitante(posicion));
        return (view);
    }

    /**
     * Método para poner la hora de cada partido
     * Separa la hora de la fehca completa
     * @param posicion
     * @return
     */
    private String ponerHora(int posicion) {
        if (datos.get(posicion).getParFechaHora().split(" ")[1].equalsIgnoreCase("00:00")) {
            return "--:--";
        } else {
            return datos.get(posicion).getParFechaHora().split(" ")[1];
        }
    }

    /**
     * Método para mostrar, o no, los goles del equipo local, segun se haya suspendido, o aun no se haya ejecutado
     * @param posicion
     * @return
     */
    private String ponerGolesLocal(int posicion) {
        if (datos.get(posicion).getParGolesLocal()==99) {
            return " ";
        } else {
            return Integer.toString(datos.get(posicion).getParGolesLocal());
        }
    }

    /**
     * Método para mostrar lel separados o la abreviatura de suspendido en el separador entre goles
     * @param posicion
     * @return
     */
    private String ponerSeparador(int posicion) {
        if (datos.get(posicion).getParFechaHora().split(" ")[1].equalsIgnoreCase("00:00")
                && datos.get(posicion).getParGolesLocal()==99
                && datos.get(posicion).getParGolesVisitante()==99) {
            return activity.getString(R.string.suspendido);
        } else {
            return activity.getString(R.string.separador);
        }
    }

    /**
     * Método para mostrar los goles del equipos visitante, o no si es un partido no ejecutado o suspendido
     * SOLO PARA VISITANTES
     * @param posicion
     * @return
     */
    private String ponerGolesVisitante(int posicion) {
        if (datos.get(posicion).getParGolesVisitante()==99) {
            return " ";
        } else {
            return Integer.toString(datos.get(posicion).getParGolesVisitante());
        }
    }

    /**
     * Método par amostrar el icono del equipo a mostrar en cada fila
     * SOLO PARA EQUIPOS LOCALES
     * @param posicion
     * @return
     */
    private int ponerIconoAdecuadoMipmapLocal(int posicion) {
        if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.barcelona))) {
            return R.mipmap.ic_barcelona128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.at_madrid))) {
            return R.mipmap.ic_atmadrid128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.at_bilbao))) {
            return R.mipmap.ic_bilbao128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.betis))) {
            return R.mipmap.ic_betis128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.deportivo))) {
            return R.mipmap.ic_depor128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.espanyol))) {
            return R.mipmap.ic_espanyol128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.levante))) {
            return R.mipmap.ic_levante128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.logrono))) {
            return R.mipmap.ic_logrono128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.madrid))) {
            return R.mipmap.ic_madrid128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.rayo))) {
            return R.mipmap.ic_rayo128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.sevilla))) {
            return R.mipmap.ic_sevilla128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.sociedad))) {
            return R.mipmap.ic_sociedad128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.sporting))) {
            return R.mipmap.ic_sporting128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.tacones))) {
            return R.mipmap.ic_tacon128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.tenerife))) {
            return R.mipmap.ic_tenerife128_foreground;
        } else if (datos.get(posicion).getParEquLocal().contains(activity.getString(R.string.valencia))) {
            return R.mipmap.ic_valencia128_foreground;
        } else {
            return R.mipmap.ic_equipos128_foreground;
        }
    }

    /**
     * Método para mosotrar el icono de cada equiop en la fila que corresponde
     * SOLO AR AEQUIPOS VISITANTES
     * @param posicion
     * @return
     */
    private int ponerIconoAdecuadoMipmapVisitante(int posicion) {
        if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.barcelona))) {
            return R.mipmap.ic_barcelona128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.at_madrid))) {
            return R.mipmap.ic_atmadrid128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.at_bilbao))) {
            return R.mipmap.ic_bilbao128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.betis))) {
            return R.mipmap.ic_betis128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.deportivo))) {
            return R.mipmap.ic_depor128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.espanyol))) {
            return R.mipmap.ic_espanyol128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.levante))) {
            return R.mipmap.ic_levante128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.logrono))) {
            return R.mipmap.ic_logrono128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.madrid))) {
            return R.mipmap.ic_madrid128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.rayo))) {
            return R.mipmap.ic_rayo128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.sevilla))) {
            return R.mipmap.ic_sevilla128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.sociedad))) {
            return R.mipmap.ic_sociedad128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.sporting))) {
            return R.mipmap.ic_sporting128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.tacones))) {
            return R.mipmap.ic_tacon128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.tenerife))) {
            return R.mipmap.ic_tenerife128_foreground;
        } else if (datos.get(posicion).getParEquVisitante().contains(activity.getString(R.string.valencia))) {
            return R.mipmap.ic_valencia128_foreground;
        } else {
            return R.mipmap.ic_equipos128_foreground;
        }
    }

}

/**
 * Class de los campos pa mostrar en la vista
 */
    class VistaTagJornada {
        TextView fecha;
        TextView hora;
        TextView equLocal;
        ImageView icoLocal;
        TextView golLocal;
        TextView separacion;
        TextView golVisitante;
        ImageView icoVisitante;
        TextView equVisitante;
    }
