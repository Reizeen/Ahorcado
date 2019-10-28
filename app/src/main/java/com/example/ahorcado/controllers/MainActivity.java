package com.example.ahorcado.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.ahorcado.R;
import com.example.ahorcado.objects.Partida;

public class MainActivity extends AppCompatActivity {

    private TextView textPalabra;
    private TextView textPuntos;
    private TextView textVidas;
    private Spinner posiciones;
    private Spinner letras;
    private Button botonJugar;
    private Button botonIniciar;
    private Button botonFinalizar;
    private Button botonOpciones;
    private Button botonRegistrar;

    private String userName;
    private int vidas;
    private boolean comodin;
    private Partida partida;
    private ArrayAdapter<CharSequence> adapterLetras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPalabra = findViewById(R.id.idMuestraPalabra);
        textPuntos = findViewById(R.id.textPuntos);
        textVidas = findViewById(R.id.textVidas);
        botonJugar = findViewById(R.id.btnJugar);
        botonIniciar = findViewById(R.id.btnIniciar);
        botonFinalizar = findViewById(R.id.btnFinalizar);
        botonOpciones = findViewById(R.id.btnOpciones);
        botonRegistrar = findViewById(R.id.btnRegistrar);

        letras = findViewById(R.id.spinnerLetras);
        posiciones = findViewById(R.id.spinnerPosicion);
        userName = "Desconocido";

        cargarPreferencias();

        botonJugar.setEnabled(false);
        botonFinalizar.setEnabled(false);
    }

    /** Cargar Archivo de preferencias */
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        vidas = preferences.getInt("nivelVidas", 10); // Se marca 0, si no existe el archivo de preferencias
        userName = preferences.getString("nameUser", "Desconocido");
        textVidas.setText(String.valueOf(vidas));
    }

    /** Utiliza Bundle para comodin */
    public void onClickOptions(View view){
        Intent intencion = new Intent(MainActivity.this, Options.class);
        Bundle datos = new Bundle();
        datos.putBoolean("comodin", comodin);
        intencion.putExtras(datos);
        startActivityForResult(intencion, 102);
    }

    /** Actualizar opciones del juego al volver de Opciones */
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

    public void onClickIniciar(View view){
        botonJugar.setEnabled(true);
        botonFinalizar.setEnabled(true);
        botonIniciar.setEnabled(false);
        botonOpciones.setEnabled(false);
        botonRegistrar.setEnabled(false);
        cargarPreferencias();

        partida = new Partida(vidas, comodin);
        partida.iniciarPartida();
        Log.i(null, partida.getPalabra());
        textPalabra.setText(partida.getImprimirPalabra());
        textPuntos.setText(String.valueOf(partida.getPuntos()));

        // CARGAR SPINNER POSICIONES
        ArrayAdapter<String> adapterPosiciones = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, partida.getListaPosiciones());
        posiciones.setAdapter(adapterPosiciones);

        // CARGAR SPINNER ALFABETICO
        adapterLetras = new ArrayAdapter(this, android.R.layout.simple_spinner_item, partida.getListaAlfabetica());
        letras.setAdapter(adapterLetras);
    }

    public void onClickJugar(View view){
        partida.compararLetra(posiciones.getSelectedItem().toString(), letras.getSelectedItem().toString());
        textPalabra.setText(partida.getImprimirPalabra());
        textVidas.setText(String.valueOf(partida.getVidas()));
        textPuntos.setText(String.valueOf(partida.getPuntos()));
    }

    public  void onClickFinalizar(View view){
        botonJugar.setEnabled(false);
        botonFinalizar.setEnabled(false);
        botonIniciar.setEnabled(true);
        botonOpciones.setEnabled(true);
        botonRegistrar.setEnabled(true);
    }
}
