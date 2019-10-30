package com.example.ahorcado.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahorcado.R;
import com.example.ahorcado.model.Partida;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private ArrayList<Character> listaAlfabetica;

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

    /** Cargar informacion del Archivo de Preferencias */
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        vidas = preferences.getInt("nivelVidas", 10);
        comodin = preferences.getBoolean("comodin", false);
        textVidas.setText(String.valueOf(vidas));
    }

    /** Envio de la Actividad RegisterUsers */
    public void onClickRegister(View view){
        Intent intencion = new Intent(MainActivity.this, RegisterUsers.class);
        Bundle datos = new Bundle();
        datos.putString("userName", userName);
        intencion.putExtras(datos);
        startActivityForResult(intencion, 101);
    }

    /** Envio de la Actividad Options junto a un Bundle para el comodin */
    public void onClickOptions(View view){
        Intent intencion = new Intent(MainActivity.this, Options.class);
        startActivityForResult(intencion, 102);
    }

    /** Actualizar opciones del juego al volver de la actividad de Options
     *  o actualizar registro al volver de la actividad RegisterUser */
    public void onActivityResult(int requestCode, int resultCode, Intent code){
        if (requestCode == 102 && resultCode == RESULT_OK){
            cargarPreferencias();
        } else if (requestCode == 101 && resultCode == RESULT_OK){
            Bundle datos = code.getExtras();
            userName = datos.getString("userName");
        }
    }

    /** Iniciación de la Partida */
    public void onClickIniciar(View view){
        botonJugar.setEnabled(true);
        botonFinalizar.setEnabled(true);
        botonIniciar.setEnabled(false);
        botonOpciones.setEnabled(false);
        botonRegistrar.setEnabled(false);

        partida = new Partida(vidas, comodin);
        partida.iniciarPartida();
        textPalabra.setText(partida.getImprimirPalabra());
        textPuntos.setText(String.valueOf(partida.getPuntos()));

        // CARGAR SPINNER POSICIONES
        ArrayAdapter<String> adapterPosiciones = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, partida.generarListaPosiciones());
        posiciones.setAdapter(adapterPosiciones);

        // CARGAR SPINNER ALFABETICO
        listaAlfabetica = partida.generarListaAlfabetica();
        adapterLetras = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaAlfabetica);
        letras.setAdapter(adapterLetras);
    }

    /** Comparar Letras */
    public void onClickJugar(View view){
        partida.compararLetra(posiciones.getSelectedItem().toString(), letras.getSelectedItem().toString());
        textPalabra.setText(partida.getImprimirPalabra());
        textVidas.setText(String.valueOf(partida.getVidas()));
        textPuntos.setText(String.valueOf(partida.getPuntos()));

        if(partida.palabraCompletada()){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = sdf.format(date);
            String mensaje = userName + ": " + textPuntos.getText().toString() + " puntos el " + fecha;
            Toast.makeText(getApplicationContext(),"GANASTE " + mensaje, Toast.LENGTH_LONG).show();
            onClickFinalizar(view);
        } else if (partida.getVidas() == 0){
            Toast.makeText(getApplicationContext(),"PERDISTE " + userName, Toast.LENGTH_LONG).show();
            onClickFinalizar(view);
        }

        if(partida.borrarLetra(letras.getSelectedItem().toString())){
            listaAlfabetica.remove(letras.getSelectedItem());
            adapterLetras.notifyDataSetChanged();
        }
    }

    /** Finalizar el Juego al hacer click en btnFinalizar */
    public void onClickFinalizar(View view){
        botonJugar.setEnabled(false);
        botonFinalizar.setEnabled(false);
        botonIniciar.setEnabled(true);
        botonOpciones.setEnabled(true);
        botonRegistrar.setEnabled(true);
    }
}
