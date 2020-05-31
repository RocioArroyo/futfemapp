package com.rocioarroyo.futfemapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.material.textfield.TextInputLayout;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getName();
    private TextInputEditText txtUserName, txtPassword, txtPassRepeat;
    private TextInputLayout txtLayoutPassRepeat, txtLayoutPass;
    private TextView txtErrorUsPass, txtRegistro, txtLogin, txtRecordarPass;
    private Button btnLogin, btnRegistrarse, btnRecordarPass;

    public LoginActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: LOGIN PULSADO");
                login();
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

    }

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
            txtErrorUsPass.setVisibility(TextView.INVISIBLE);
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(getString(R.string.type_login), txtUserName.getText().toString(), txtPassword.getText().toString());
        }
    }

    private void registrarse() {
        if (TextUtils.isEmpty(txtUserName.getText().toString().trim()) || TextUtils.isEmpty(txtPassword.getText().toString().trim()) || TextUtils.isEmpty(txtPassRepeat.getText().toString().trim())) {
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
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(getString(R.string.type_registro), txtUserName.getText().toString(), txtPassword.getText().toString());
        }
    }

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
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(getString(R.string.type_pass), txtUserName.getText().toString());
        }
    }

    private boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(getString(R.string.EMAIL_PATTERN));
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
