package com.rocioarroyo.futfemapp.dao;

import android.content.Context;
import android.util.Log;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dialogos.CargaDialog;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.excepcion.FutFemAppException;

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
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class EquipoDAO {

    private final String TAG = EquipoDAO.class.getName();
    private Context context;
    private ArrayList<EquipoDTO> listaEquipos;
    private String user_name;

    /**
     * Constructor vacio
     */
    public EquipoDAO() {}

    /**
     * Constructor
     * @param context
     * @param user_name
     */
    public EquipoDAO(Context context, String user_name) {
        this.context=context;
        this.user_name = user_name;
    }

    /**
     * Método de validación tras la recuperación de los datos de la clasificación de la liga.
     * Este método valida que los datos se han recuperado correctamente para poder recueprar los partidos
     * @param s
     * @param ccd
     * @throws FutFemAppException
     */
    public void validarClasificacion(ArrayList<EquipoDTO> s, CargaDialog ccd) throws FutFemAppException {
        listaEquipos = s;
        if (s!=null) {
            Log.i(TAG, "validarClasificacion: Se ha encontrado la lista de resultados: " + s.size());
            BackgroundWorker backgroundWorker = new BackgroundWorker(context, s, user_name);
            backgroundWorker.execute(context.getString(R.string.type_jornada));
            Log.i(TAG, "validarClasificacion: ejecutamos consulta para recuperar todos los partidos");
        } else {
            ccd.dismiss();
            throw new FutFemAppException("NO SE HA PODIDO CONECTAR CON EL SERVIDOR");
        }
    }

    /**
     * Método de validación tras la recuepración de los datos de favoritos del usuario
     * Este método validad que los datos madnado y recuperados de la tabla de favoritos se ha recueprado correctamente
     * @param s
     * @throws FutFemAppException
     */
    public void validarFavorito(ArrayList<String> s) throws FutFemAppException {
        if (s!=null) {
            String resultado = s.get(0);
            if (resultado.equalsIgnoreCase(context.getString(R.string.fav_del_fail))) {
                Log.e(TAG, "validarFavorito: NO SE HA PODIDO ELIMINAR DE LA TABLA", new Exception());
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.fav_ins_fail))) {
                Log.e(TAG, "validarFavorito: NO SE HAN PODIDO INSERTAR EN LA TABLA", new Exception());
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.fav_del_ok))) {
                //reordenarLista(listaEquipos);
                Log.i(TAG, "validarFavorito: SE HA ELIMINADO CORRECTAMENTE");
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.fav_ins_ok))) {
                //reordenarLista(listaEquipos);
                Log.i(TAG, "validarFavorito: SE HA INSERTADO CORRECTAMENTE");
            } else {
                throw new FutFemAppException("NO SE HA PODIDO CONECTAR CON EL SERVIDOR");
            }
        }
    }

    /**
     * Método para reordenar la lista de equipos de tal forma que se muestran favoritos primero,
     * de esta forma el usuario podrá ver la informacion de cada equipo mostrandole primero su lista de favoritos
     * @param listaEquipos
     * @return
     */
    public ArrayList<EquipoDTO> reordenarLista(ArrayList<EquipoDTO> listaEquipos) {
        ArrayList<EquipoDTO> listaOrdenada = new ArrayList<>();
        ArrayList<EquipoDTO> listaFav = new ArrayList<>();
        ArrayList<EquipoDTO> listaNoFav = new ArrayList<>();
        for (EquipoDTO equipoDTO: listaEquipos) {
            if (equipoDTO.getEquNombre()!=null && equipoDTO.getFav()==1) {
                listaFav.add(equipoDTO);
            } else if (equipoDTO.getEquNombre()!=null && equipoDTO.getFav()==0) {
                listaNoFav.add(equipoDTO);
            }
        }
        if (!listaFav.isEmpty()) {
            listaOrdenada.addAll(listaFav);
        }
        if (!listaNoFav.isEmpty()) {
            listaOrdenada.addAll(listaNoFav);
        }

        return listaOrdenada;
    }

    /**
     * Método de recuperación de datos de la clasificación
     * Método que recupera todos los datos de los equipos para mostrar en la tabla clasificatoria
     * Devuelve una lista de equipos
     * @param type
     * @param login_url
     * @return
     * @throws FutFemAppException
     * @throws JSONException
     * @throws IOException
     */
    public ArrayList<EquipoDTO> recibirClasificacion(String type, String login_url) throws FutFemAppException, JSONException, IOException {
        URL url = null;
        if (type.equalsIgnoreCase(context.getString(R.string.type_clasificacion))) {
            Log.i(TAG, "recibirClasificacion: OBTENEMOS LA CONTRASEÑA");
            url = new URL(login_url + "/clasificacion.php");
        } else {
            throw new FutFemAppException("NO SE HA PODIDO ENCONTRAR LA DIRECCION URL PARA ESTE TIPO DE LLAMADA: "+ type);
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        Log.i(TAG, "recibirClasificacion: abrimos la conexion con la base de datos");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        Log.i(TAG, "recibirClasificacion: mandamos mensaje a la base de datos");
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
        Log.i(TAG, "recibirClasificacion: codificamos el mensaje a mandar");
        bufferedWriter.write(post_data);
        Log.i(TAG, "recibirClasificacion: mandamos el mensaje: " + post_data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = httpURLConnection.getInputStream();
        Log.i(TAG, "recibirClasificacion: recibimos los datos de la base de datos");
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
        Log.i(TAG, "recibirClasificacion: empezamos a leer los datos");
        String result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        Log.i(TAG, "recibirClasificacion: terminamos de leer los datos: " + result);
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        Log.i(TAG, "recibirClasificacion: cerramos conexion con la base de datos");
        Log.i(TAG, "recibirClasificacion: DATOS OBTENIDOS CORRECTAMENTE");
        Log.i(TAG, "recibirClasificacion: leemos JSON");
        JSONArray jsonArray = null;
        if (!result.equalsIgnoreCase(context.getString(R.string.clasificacion_fail))) {
            jsonArray = new JSONArray(result);
            ArrayList<EquipoDTO> listaEquipos = new ArrayList<>();
            EquipoDTO equipoDTO = new EquipoDTO();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                equipoDTO = new EquipoDTO();
                equipoDTO.setEquId(jsonObject.getString("equ_id"));
                equipoDTO.setEquNombre(jsonObject.getString("equ_nombre"));
                equipoDTO.setPosicion(i+1);
                equipoDTO.setEquPuntos(jsonObject.getInt("equ_puntos"));
                equipoDTO.setEquParGanado(jsonObject.getInt("equ_par_ganados"));
                equipoDTO.setEquParEmpatados(jsonObject.getInt("equ_par_empatados"));
                equipoDTO.setEquParPerdidos(jsonObject.getInt("equ_par_perdidos"));
                equipoDTO.setEquGolesFavor(jsonObject.getInt("equ_goles_favor"));
                equipoDTO.setEquGolesContra(jsonObject.getInt("equ_goles_contra"));
                equipoDTO.setFav(jsonObject.getInt("fav"));
                listaEquipos.add(equipoDTO);
            }
            Log.i(TAG, "recibirClasificacion: Datos recuperados correctamente");
            Log.i(TAG, "recibirClasificacion: SALIDA");
            return listaEquipos;
        } else {
            Log.i(TAG, "recibirClasificacion: SALIAD");
            throw new FutFemAppException("NO SE HA PODIDO CONECTAR CON EL SERVIDOR");
        }
    }

    /**
     * Método de recuperación de datos de la tabla de favoritos
     * Método que manda datos al servidor para guardar o eliminar los favoritos de unn usuario
     * Devuelve un mensaje validado despues que informa de la correcta o incorrecta llamada al servidor
     * @param type
     * @param equ_id
     * @param login_url
     * @return
     * @throws IOException
     * @throws FutFemAppException
     */
    public ArrayList<String> controlFavoritos(String type, String equ_id, String login_url) throws IOException, FutFemAppException {
        URL url = null;
        if (type.equalsIgnoreCase(context.getString(R.string.type_fav))) {
            Log.i(TAG, "controlFavoritos: OBTENEMOS DATOS DE FAVORITOS");
            url = new URL(login_url + "/favoritos.php");
        } else {
            throw new FutFemAppException("NO SE HA RECONOCIDO LA DIRRECCION LLAMADA");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        Log.i(TAG, "controlFavoritos: abrimos la conexion con la base de datos");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        Log.i(TAG, "controlFavoritos: mandamos mensaje a la base de datos");
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8")
                + "&" + URLEncoder.encode("equ_id", "UTF-8") + "=" + URLEncoder.encode(equ_id, "UTF-8");
        Log.i(TAG, "controlFavoritos: codificamos el mensaje a mandar");
        bufferedWriter.write(post_data);
        Log.i(TAG, "controlFavoritos: mandamos el mensaje: " + post_data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = httpURLConnection.getInputStream();
        Log.i(TAG, "controlFavoritos: recibimos los datos de la base de datos");
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
        Log.i(TAG, "controlFavoritos: empezamos a leer los datos");
        String result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        Log.i(TAG, "controlFavoritos: terminamos de leer los datos: " + result);
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        Log.i(TAG, "controlFavoritos: cerramos conexion con la base de datos");
        Log.i(TAG, "controlFavoritos: DATOS OBTENIDOS CORRECTAMENTE");
        Log.i(TAG, "controlFavoritos: SALIDA");
        ArrayList<String> resultados = new ArrayList<>();
        resultados.add(result);
        return resultados;
    }

}
