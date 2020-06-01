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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private final String TAG = EquipoDAO.class.getName();
    private Context context;

    public EquipoDAO() {}

    public EquipoDAO(Context context) {
        this.context=context;
    }

    public void validarClasificacion(ArrayList<EquipoDTO> s) {
        if (s!=null) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(context, s);
            backgroundWorker.execute(context.getString(R.string.type_jornada), "1");
        }
    }

    public ArrayList<EquipoDTO> recibirClasificacion(String type, String login_url) {
        try {
            URL url = null;
            if (type.equalsIgnoreCase(context.getString(R.string.type_clasificacion))) {
                Log.i(TAG, "recibirEquipos: OBTENEMOS LA CONTRASEÑA");
                url = new URL(login_url + "/clasificacion.php");
            }
            try {
                if (url != null) {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    Log.i(TAG, "recibirEquipos: abrimos la conexion con la base de datos");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    InputStream inputStream = httpURLConnection.getInputStream();
                    Log.i(TAG, "recibirEquipos: recibimos los datos de la base de datos");
                    BufferedReader bufferedReader = null;
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
                    Log.i(TAG, "recibirEquipos: empezamos a leer los datos");
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    Log.i(TAG, "recibirEquipos: terminamos de leer los datos: " + result);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.i(TAG, "recibirEquipos: cerramos conexion con la base de datos");
                    Log.i(TAG, "recibirEquipos: DATOS OBTENIDOS CORRECTAMENTE");
                    Log.i(TAG, "validarPassword: leemos JSON");
                    JSONArray jsonArray = null;
                    if (!result.equalsIgnoreCase(context.getString(R.string.clasificacion_fail))) {
                        jsonArray = new JSONArray(result);
                        ArrayList<EquipoDTO> listaEquipos = new ArrayList<>();
                        EquipoDTO equipoDTO = new EquipoDTO();
                        listaEquipos.add(equipoDTO);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            equipoDTO = new EquipoDTO();
                            equipoDTO.setEquId(jsonObject.getString("equ_id"));
                            equipoDTO.setEquNombre(jsonObject.getString("equ_nombre"));
                            equipoDTO.setEquPuntos(jsonObject.getInt("equ_puntos"));
                            equipoDTO.setEquParGanado(jsonObject.getInt("equ_par_ganados"));
                            equipoDTO.setEquParEmpatados(jsonObject.getInt("equ_par_empatados"));
                            equipoDTO.setEquParPerdidos(jsonObject.getInt("equ_par_perdidos"));
                            equipoDTO.setEquGolesFavor(jsonObject.getInt("equ_goles_favor"));
                            equipoDTO.setEquGolesContra(jsonObject.getInt("equ_goles_contra"));
                            listaEquipos.add(equipoDTO);
                        }
                        return listaEquipos;
                    } else {
                        Log.i(TAG, "recibirEquipos: SALIDA");
                        Log.i(TAG, "validarEquipo: ERROR");
                        return null;
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "recibirEquipos: se ha producido un error con la conexion", e);
            } catch (JSONException e) {
                Log.e(TAG, "validarPassword: Error al validar la recuperacion de la clasifiacion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "recibirEquipos: la URL esta incorrecta", e);
        }
        return null;
    }

}
