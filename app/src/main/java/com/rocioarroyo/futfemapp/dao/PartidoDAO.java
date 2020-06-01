package com.rocioarroyo.futfemapp.dao;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.dto.PartidoDTO;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartidoDAO {

    private final String TAG = PartidoDAO.class.getName();
    Context context;

    public PartidoDAO(){}

    public PartidoDAO(Context context) {
        this.context = context;
    }

    public ArrayList<PartidoDTO> recibirJornadas(String type, String num_jornada, String login_url) {
        try {
            URL url = null;
            if (type.equalsIgnoreCase(context.getString(R.string.type_jornada))) {
                Log.i(TAG, "recibirJornadas: OBTENEMOS LA 1 JORNADA");
                url = new URL(login_url + "/partidos.php");
            }
            try {
                if (url != null) {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    Log.i(TAG, "recibirJornadas: abrimos la conexion con la base de datos");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    Log.i(TAG, "recibirJornadas: mandamos mensaje a la base de datos");
                    BufferedWriter bufferedWriter = null;
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("jornada", "UTF-8") + "=" + URLEncoder.encode(num_jornada, "UTF-8");
                    Log.i(TAG, "recibirJornadas: codificamos el mensaje a mandar");
                    bufferedWriter.write(post_data);
                    Log.i(TAG, "recibirJornadas: mandamos el mensaje: " + post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    Log.i(TAG, "recibirJornadas: recibimos los datos de la base de datos");
                    BufferedReader bufferedReader = null;
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
                    Log.i(TAG, "recibirJornadas: empezamos a leer los datos");
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    Log.i(TAG, "recibirJornadas: terminamos de leer los datos: " + result);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.i(TAG, "recibirJornadas: cerramos conexion con la base de datos");
                    Log.i(TAG, "recibirJornadas: DATOS OBTENIDOS CORRECTAMENTE");
                    Log.i(TAG, "validarPassword: leemos JSON");
                    JSONArray jsonArray = null;
                    if (!result.equalsIgnoreCase(context.getString(R.string.jornada_fail))) {
                        jsonArray = new JSONArray(result);
                        ArrayList<PartidoDTO> listaPartidos = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PartidoDTO partidoDTO = new PartidoDTO();
                            partidoDTO.setParFechaHora(jsonObject.getString("par_fecha_hora"));
                            partidoDTO.setParGolesLocal(jsonObject.getInt("par_goles_local"));
                            partidoDTO.setParGolesVisitante(jsonObject.getInt("par_goles_visitante"));
                            partidoDTO.setParJornada(jsonObject.getInt("par_jornada"));
                            partidoDTO.setParEquLocal(jsonObject.getString("equ_nombre_loc"));
                            partidoDTO.setParEquVisitante(jsonObject.getString("equ_nombre_vis"));
                            listaPartidos.add(partidoDTO);
                        }
                        Log.i(TAG, "recibirJornadas: SALIDA");
                        return listaPartidos;
                    } else {
                        Log.i(TAG, "validarEquipo: ERROR");
                        Log.i(TAG, "recibirJornadas: SALIDA");
                        return null;
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "recibirPassword: se ha producido un error con la conexion", e);
            } catch (JSONException e) {
                Log.e(TAG, "validarPassword: Error al validar la recuperacion de la clasifiacion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "recibirPassword: la URL esta incorrecta", e);
        }
        return null;
    }

    public void validarPartidos(ArrayList<PartidoDTO> listaPartidos, ArrayList<EquipoDTO> listaEquipos) {
        if (listaPartidos!=null && !listaPartidos.isEmpty()) {
            Intent intent = new Intent(context, PrincipalActivity.class);
            intent.putParcelableArrayListExtra("listaEquipos", listaEquipos);
            intent.putParcelableArrayListExtra("listaPartidos" , listaPartidos);
            context.startActivity(intent);
        }
    }
}
