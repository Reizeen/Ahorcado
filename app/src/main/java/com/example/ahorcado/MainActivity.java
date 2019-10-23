package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView muestraPalabra;
    TextView textPuntos;
    TextView textVidas;
    Spinner posicion;
    Spinner letras;
    Button botonJugar;
    Button botonIniciar;
    Button botonFinalizar;
    Button botonOpciones;
    Button botonRegistrar;
    String[] listaPalabras;
    int vidas;
    boolean comodin;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        muestraPalabra = findViewById(R.id.idMuestraPalabra);
        textPuntos = findViewById(R.id.textPuntos);
        textVidas = findViewById(R.id.textVidas);
        botonJugar = findViewById(R.id.btnJugar);
        botonIniciar = findViewById(R.id.btnIniciar);
        botonFinalizar = findViewById(R.id.btnFinalizar);
        botonOpciones = findViewById(R.id.btnOpciones);
        botonRegistrar = findViewById(R.id.btnRegistrar);

        // Spinner de Letras
        letras = findViewById(R.id.spinnerLetras);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.listaLetras, android.R.layout.simple_spinner_item);
        letras.setAdapter(adapter);

        // Spinner de posicion NO TERMINADO
        posicion = findViewById(R.id.spinnerPosicion);
        /*ArrayAdapter adapterPosuicion
        posicion.setAdapter();*/

        listaPalabras = getResources().getStringArray(R.array.listaPalabras);

        // Desactivar botones hasta que no se inicie el juego NO TERMINADO
        botonJugar.setEnabled(false);
        botonFinalizar.setEnabled(false);

        // Cargar el archivo de preferencias
        cargarPreferencias();
    }

    // Actualizar el nivel de vidas desde el archivo de Preferencias.
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        vidas = preferences.getInt("nivelVidas", 0); // Se marca 0, si no existe el archivo de preferencias
        textVidas.setText(String.valueOf(vidas));
    }

    // Llevar a la Actividad Options las opciones actuales del juego
    public void onClickOptions(View view){
        Intent intencion = new Intent(MainActivity.this, Options.class);
        Bundle datos = new Bundle();
        datos.putBoolean("comodin", comodin);
        intencion.putExtras(datos);
        startActivityForResult(intencion, 102);
    }

    // Actualizar las opciones del juego
    public void onActivityResult(int requestCode, int resultCode, Intent code){
        if (requestCode == 102 && resultCode == RESULT_OK){
            Bundle options = code.getExtras();
            comodin = options.getBoolean("comodin");
            cargarPreferencias(); // Actualizamos de nuevo las Preferencias
        }
    }

    public void onClickRegister(View view){
        Intent intencion = new Intent(MainActivity.this, RegisterUsers.class);
        startActivity(intencion);
    }
}
