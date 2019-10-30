package com.example.ahorcado.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.ahorcado.R;

public class Options extends AppCompatActivity {

    private RadioButton botonFacil;
    private RadioButton botonMedio;
    private RadioButton botonDificil;
    private EditText numeroVidas;
    private Switch botoncComodin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        botonFacil = findViewById(R.id.btnRadioFacil);
        botonMedio = findViewById(R.id.btnRadioMedio);
        botonDificil = findViewById(R.id.btnRadioDificil);
        numeroVidas = findViewById(R.id.numVidas);
        botoncComodin = findViewById(R.id.comodin);
        cargarPreferencias();
    }

    // Actualizar el nivel de vidas desde el archivo de Preferencias.
    /** Actualizar las opciones del Archivo Preferencias */
    public void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        int vidas = preferences.getInt("nivelVidas", 10); // Se marca 0, si no existe el archivo de preferencias
        boolean comodin = preferences.getBoolean("comodin", false);

        if (vidas == 15)
            botonFacil.setChecked(true);
        else if (vidas == 10)
            botonMedio.setChecked(true);
        else if (vidas == 5)
            botonDificil.setChecked(true);
        else
            numeroVidas.setText(String.valueOf(vidas));

        if(comodin)
            botoncComodin.setChecked(true);
    }

    /** Actualizar el numero devidas */
    public int actualizarVidas() {
        if (numeroVidas.getText().toString().isEmpty()) {
            if (botonFacil.isChecked())
                return 15;
            else if (botonMedio.isChecked())
                return 10;
            else if (botonDificil.isChecked())
                return 5;
        } else {
            return Integer.valueOf(numeroVidas.getText().toString());
        }
        return 10; // Vida por defecto
    }

    /** Actualizar comodin */
    public boolean comodin(){
        if (botoncComodin.isChecked())
            return true;
        return false;
    }

    /** Guardar las Opciones en el Archivo de Preferencias */
    public void guardarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("nivelVidas", actualizarVidas());
        editor.putBoolean("comodin", comodin());
        editor.commit();
    }

    /** Volver a la Activdad anterior */
    public void onClickVolver(View view) {
        guardarPreferencias();
        Intent intencion = new Intent(Options.this, MainActivity.class);
        setResult(RESULT_OK, intencion);
        finish();
    }
}
