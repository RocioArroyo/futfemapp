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
import com.rocioarroyo.futfemapp.dao.EquipoDAO;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;

import java.util.ArrayList;

public class EquiposAdapter extends ArrayAdapter {

    Activity activity;
    private ArrayList<EquipoDTO> datos;
    private String user_name;

    /**
     * Constructor con las constantes de la apliacion y la lista de equipos a mostrar
     * @param context
     * @param datos
     * @param user_name
     */
    public EquiposAdapter(Context context, ArrayList<EquipoDTO> datos, String user_name) {
        super(context, R.layout.activity_item_equipos, datos);
        this.activity = (Activity) context;
        this.user_name=user_name;
        this.datos = datos;
    }

    /**
     * Método que se utiliza para llamar y mostrar la lista en cuestion
     * @param posicion
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        View view = convertView;
        VistaTagEquipos vistaTagEquipos;
        if (view == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.activity_item_equipos, null);
            vistaTagEquipos = new VistaTagEquipos();
            vistaTagEquipos.icono = view.findViewById(R.id.ivIconoClub);
            vistaTagEquipos.nombre = view.findViewById(R.id.idNombreEquipos);
            vistaTagEquipos.posicion = view.findViewById(R.id.idPosicionEquipos);
            vistaTagEquipos.puntos = view.findViewById(R.id.idPuntosEquipos);
            vistaTagEquipos.fav = view.findViewById(R.id.btnFav);
            view.setTag(vistaTagEquipos);
        } else {
            vistaTagEquipos = (VistaTagEquipos) view.getTag();
        }
        vistaTagEquipos.icono.setImageResource(ponerIconoAdecuadoMipmap(posicion));
        vistaTagEquipos.nombre.setText(datos.get(posicion).getEquNombre().split("-")[0]);
        vistaTagEquipos.posicion.setText(activity.getString(R.string.posicion) + " " + datos.get(posicion).getPosicion());
        vistaTagEquipos.puntos.setText(activity.getString(R.string.puntos) + " " + datos.get(posicion).getEquPuntos());
        vistaTagEquipos.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (datos.get(posicion).getFav()==1) {
                        datos.get(posicion).setFav(0);
                        vistaTagEquipos.fav.setBackground(activity.getDrawable(R.drawable.ic_nofav_foreground));
                    } else {
                        datos.get(posicion).setFav(1);
                        vistaTagEquipos.fav.setBackground(activity.getDrawable(R.drawable.ic_fav_foreground));
                    }
                }
                new EquipoDAO().reordenarLista(datos);
                favoritos(posicion);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vistaTagEquipos.fav.setBackground(ponerIconoFavDrawable(posicion));
        } else {
            vistaTagEquipos.fav.setImageResource(ponerIconoFavMipMap(posicion));
        }
        return (view);
    }

    /**
     * Método que muestra el icono de favorritos a mostrar en la vista
     * SOLO PARA ANDROID CON API MAYOR A LOLLIPOP
     * @param posicion
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable ponerIconoFavDrawable (int posicion) {
        if (datos.get(posicion).getFav()==1) {
            return activity.getDrawable(R.drawable.ic_fav_foreground);
        } else {
            return activity.getDrawable(R.drawable.ic_nofav_foreground);
        }
    }

    /**
     * Método que muestra el icono de favoritos en la vista
     * SOLO PARA APIS MENOSRES DE LOLLIPOP
     * @param posicion
     * @return
     */
    private int ponerIconoFavMipMap (int posicion) {
        if (datos.get(posicion).getFav()==1) {
            return R.drawable.ic_fav_foreground;
        } else {
            return R.drawable.ic_nofav_foreground;
        }
    }

    /**
     * Método que saca el icono que ha de aparecera para cada lista o equipo
     * @param posicion
     * @return
     */
    private int ponerIconoAdecuadoMipmap(int posicion) {
        if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.barcelona))) {
            return R.mipmap.ic_barcelona128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.at_madrid))) {
            return R.mipmap.ic_atmadrid128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.at_bilbao))) {
            return R.mipmap.ic_bilbao128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.betis))) {
            return R.mipmap.ic_betis128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.deportivo))) {
            return R.mipmap.ic_depor128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.espanyol))) {
            return R.mipmap.ic_espanyol128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.levante))) {
            return R.mipmap.ic_levante128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.logrono))) {
            return R.mipmap.ic_logrono128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.madrid))) {
            return R.mipmap.ic_madrid128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.rayo))) {
            return R.mipmap.ic_rayo128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.sevilla))) {
            return R.mipmap.ic_sevilla128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.sociedad))) {
            return R.mipmap.ic_sociedad128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.sporting))) {
            return R.mipmap.ic_sporting128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.tacones))) {
            return R.mipmap.ic_tacon128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.tenerife))) {
            return R.mipmap.ic_tenerife128_foreground;
        } else if (datos.get(posicion).getEquNombre().contains(activity.getString(R.string.valencia))) {
            return R.mipmap.ic_valencia128_foreground;
        } else {
            return R.mipmap.ic_equipos128_foreground;
        }
    }

    /**
     * Método que llama al sevidor para informar de un nuevo favorito o eliminacion de un favorito
     * @param posicion
     */
    private void favoritos(int posicion) {
        BackgroundWorker backgroundWorker = new BackgroundWorker(activity, user_name);
        backgroundWorker.execute(activity.getString(R.string.type_fav), datos.get(posicion).getEquId());
    }
}

/**
 * Class de los campos a mostrar en la vista
 */
    class VistaTagEquipos {
        ImageView icono;
        TextView nombre;
        TextView posicion;
        TextView puntos;
        ImageButton fav;
    }
