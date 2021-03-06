package com.rocioarroyo.futfemapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.material.textfield.TextInputLayout;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dialogos.CargaDialog;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getName();
    private TextInputEditText txtUserName, txtPassword, txtPassRepeat;
    private TextInputLayout txtLayoutPassRepeat, txtLayoutPass;
    private TextView txtErrorUsPass, txtRegistro, txtLogin, txtRecordarPass;
    private Button btnLogin, btnRegistrarse, btnRecordarPass;
    private String user_name;
    private Context context = LoginActivity.this;
    private FragmentManager fm;

    /**
     * Constructor vacio
     */
    public LoginActivity() {}

    /**
     * Método que crea la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUserName = findViewById(R.id.idUserName);
        txtPassword = findViewById(R.id.idPassword);
        btnLogin = findViewById(R.id.idBtnLogin);
        txtErrorUsPass = findViewById(R.id.idErrorUsPass);
        txtRegistro = findViewById(R.id.idRegistro);
        txtPassRepeat = findViewById(R.id.idPasswordRepeat);
        btnRegistrarse = findViewById(R.id.idBtnRegistrarse);
        txtLogin = findViewById(R.id.idLogin);
        txtLayoutPassRepeat = findViewById(R.id.idLayoutPassRepeat);
        txtRecordarPass = findViewById(R.id.idPassOlvidada);
        btnRecordarPass = findViewById(R.id.idBtnRecordarPass);
        txtLayoutPass = findViewById(R.id.idLayoutPass);

        fm = getSupportFragmentManager();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: LOGIN PULSADO");
                login();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
            }
        });

        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPassRepeat.setVisibility(TextInputEditText.VISIBLE);
                btnRegistrarse.setVisibility(Button.VISIBLE);
                txtRegistro.setVisibility(TextView.GONE);
                btnLogin.setVisibility(Button.GONE);
                txtLogin.setVisibility(TextView.VISIBLE);
                txtLayoutPassRepeat.setVisibility(TextInputLayout.VISIBLE);
                txtRecordarPass.setVisibility(TextView.GONE);
                txtErrorUsPass.setVisibility(TextView.INVISIBLE);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: REGISTRO PULSADO");
                registrarse();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(txtPassRepeat.getWindowToken(), 0);

            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPassRepeat.setVisibility(TextInputEditText.GONE);
                btnRegistrarse.setVisibility(Button.GONE);
                txtRegistro.setVisibility(TextView.VISIBLE);
                btnLogin.setVisibility(Button.VISIBLE);
                txtLogin.setVisibility(TextView.GONE);
                txtLayoutPassRepeat.setVisibility(TextInputLayout.GONE);
                btnRecordarPass.setVisibility(Button.GONE);
                txtRecordarPass.setVisibility(TextView.VISIBLE);
                txtLayoutPass.setVisibility(TextInputLayout.VISIBLE);
                txtPassword.setVisibility(TextInputLayout.VISIBLE);
                txtErrorUsPass.setVisibility(TextView.INVISIBLE);
            }
        });

        txtRecordarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPassword.setVisibility(TextInputEditText.GONE);
                txtLayoutPass.setVisibility(TextInputLayout.GONE);
                btnLogin.setVisibility(Button.GONE);
                txtRegistro.setVisibility(TextView.GONE);
                txtRecordarPass.setVisibility(TextView.GONE);
                btnRecordarPass.setVisibility(Button.VISIBLE);
                txtLogin.setVisibility(TextView.VISIBLE);
                txtErrorUsPass.setVisibility(TextView.INVISIBLE);
            }
        });

        btnRecordarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarContrasena();
            }
        });

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        fm = getSupportFragmentManager();

    }

    /**
     * Método que se lanza al hacer click en el boton de incio de sesio y que comprueba errores vasicoas antes de lanzar el hilo
     */
    private void login() {
        if (TextUtils.isEmpty(txtUserName.getText().toString().trim()) || TextUtils.isEmpty(txtPassword.getText().toString().trim())) {
            Log.i(TAG, "login: campo vacio");
            txtErrorUsPass.setText(getString(R.string.errorCampoVacio));
            txtErrorUsPass.setTextColor(Color.parseColor("#FC3232"));
            txtErrorUsPass.setVisibility(TextView.VISIBLE);
        } else if (!emailValidator(txtUserName.getText().toString())) {
            Log.i(TAG, "login: email mal formado: " + txtUserName.getText().toString());
            txtErrorUsPass.setText(getString(R.string.errorValidacionEmail));
            txtErrorUsPass.setTextColor(Color.parseColor("#FC3232"));
            txtErrorUsPass.setVisibility(TextView.VISIBLE);
        } else {
            Log.i(TAG, "login: TODO VALIDADO");
            user_name = txtUserName.getText().toString();
            txtErrorUsPass.setVisibility(TextView.INVISIBLE);
            BackgroundWorker backgroundWorker = new BackgroundWorker(context, user_name, fm);
            backgroundWorker.execute(getString(R.string.type_login), txtPassword.getText().toString());
        }
    }

    /**
     * Método que se lanzar al hacer click en el boton de registro y que comprueba antes errores vasico, antes de lanzar el hilo
     */
    private void registrarse() {
        if (TextUtils.isEmpty(txtUserName.getText().toString().trim()) || TextUtils.isEmpty(txtPassword.getText().toString().trim())
                || TextUtils.isEmpty(txtPassRepeat.getText().toString().trim())) {
            Log.i(TAG, "registrarse: campo vacio");
            txtErrorUsPass.setText(getString(R.string.errorCampoVacio));
            txtErrorUsPass.setTextColor(Color.parseColor("#FC3232"));
            txtErrorUsPass.setVisibility(TextView.VISIBLE);
        } else if (!emailValidator(txtUserName.getText().toString())) {
            Log.i(TAG, "registrarse: email mal formado: " + txtUserName.getText().toString());
            txtErrorUsPass.setText(getString(R.string.errorValidacionEmail));
            txtErrorUsPass.setTextColor(Color.parseColor("#FC3232"));
            txtErrorUsPass.setVisibility(TextView.VISIBLE);
        } else  if(!txtPassword.getText().toString().equalsIgnoreCase(txtPassRepeat.getText().toString())) {
            Log.i(TAG, "registrarse: contraseña no coinciden: " + txtPassword.getText().toString() + " != " + txtPassRepeat.getText().toString());
            txtErrorUsPass.setText(getString(R.string.errorPass));
            txtErrorUsPass.setTextColor(Color.parseColor("#FC3232"));
            txtErrorUsPass.setVisibility(TextView.VISIBLE);
        }else {
            Log.i(TAG, "registrarse: TODO VALIDADO");
            txtErrorUsPass.setVisibility(TextView.INVISIBLE);
            user_name = txtUserName.getText().toString();
            BackgroundWorker backgroundWorker = new BackgroundWorker(context, user_name, fm);
            backgroundWorker.execute(getString(R.string.type_registro), txtPassword.getText().toString());
        }
    }

    /**
     * Método que obtiene la contraseña y se ejecuta al clicar el boton de recuperar contraseña, prueba errores vasicaon antes de lanzar el hilo
     */
    private void recuperarContrasena() {
        if (TextUtils.isEmpty(txtUserName.getText().toString().trim())) {
            Log.i(TAG, "registrarse: campo vacio");
            txtErrorUsPass.setText(getString(R.string.errorCampoVacio));
            txtErrorUsPass.setTextColor(Color.parseColor("#FC3232"));
            txtErrorUsPass.setVisibility(TextView.VISIBLE);
        } else if (!emailValidator(txtUserName.getText().toString())) {
            Log.i(TAG, "registrarse: email mal formado: " + txtUserName.getText().toString());
            txtErrorUsPass.setText(getString(R.string.errorValidacionEmail));
            txtErrorUsPass.setTextColor(Color.parseColor("#FC3232"));
            txtErrorUsPass.setVisibility(TextView.VISIBLE);
        } else {
            Log.i(TAG, "registrarse: TODO VALIDADO");
            txtErrorUsPass.setVisibility(TextView.INVISIBLE);
            user_name = txtUserName.getText().toString();
            BackgroundWorker backgroundWorker = new BackgroundWorker(context, user_name);
            backgroundWorker.execute(getString(R.string.type_pass));
        }
    }

    /**
     * Método de comprobacion de error con la formacion del email
     * @param email
     * @return
     */
    private boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(getString(R.string.EMAIL_PATTERN));
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
