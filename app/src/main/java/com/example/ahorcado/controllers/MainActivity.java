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
import com.example.ahorcado.objects.Palabra;

import java.util.ArrayList;
import java.util.Arrays;

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

    private ArrayList<Character> listaLetras;
    private ArrayList<String> listaPosiciones;
    private String userName;
    private int vidas;
    private int puntos;
    private boolean comodin;
    private ArrayList<String> listaPalabras;
    private Palabra palabra;
    private String muestraPalabra;
    private char[] letrasPalabra;
    private char[] letrasAdivinadas;




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

        // Spinner de Letras
        letras = findViewById(R.id.spinnerLetras);
        cargarSpinnerLetras();

        // Spinner de posicion NO TERMINADO
        posiciones = findViewById(R.id.spinnerPosicion);

        userName = "Desconocido";
        listaPalabras = new ArrayList<>();
        listaPalabras.add("FUTBOL");
        listaPalabras.add("BICICLETA");
        listaPalabras.add("ORDENADOR");
        listaPalabras.add("COCHE");

        // Cargar el archivo de preferencias
        cargarPreferencias();

        // Desactivar botones hasta que no se inicie el juego NO TERMINADO
        botonJugar.setEnabled(false);
        botonFinalizar.setEnabled(false);
    }

    // Actualizar el nivel de vidas desde el archivo de Preferencias.
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        vidas = preferences.getInt("nivelVidas", 10); // Se marca 0, si no existe el archivo de preferencias
        userName = preferences.getString("nameUser", "Desconocido");
        textVidas.setText(String.valueOf(vidas));
    }

    public void cargarSpinnerLetras(){
        listaLetras = new ArrayList<>();
        listaLetras.add(' ');
        listaLetras.add((char)209);
        for (char x = 65; x <= 90; x++)
            listaLetras.add(x);
        ArrayAdapter<CharSequence> adapterLetras = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaLetras);
        letras.setAdapter(adapterLetras);
    }

    // Llevar a la Actividad Options las opciones actuales del juego
    public void onClickOptions(View view){
        Intent intencion = new Intent(MainActivity.this, Options.class);
        Bundle datos = new Bundle();
        datos.putBoolean("comodin", comodin);
        intencion.putExtras(datos);
        startActivityForResult(intencion, 102);
    }

    public void onClickRegister(View view){
        Intent intencion = new Intent(MainActivity.this, RegisterUsers.class);
        startActivity(intencion);
    }

    // Actualizar las opciones del juego
    public void onActivityResult(int requestCode, int resultCode, Intent code){
        if (requestCode == 102 && resultCode == RESULT_OK){
            Bundle options = code.getExtras();
            comodin = options.getBoolean("comodin");
            cargarPreferencias(); // Actualizamos de nuevo las Preferencias
        }
    }

    // Inicia el juego
    public void onClickIniciar(View view){
        botonJugar.setEnabled(true);
        botonFinalizar.setEnabled(true);
        botonIniciar.setEnabled(false);
        botonOpciones.setEnabled(false);
        botonRegistrar.setEnabled(false);

        palabra = new Palabra(listaPalabras);
        palabra.seleccionPalabra();
        muestraPalabra = "";
        letrasPalabra = palabra.palabraDividia();
        letrasAdivinadas = new char[letrasPalabra.length];
        for (int x = 0; x < letrasAdivinadas.length; x++){
            muestraPalabra += " _ ";
            letrasAdivinadas[x] = '_';
        }
        textPalabra.setText(muestraPalabra);

        // Cargar Spinner Posiciones
        listaPosiciones = new ArrayList<>();
        if(comodin)
            listaPosiciones.add("*");
        for(int x = 1; x <= letrasPalabra.length; x++){
            listaPosiciones.add(String.valueOf(x));
        }
        ArrayAdapter<String> adapterPosiciones = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaPosiciones);
        posiciones.setAdapter(adapterPosiciones);
    }

    // Controlador del funcionamiento del juego
    public void onClickJugar(View view){
        if (posiciones.getSelectedItem().equals("*")){
            boolean adivinado = false;
            for(int x = 0; x < letrasPalabra.length; x++){
                if(letras.getSelectedItem().equals(letrasPalabra[x])){
                    letrasAdivinadas[x] = letrasPalabra[x];
                    adivinado = true;
                }
            }
            if(adivinado) {
                puntos++;
                textPuntos.setText(String.valueOf(puntos));
            } else {
                vidas--;
                textVidas.setText(String.valueOf(vidas));
            }

        } else {
            // adaptar posicion del usuario a las posiciones del Array de Char
            int pos = Integer.parseInt(posiciones.getSelectedItem().toString()) - 1;
            if(letras.getSelectedItem().equals(letrasPalabra[pos])){
                puntos+=5;
                textPuntos.setText(String.valueOf(puntos));
                letrasAdivinadas[pos] = letrasPalabra[pos];
            } else {
                vidas--;
                textVidas.setText(String.valueOf(vidas));
            }
        }

        // Imprimir resultado en pantalla
        muestraPalabra = "";
        for(int x = 0; x < letrasAdivinadas.length; x++){
            muestraPalabra += " " + letrasAdivinadas[x] + " ";
        }
        textPalabra.setText(muestraPalabra);
    }

    public  void onClickFinalizar(View view){
        botonJugar.setEnabled(false);
        botonFinalizar.setEnabled(false);
        botonIniciar.setEnabled(true);
        botonOpciones.setEnabled(true);
        botonRegistrar.setEnabled(true);
        cargarPreferencias();
        puntos = 0;
        textPuntos.setText(String.valueOf(puntos));
    }

}
