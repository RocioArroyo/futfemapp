package com.rocioarroyo.futfemapp.dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;

public class CambioContrasenaDialog extends DialogFragment {

    private Context context;
    private String user_name;
    private TextInputEditText tvPass;
    private TextInputEditText tvCambPass;

    /**
     * OnAttach
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Constructor vacio
     */
    public CambioContrasenaDialog() {}

    /**
     * Constructor con las constantes de la apliacion
     * @param context
     * @param user_name
     */
    public CambioContrasenaDialog(Context context, String user_name) {
        this.context = context;
        this.user_name = user_name;
    }

    /**
     * Método de creacion del dialogo
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity(), R.style.custom_dialog);
        ventana.setTitle(getString(R.string.title_cambiar_pass));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View vistaVentana = inflater.inflate(R.layout.dialog_cambio_pass, null);
        tvPass = vistaVentana.findViewById(R.id.idNuevaPass);
        tvCambPass = vistaVentana.findViewById(R.id.idRepNuePass);
        ventana.setView(vistaVentana)
                .setNegativeButton(context.getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton(context.getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (tvPass.getText().toString().isEmpty() || tvCambPass.getText().toString().isEmpty()) {
                    FragmentManager fm = getFragmentManager();
                    InformativoDialog dp = new InformativoDialog(context.getString(R.string.errorCampoVacio));
                    dp.show(fm, "Tag Dialogo Informativo");
                } else if (tvPass.getText().toString().equalsIgnoreCase(tvCambPass.getText().toString())) {
                    BackgroundWorker backgroundWorker = new BackgroundWorker(context, user_name);
                    backgroundWorker.execute(context.getString(R.string.type_cambiar_pass), tvPass.getText().toString());
                    FragmentManager fm = getFragmentManager();
                    InformativoDialog dp = new InformativoDialog(context.getString(R.string.pass_cambiada));
                    dp.show(fm, "Tag Dialogo informacion");
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(tvPass.getWindowToken(), 0);
                    inputMethodManager.hideSoftInputFromWindow(tvCambPass.getWindowToken(), 0);
                    FragmentManager fm = getFragmentManager();
                    InformativoDialog dp = new InformativoDialog(context.getString(R.string.errorPass));
                    dp.show(fm, "Tag Dialogo Informativo");
                }

            }
        });
        return ventana.create();
    }
}

