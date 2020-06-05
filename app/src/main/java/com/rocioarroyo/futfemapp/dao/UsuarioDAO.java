package com.rocioarroyo.futfemapp.dao;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dto.UsuarioDTO;

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

public class UsuarioDAO {

    private final String TAG = UsuarioDAO.class.getName();
    private Context context;
    private BackgroundWorker backgroundWorker;
    private String user_name;

    public UsuarioDAO() {}

    public UsuarioDAO (Context context, String user_name) {
        this.context = context;
        this.user_name = user_name;
    }

    public void validarLoginRegistro(ArrayList<String> resultados) {
        String resultado = null;
        if (resultados!=null && !resultados.isEmpty()) {
            resultado = resultados.get(0);
        }
        TextView txtError = ((Activity)context).findViewById(R.id.idErrorUsPass);
        if (resultado == null) {
            Log.i(TAG, "validarLoginRegistro: No se ha podido conectar con la base de datos");
            txtError.setText(context.getString(R.string.errorConectionDB));
            txtError.setVisibility(TextView.VISIBLE);
        } else {
            Log.i(TAG, "validarLoginRegistro: Se ha conectado con la base de datos");
            if (resultado.equalsIgnoreCase(context.getString(R.string.login_ok)) || resultado.equalsIgnoreCase(context.getString(R.string.registro_ok))) {
                Log.i(TAG, "validarLoginRegistro: abrimos la pantalla de inicio ya que el login es correcto");
                txtError.setVisibility(TextView.INVISIBLE);
                backgroundWorker = new BackgroundWorker(context, user_name);
                backgroundWorker.execute(context.getString(R.string.type_clasificacion));
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.login_fail))) {
                Log.i(TAG, "validarLoginRegistro: inicio de sesion incorrecto");
                txtError.setText(context.getString(R.string.errorUsPass));
                txtError.setVisibility(TextView.VISIBLE);
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.registro_fail))) {
                Log.i(TAG, "validarLoginRegistro: registro incorrecto");
                txtError.setText(context.getString(R.string.errorUsExistente));
                txtError.setVisibility(TextView.VISIBLE);
            }
        }
    }

    public void validarPassword(ArrayList<String> resultados) {
        String s = resultados.get(0);
        TextView txtError = ((Activity)context).findViewById(R.id.idErrorUsPass);
        Log.i(TAG, "validarPassword: leemos JSON");
        JSONArray jsonArray = null;
        try {
            if (!s.equalsIgnoreCase(context.getString(R.string.email_fail))) {
                jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setUsrEmail(jsonObject.getString("usu_email"));
                usuarioDTO.setUsrPassword(jsonObject.getString("usu_password"));
                txtError.setText(context.getString(R.string.title_password) + ": " + usuarioDTO.getUsrPassword());
                txtError.setTextColor(Color.parseColor("#F8F40D"));
                txtError.setVisibility(TextView.VISIBLE);
            } else {
                txtError.setText(context.getText(R.string.errorEmail));
                txtError.setTextColor(Color.parseColor("#FC3232"));
                txtError.setVisibility(TextView.VISIBLE);
            }
        } catch (JSONException e) {
            Log.e(TAG, "validarPassword: Error al validar la recuperacion de a contraseña", e);
        }
    }

    private void validarCambioContrasen(ArrayList<String> s) {
        String resultado = null;
        if (s!=null && !s.isEmpty()) {
            resultado = s.get(0);
        }
        if (resultado == null) {
            Log.i(TAG, "validarLoginRegistro: No se ha podido conectar con la base de datos");
        } else {
            Log.i(TAG, "validarLoginRegistro: Se ha conectado con la base de datos");
            if (resultado.equalsIgnoreCase(context.getString(R.string.cambio_ok))) {
                Log.i(TAG, "validarLoginRegistro: abrimos la pantalla de inicio ya que el login es correcto");
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.cambio_fail))) {
                Log.i(TAG, "validarLoginRegistro: inicio de sesion incorrecto");
            }
        }
    }

    public ArrayList<String> mandarEmailPass(String type, String pass_word, String login_url) {
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
                    ArrayList<String> resultados = new ArrayList<>();
                    resultados.add(result);
                    return resultados;
                }
            } catch (IOException e) {
                Log.e(TAG, "mandarEmailPass: se ha producido un error con la conexion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "mandarEmailPass: la URL esta incorrecta", e);
        }
        return null;
    }

    public ArrayList<String> recibirPassword(String type, String login_url) {
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
                    ArrayList<String> resultado = new ArrayList<>();
                    resultado.add(result);
                    return resultado;
                }
            } catch (IOException e) {
                Log.e(TAG, "recibirPassword: se ha producido un error con la conexion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "recibirPassword: la URL esta incorrecta", e);
        }
        return null;
    }



    public ArrayList<String> cambiarContrasena(String type, String nueva_pass, String login_url) {
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
                            + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(nueva_pass, "UTF-8");
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
                    ArrayList<String> resultados = new ArrayList<>();
                    resultados.add(result);
                    return resultados;
                }
            } catch (IOException e) {
                Log.e(TAG, "mandarEmailPass: se ha producido un error con la conexion", e);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "mandarEmailPass: la URL esta incorrecta", e);
        }
        return null;
    }

}
