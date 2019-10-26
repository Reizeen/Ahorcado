package com.example.ahorcado.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.ahorcado.R;

public class Options extends AppCompatActivity {

    RadioButton botonFacil;
    RadioButton botonMedio;
    RadioButton botonDificil;
    EditText numeroVidas;
    Switch comodin;
    int vidas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        botonFacil = findViewById(R.id.btnRadioFacil);
        botonMedio = findViewById(R.id.btnRadioMedio);
        botonDificil = findViewById(R.id.btnRadioDificil);
        numeroVidas = findViewById(R.id.numVidas);
        comodin = findViewById(R.id.comodin);

        cargarPreferencias();
    }

    // Actualizar el nivel de vidas desde el archivo de Preferencias.
    public void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        vidas = preferences.getInt("nivelVidas", 0); // Se marca 0, si no existe el archivo de preferencias

        if (vidas == 15)
            botonFacil.setChecked(true);
        else if (vidas == 10)
            botonMedio.setChecked(true);
        else if (vidas == 5)
            botonDificil.setChecked(true);
        else
            numeroVidas.setText(String.valueOf(vidas));
    }

    public void onClickVolver(View view) {
        // Actualizar valor del numero de vidas
        vidas = actualizarNivel();
        // Guardar el nivel de vidas en el archivo de preferencias
        guardarPreferencias();
        // Guardar en un Bundle el valor del comodin
        Intent intencion = new Intent(Options.this, MainActivity.class);
        Bundle datos = new Bundle();
        datos.putBoolean("comodin", comodin());
        intencion.putExtras(datos);
        setResult(RESULT_OK, intencion);
        finish();
    }

    public int actualizarNivel() {
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

    public void guardarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("nivelVidas", vidas);
        editor.commit();
    }

    public boolean comodin(){
        if (comodin.isChecked())
            return true;
        return false;
    }

}
