package com.rocioarroyo.futfemapp.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;

import java.util.ArrayList;

public class EquiposAdapter extends ArrayAdapter {

    Activity activity;
    private ArrayList<EquipoDTO> datos;

    public EquiposAdapter(Context context, ArrayList<EquipoDTO> datos) {
        super(context, R.layout.activity_item_equipos, datos);
        this.activity = (Activity) context;
        this.datos = datos;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        View view = convertView;
        VistaTagEquipos vistaTagEquipos;
        if (view == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.activity_item_equipos, null);
            vistaTagEquipos = new VistaTagEquipos();

            view.setTag(vistaTagEquipos);
        } else {
            vistaTagEquipos = (VistaTagEquipos) view.getTag();
            vistaTagEquipos.icono = view.findViewById(R.id.idIconoEquipo);
            vistaTagEquipos.nombre = view.findViewById(R.id.idNombreEquipos);
            vistaTagEquipos.posicion = view.findViewById(R.id.idPosicionEquipos);
            vistaTagEquipos.puntos = view.findViewById(R.id.idPuntosEquipos);
            vistaTagEquipos.fav = view.findViewById(R.id.btnFav);
        }
        vistaTagEquipos.icono.setImageResource(ponerIconoAdecuadoMipmap(posicion));
        vistaTagEquipos.nombre.setText(datos.get(posicion).getEquNombre().split("-")[0]);
        vistaTagEquipos.posicion.setText(Integer.toString(posicion+1));
        vistaTagEquipos.puntos.setText(Integer.toString(datos.get(posicion).getEquPuntos()));
        vistaTagEquipos.fav.setBackground(ponerIconoFav(posicion));
        return (view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable ponerIconoFav (int posicion) {
        if (datos.get(posicion).isFav()) {
            return getContext().getDrawable(R.drawable.ic_fav_foreground);
        } else {
            return getContext().getDrawable(R.drawable.ic_nofav_foreground);
        }
    }

    private int ponerIconoAdecuadoMipmap(int p) {
        int posicion = p+1;
         if (datos.get(posicion).getEquNombre().contains(getContext().getString(R.string.barcelona))) {
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
class VistaTagEquipos {
    ImageView icono;
    TextView nombre;
    TextView posicion;
    TextView puntos;
    ImageButton fav;
}
