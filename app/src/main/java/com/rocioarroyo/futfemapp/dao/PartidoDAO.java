package com.rocioarroyo.futfemapp.dao;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.dto.PartidoDTO;
import com.rocioarroyo.futfemapp.excepcion.FutFemAppException;
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
    private Context context;
    private String user_name;

    /**
     * Constructor vacio
     */
    public PartidoDAO(){}

    /**
     * Constructor con las constantes del proyecto
     * @param context
     * @param user_name
     */
    public PartidoDAO(Context context, String user_name) {
        this.context = context;
        this.user_name = user_name;
    }

    /**
     * Método de recuperación de todos los partidos de la liga
     * Método que llama al servidor donde la base de datos para recuperar todos los partidos
     * Devuelve la lista de todos los partidos, si no es asi se manda un excepcion por falta de conexión
     * @param type
     * @param login_url
     * @return
     * @throws FutFemAppException
     * @throws JSONException
     * @throws IOException
     */
    public ArrayList<PartidoDTO> recibirJornadas(String type, String login_url) throws FutFemAppException, JSONException, IOException {
        URL url = null;
        if (type.equalsIgnoreCase(context.getString(R.string.type_jornada))) {
            Log.i(TAG, "recibirJornadas: OBTENEMOS LA 1 JORNADA");
            url = new URL(login_url + "/partidos.php");
        } else {
            throw new FutFemAppException("NO SE HA PODIDO LEER LA URL");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        Log.i(TAG, "recibirJornadas: abrimos la conexion con la base de datos");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

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
            PartidoDTO partidoDTO=new PartidoDTO();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                partidoDTO = new PartidoDTO();
                partidoDTO.setParFechaHora(jsonObject.getString("par_fecha_hora"));
                if (jsonObject.getString("par_goles_local").equalsIgnoreCase("null")) {
                    partidoDTO.setParGolesLocal(99);
                } else {
                    partidoDTO.setParGolesLocal(jsonObject.getInt("par_goles_local"));
                }
                if (jsonObject.getString("par_goles_visitante").equalsIgnoreCase("null")) {
                    partidoDTO.setParGolesVisitante(99);
                } else {
                    partidoDTO.setParGolesVisitante(jsonObject.getInt("par_goles_visitante"));
                }
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
            throw new FutFemAppException("NO SE HA PODIDO CONECTAR CON EL SERVIDOR");
        }
    }

    /**
     * Método de validación de partidos
     * Método que valida que los partidos se han recuperado correctamente
     * @param listaPartidos
     * @param listaEquipos
     * @throws FutFemAppException
     */
    public void validarPartidos(ArrayList<PartidoDTO> listaPartidos, ArrayList<EquipoDTO> listaEquipos) throws FutFemAppException {
        if (listaPartidos!=null && !listaPartidos.isEmpty()) {
            Log.i(TAG, "validarPartidos: ENTRAMOS EN LA APLIACION");
            Intent intent = new Intent(context, PrincipalActivity.class);
            intent.putParcelableArrayListExtra("listaEquipos", listaEquipos);
            intent.putParcelableArrayListExtra("listaPartidos" , listaPartidos);
            intent.putExtra("user_name" , user_name);
            context.startActivity(intent);
        } else {
            throw new FutFemAppException("NO SE HAN ENCONTRADO CORRECTAMENTE TODOS LOS DATOS");
        }
    }
}
