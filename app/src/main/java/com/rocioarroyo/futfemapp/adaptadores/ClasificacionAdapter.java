package com.rocioarroyo.futfemapp.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

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
        VistaTag vistaTag;
        if (view == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.activity_item_clasificacion, null);
            vistaTag = new VistaTag();
            vistaTag.posicion = view.findViewById(R.id.tvPosicionE);
            vistaTag.icono = view.findViewById(R.id.idIconoEquipo);
            vistaTag.nombre = view.findViewById(R.id.tvNombreE);
            vistaTag.puntos = view.findViewById(R.id.tvPuntosE);
            vistaTag.partidosJugados = view.findViewById(R.id.tvPartidosJugadosE);
            vistaTag.partidosGanados = view.findViewById(R.id.tvPartidosGanadosE);
            vistaTag.partidosEmpatados = view.findViewById(R.id.tvPartidosEmpatadosE);
            vistaTag.partidoPerdidos = view.findViewById(R.id.tvPartidosPerdidosE);
            vistaTag.golesFavor = view.findViewById(R.id.tvGolesFavorE);
            vistaTag.golesContra = view.findViewById(R.id.tvGolesContraE);
            vistaTag.golesDiferencia = view.findViewById(R.id.tvGolesDiferenciaE);
            view.setTag(vistaTag);
        } else {
            vistaTag = (VistaTag) view.getTag();
        }
        int posicionClasificacion = posicion+1;
        vistaTag.posicion.setText(Integer.toString(posicionClasificacion));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vistaTag.icono.setImageDrawable(ponerIconoAdecuado(posicion));
        } else {
            vistaTag.icono.setImageResource(R.mipmap.ic_logos_foreground);
        }
        vistaTag.nombre.setText(datos.get(posicion).getEquNombre().split("-")[1]);
        vistaTag.puntos.setText(Integer.toString(datos.get(posicion).getEquPuntos()));
        vistaTag.partidosJugados.setText(Integer.toString(partidosJugados(datos.get(posicion).getEquParGanado(), datos.get(posicion).getEquParEmpatados(), datos.get(posicion).getEquParPerdidos())));
        vistaTag.partidosGanados.setText(Integer.toString(datos.get(posicion).getEquParGanado()));
        vistaTag.partidosEmpatados.setText(Integer.toString(datos.get(posicion).getEquParEmpatados()));
        vistaTag.partidoPerdidos.setText(Integer.toString(datos.get(posicion).getEquParPerdidos()));
        vistaTag.golesFavor.setText(Integer.toString(datos.get(posicion).getEquGolesFavor()));
        vistaTag.golesContra.setText(Integer.toString(datos.get(posicion).getEquGolesContra()));
        vistaTag.golesDiferencia.setText(Integer.toString(golesDiferencia(datos.get(posicion).getEquGolesFavor(), datos.get(posicion).getEquGolesContra())));
        return (view);
    }

    private int golesDiferencia(int golesFavor, int golesContra) {
        return golesFavor-golesContra;
    }

    private int partidosJugados(int partidosGnados, int partidosEmpatados, int partidosPerdidos) {
        return partidosGnados+partidosEmpatados+partidosPerdidos;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Drawable ponerIconoAdecuado(int posicion) {
        if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.barcelona))) {
            return getContext().getDrawable(R.drawable.ic_barcelona);
        } else {
            return getContext().getDrawable(R.drawable.ic_barcelona);
        }
    }

}

class VistaTag {
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
}
