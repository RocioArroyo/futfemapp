package com.rocioarroyo.futfemapp.dao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.dto.UsuarioDTO;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private final String TAG = EquipoDAO.class.getName();
    private Context context;

    public EquipoDAO() {}

    public EquipoDAO(Context context) {
        this.context=context;
    }

    public void validarClasificacion(String s) {
        Log.i(TAG, "validarPassword: leemos JSON");
        JSONArray jsonArray = null;
        try {
            if (!s.equalsIgnoreCase(context.getString(R.string.clasificacion_fail))) {
                jsonArray = new JSONArray(s);
                List<EquipoDTO> listaEquipos = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    EquipoDTO equipoDTO = new EquipoDTO();
                    equipoDTO.setEquId(jsonObject.getString("equ_id"));
                    equipoDTO.setEquNombre(jsonObject.getString("equ_nombre"));
                    equipoDTO.setEquPosicion(jsonObject.getInt("equ_posicion"));
                    equipoDTO.setEquPuntos(jsonObject.getInt("equ_puntos"));
                    equipoDTO.setEquParGanado(jsonObject.getInt("equ_par_ganados"));
                    equipoDTO.setEquParEmpatados(jsonObject.getInt("equ_par_empatado"));
                    equipoDTO.setEquParPerdidos(jsonObject.getInt("equ_par_perdidos"));
                    equipoDTO.setEquGolesFavor(jsonObject.getInt("equ_goles_favor"));
                    equipoDTO.setEquGolesContra(jsonObject.getInt("equ_goles_contra"));
                    listaEquipos.add(equipoDTO);
                }
                Intent intent = new Intent(context, PrincipalActivity.class);
                intent.putParcelableArrayListExtra("listaEquipos", (ArrayList<? extends Parcelable>) listaEquipos);
                context.startActivity(intent);

            } else {
                Log.i(TAG, "validarEquipo: ERROR");
            }
        } catch (JSONException e) {
            Log.e(TAG, "validarPassword: Error al validar la recuperacion de a clasifiacion", e);
        }
    }

}
