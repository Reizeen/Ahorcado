package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

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
    }

    public void onClickVolver(View view){
        actualizarNivel(); // Actualizar valor del numero de vidas
        // Si no hay establecido un numero de vidas, te marca un Toast
        if (vidas == 0){
            Toast.makeText(getApplicationContext(), "Establece un nivel o numero de vidas", Toast.LENGTH_SHORT).show();
        } else {
            Intent intencion = new Intent(Options.this, MainActivity.class);
            Bundle datos = new Bundle();
            datos.putInt("numeroVidas", vidas);
            datos.putBoolean("comodin", comodin());
            intencion.putExtras(datos);
            setResult(RESULT_OK, intencion);
            finish();
        }
    }

    public void actualizarNivel(){
        if (numeroVidas.getText().toString().isEmpty()){
            if(botonFacil.isChecked())
                vidas = 5;
            else if(botonMedio.isChecked())
                vidas = 10;
            else if (botonDificil.isChecked())
                vidas = 15;
        } else {
            vidas = Integer.valueOf(numeroVidas.getText().toString());
            Log.i(null, "entra: ");
        }
    }

    public boolean comodin(){
        if (comodin.isChecked())
            return true;
        return false;
    }

}
