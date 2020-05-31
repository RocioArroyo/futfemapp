package com.rocioarroyo.futfemapp.db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;


import com.rocioarroyo.futfemapp.dao.EquipoDAO;
import com.rocioarroyo.futfemapp.dao.UsuarioDAO;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;
import com.rocioarroyo.futfemapp.R;

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

public class BackgroundWorker extends AsyncTask <String, Integer, String> {

    private Context context;
    private final String TAG= BackgroundWorker.class.getName();
    private String tipo;
    private UsuarioDAO usuarioDAO;
    private EquipoDAO equipoDAO;

    public BackgroundWorker(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i(TAG, "doInBackground: ENTRADA");
        String type = strings[0];
        String login_url="http://192.168.1.149";
        if (type.equalsIgnoreCase(context.getString(R.string.type_login)) || type.equalsIgnoreCase(context.getString(R.string.type_registro))) {
            tipo = "loginregistro";
            String user_name = strings[1];
            String pass_word = strings[2];
            return mandarEmailPass(type, user_name, pass_word, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_pass))) {
            tipo = "recuperapass";
            String user_name = strings[1];
            return recibirPassword(type, user_name, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_clasificacion))) {
            tipo="clasificacion";
            return recibirClasificacion(type, login_url);
        }
        Log.i(TAG, "doInBackground: NO SE HAN PODIDO OBTENER LOS DATOS");
        Log.i(TAG, "doInBackground: SALIDA");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        usuarioDAO = new UsuarioDAO(context);
        equipoDAO = new EquipoDAO(context);
        Log.i(TAG, "onPostExecute: ENTRADA");
        if (tipo.equalsIgnoreCase("loginregistro")) {
            usuarioDAO.validarLoginRegistro(s);
        } else if (tipo.equalsIgnoreCase("recuperapass")) {
            usuarioDAO.validarPassword(s);
        } else if (tipo.equalsIgnoreCase("clasificacion")){
            equipoDAO.validarClasificacion(s);
        }
    }

    private String mandarEmailPass(String type, String user_name, String pass_word, String login_url) {
        try {
            URL url = null;
            if (type.equalsIgnoreCase(context.getString(R.string.type_login))) {
                Log.i(TAG, "mandarEmailPass: OBTENEMOS DATOS DEL LOGIN");
                url = new URL(login_url + "/login.php");
            } else if (type.equalsIgnoreCase(context.getString(R.string.type_registro))) {
                Log.i(TAG, "mandarEmailPass: OBTENEMOS DATOS DEL REGISTRO");
                url = new URL(login_url + "/registro.php");
            }
            try {
                if (url != null) {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    Log.i(TAG, "mandarEmailPass: abrimos la conexion con la base de datos");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    Log.i(TAG, "mandarEmailPass: mandamos mensaje a la base de datos");
                    BufferedWriter bufferedWriter = null;
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8")
                            + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass_word, "UTF-8");
                    Log.i(TAG, "mandarEmailPass: codificamos el mensaje a mandar");
                    bufferedWriter.write(post_data);
                    Log.i(TAG, "mandarEmailPass: mandamos el mensaje: " + post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    Log.i(TAG, "mandarEmailPass: recibimos los datos de la base de datos");
                    BufferedReader bufferedReader = null;
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
                    Log.i(TAG, "mandarEmailPass: empezamos a leer los datos");
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    Log.i(TAG, "mandarEmailPass: terminamos de leer los datos: " + result);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.i(TAG, "mandarEmailPass: cerramos conexion con la base de datos");
                    Log.i(TAG, "mandarEmailPass: DATOS OBTENIDOS CORRECTAMENTE");
                    Log.i(TAG, "mandarEmailPass: SALIDA");
                    return result;
                }
            } catch (IOException e) {
                Log.e(TAG, "mandarEmailPass: se ha producido un error con la conexion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "mandarEmailPass: la URL esta incorrecta", e);
        }
        return null;
    }

    private String recibirPassword(String type, String user_name, String login_url) {
        try {
            URL url = null;
            if (type.equalsIgnoreCase(context.getString(R.string.type_pass))) {
                Log.i(TAG, "recibirPassword: OBTENEMOS LA CONTRASEÑA");
                url = new URL(login_url + "/pass.php");
            }
            try {
                if (url != null) {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    Log.i(TAG, "recibirPassword: abrimos la conexion con la base de datos");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    Log.i(TAG, "recibirPassword: mandamos mensaje a la base de datos");
                    BufferedWriter bufferedWriter = null;
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
                    Log.i(TAG, "recibirPassword: codificamos el mensaje a mandar");
                    bufferedWriter.write(post_data);
                    Log.i(TAG, "recibirPassword: mandamos el mensaje: " + post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    Log.i(TAG, "recibirPassword: recibimos los datos de la base de datos");
                    BufferedReader bufferedReader = null;
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
                    Log.i(TAG, "recibirPassword: empezamos a leer los datos");
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    Log.i(TAG, "recibirPassword: terminamos de leer los datos: " + result);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.i(TAG, "recibirPassword: cerramos conexion con la base de datos");
                    Log.i(TAG, "recibirPassword: DATOS OBTENIDOS CORRECTAMENTE");
                    Log.i(TAG, "recibirPassword: SALIDA");
                    return result;
                }
            } catch (IOException e) {
                Log.e(TAG, "recibirPassword: se ha producido un error con la conexion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "recibirPassword: la URL esta incorrecta", e);
        }
        return null;
    }

    private String recibirClasificacion(String type, String login_url) {
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
                    Log.i(TAG, "recibirEquipos: SALIDA");

                    return result;
                }
            } catch (IOException e) {
                Log.e(TAG, "recibirEquipos: se ha producido un error con la conexion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "recibirEquipos: la URL esta incorrecta", e);
        }
        return null;
    }

}
