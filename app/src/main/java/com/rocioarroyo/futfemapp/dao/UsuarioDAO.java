package com.rocioarroyo.futfemapp.dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dialogos.CambioContrasenaDialog;
import com.rocioarroyo.futfemapp.dialogos.CargaDialog;
import com.rocioarroyo.futfemapp.dialogos.InformativoDialog;
import com.rocioarroyo.futfemapp.dto.UsuarioDTO;
import com.rocioarroyo.futfemapp.excepcion.FutFemAppException;
import com.rocioarroyo.futfemapp.fragments.ClasificacionFragment;
import com.rocioarroyo.futfemapp.ui.LoginActivity;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UsuarioDAO {

    private final String TAG = UsuarioDAO.class.getName();
    private Context context;
    private BackgroundWorker backgroundWorker;
    private String user_name;

    /**
     * Constructor vacio
     */
    public UsuarioDAO() {}

    /**
     * Constructor con las dos constantes necesarias para el proyecto
     * @param context
     * @param user_name
     */
    public UsuarioDAO (Context context, String user_name) {
        this.context = context;
        this.user_name = user_name;
    }

    /**
     * Método de validcion de login y registro
     * Método que valida la conexion de datos con el servidor y controlo que la entrada a la apliación sea la correcta
     * @param resultados
     * @param ccd
     * @throws FutFemAppException
     */
    public void validarLoginRegistro(ArrayList<String> resultados, CargaDialog ccd) throws FutFemAppException {
        String resultado = null;
        if (resultados!=null && !resultados.isEmpty()) {
            resultado = resultados.get(0);
        } else {
            throw new FutFemAppException("NO SE HA RECIBIDO NINGUN PARAMETRO DE LA CONEXION CON EL SERVIDOR");
        }
        TextView txtError = ((Activity)context).findViewById(R.id.idErrorUsPass);
        if (resultado == null) {
            Log.i(TAG, "validarLoginRegistro: No se ha podido conectar con la base de datos");
            txtError.setText(context.getString(R.string.errorConectionDB));
            txtError.setVisibility(TextView.VISIBLE);
            ccd.dismiss();
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
                ccd.dismiss();
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.registro_fail))) {
                Log.i(TAG, "validarLoginRegistro: registro incorrecto");
                txtError.setText(context.getString(R.string.errorUsExistente));
                txtError.setVisibility(TextView.VISIBLE);
                ccd.dismiss();
            } else {
                throw new FutFemAppException("SE HA PRODUCIDO UN ERROR NO CONROLODA");
            }
        }
    }

    /**
     * Método que valida la contraseña
     * Método que valida la recuperacion de la contraseña
     * @param resultados
     * @throws JSONException
     */
    public void validarPassword(ArrayList<String> resultados) throws JSONException {
        String s = resultados.get(0);
        TextView txtError = ((Activity)context).findViewById(R.id.idErrorUsPass);
        Log.i(TAG, "validarPassword: leemos JSON");
        JSONArray jsonArray = null;
        if (!s.equalsIgnoreCase(context.getString(R.string.email_fail))) {
            jsonArray = new JSONArray(s);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setUsrEmail(jsonObject.getString("usu_email"));
            usuarioDTO.setUsrPassword(jsonObject.getString("usu_password"));
            txtError.setText(context.getString(R.string.title_password) + ": " + usuarioDTO.getUsrPassword());
            txtError.setTextColor(Color.parseColor("#F8F40D"));
            txtError.setVisibility(TextView.VISIBLE);
            Log.i(TAG, "validarPassword: JSON LEIDO CORRECTAMENTE");
        } else {
            txtError.setText(context.getText(R.string.errorEmail));
            txtError.setTextColor(Color.parseColor("#FC3232"));
            txtError.setVisibility(TextView.VISIBLE);
            Log.i(TAG, "validarPassword: NO SE ENCONTRA EL EMAIL: " + s);
        }
    }

    /**
     * Método de validación del cambio de contraseña
     * Método que controla la resolución del contacto con el servidor al pedir un cambio de contraseña
     * @param s
     * @return
     * @throws FutFemAppException
     */
    public String validarCambioContrasena(ArrayList<String> s) throws FutFemAppException {
        String resultado = null;
        if (s!=null && !s.isEmpty()) {
            resultado = s.get(0);
        } else {
            throw new FutFemAppException("NO SE HAN ENCONTRADO DATOS DEVUELTOS");
        }
        if (resultado == null) {
            throw new FutFemAppException("NO SE HA PODIDO CONECTAR CON EL SERVIDOR");
        } else {
            Log.i(TAG, "validarCambioContrasena: Se ha conectado con la base de datos");
            if (resultado.equalsIgnoreCase(context.getString(R.string.cambio_ok))) {
                Log.i(TAG, "validarCambioContrasena: abrimos la pantalla de inicio ya que el login es correcto");
                return context.getString(R.string.pass_cambiada);
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.cambio_fail))) {
                Log.i(TAG, "validarCambioContrasena: inicio de sesion incorrecto");
                return context.getString(R.string.errorConectionDB);
            }
        }
        throw new FutFemAppException("NO SE HAN ENCONTRADO LOS DATOS DEVUELTOS DEL SERVIDOR");
    }

    /**
     * Método validación baja
     * Método que informa al desarrollador de si se ha procesado bien la baja, si se ha producido un error se informará al usuario
     * @param s
     * @throws FutFemAppException
     */
    public void validaBaja(ArrayList<String>s) throws FutFemAppException {
        String result=s.get(0);
        if (result.equalsIgnoreCase(context.getString(R.string.baja_ok))) {
            Log.i(TAG, "validaBaja: SE HA DADO DE BAJA CORRECTAMENTE");
        } else if (result.equalsIgnoreCase(context.getString(R.string.baja_fail))) {
            Log.i(TAG, "validaBaja: NO SE HA CONSEGUIDO DAR DE BAJA");
            throw new FutFemAppException("NO SE HA PODIDO DAR DE BAJA CORRECTAMENTE");
        }
    }

    /**
     * Método de conexión con el servidor
     * étodo que manda los datos e usuario y contraseña al servidor para validar la entrada a la aplicación
     * @param type
     * @param pass_word
     * @param login_url
     * @return
     * @throws FutFemAppException
     * @throws IOException
     */
    public ArrayList<String> mandarEmailPass(String type, String pass_word, String login_url) throws FutFemAppException, IOException {
        URL url;
        if (type.equalsIgnoreCase(context.getString(R.string.type_login))) {
            Log.i(TAG, "mandarEmailPass: OBTENEMOS DATOS DEL LOGIN");
            url = new URL(login_url + "/login.php");
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_registro))) {
            Log.i(TAG, "mandarEmailPass: OBTENEMOS DATOS DEL REGISTRO");
            url = new URL(login_url + "/registro.php");
        } else {
            throw new FutFemAppException("NO SE HA ENCONTRADO LA URL");
        }
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

    /**
     * Método de conexión con el servidor
     * Método que recibe la contraseña olvidada del usuario con los datos recibidos del servidor
     * @param type
     * @param login_url
     * @return
     * @throws FutFemAppException
     * @throws IOException
     */
    public ArrayList<String> recibirPassword(String type, String login_url) throws FutFemAppException, IOException {
        URL url;
        if (type.equalsIgnoreCase(context.getString(R.string.type_pass))) {
            Log.i(TAG, "recibirPassword: OBTENEMOS LA CONTRASEÑA");
            url = new URL(login_url + "/pass.php");
        } else {
            throw new FutFemAppException("ERROR AL ENCONTRAR LA URL");
        }
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

    /**
     * Método de conexión con el servidor
     * Método que recibe y manda información con el servidor para cambiar la contraseña
     * @param type
     * @param nueva_pass
     * @param login_url
     * @return
     * @throws IOException
     * @throws FutFemAppException
     */
    public ArrayList<String> cambiarContrasena(String type, String nueva_pass, String login_url) throws IOException, FutFemAppException {
        URL url;
        if (type.equalsIgnoreCase(context.getString(R.string.type_cambiar_pass))) {
            Log.i(TAG, "mandarEmailPass: OBTENEMOS DATOS DEL LOGIN");
            url = new URL(login_url + "/cambiarpass.php");
        } else {
            throw new FutFemAppException("NO SE HA PODIDO LEER LA URL");
        }
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

    public ArrayList<String> darBaja(String type, String login_url) throws FutFemAppException, IOException {
        URL url;
        if (type.equalsIgnoreCase(context.getString(R.string.type_baja))) {
            Log.i(TAG, "mandarEmailPass: OBTENEMOS DATOS DEL LOGIN");
            url = new URL(login_url + "/baja.php");
        } else {
            throw new FutFemAppException("NO SE HA PODIDO LEER LA URL");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        Log.i(TAG, "mandarEmailPass: abrimos la conexion con la base de datos");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        Log.i(TAG, "mandarEmailPass: mandamos mensaje a la base de datos");
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
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

}
