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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textPalabra;
    private TextView textPuntos;
    private TextView textVidas;
    private TextView textUserName;
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
    private ArrayAdapter<String> adapterLetras;
    private ArrayList<String> listaAlfabetica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPalabra = findViewById(R.id.idMuestraPalabra);
        textPuntos = findViewById(R.id.textPuntos);
        textVidas = findViewById(R.id.textVidas);
        textUserName = findViewById(R.id.idUserName);
        botonJugar = findViewById(R.id.btnJugar);
        botonIniciar = findViewById(R.id.btnIniciar);
        botonFinalizar = findViewById(R.id.btnFinalizar);
        botonOpciones = findViewById(R.id.btnOpciones);
        botonRegistrar = findViewById(R.id.btnRegistrar);

        letras = findViewById(R.id.spinnerLetras);
        posiciones = findViewById(R.id.spinnerPosicion);

        userName = "Desconocido";
        textUserName.setText("Jugador: " + userName);

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
            textUserName.setText("Jugador: " + userName);
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

    /** Llamar al metodo Comparar Letras, controlar la finalizacion de la partida y borrar letras del Spinner */
    public void onClickJugar(View view){
        partida.compararLetra(posiciones.getSelectedItem().toString(), letras.getSelectedItem().toString());
        textPalabra.setText(partida.getImprimirPalabra());
        textVidas.setText(String.valueOf(partida.getVidas()));
        textPuntos.setText(String.valueOf(partida.getPuntos()));

        if(partida.palabraCompletada()){
            partida.guardarPuntuacion(this, userName);
            Toast.makeText(getApplicationContext(),userName + " Ganaste - " + textPuntos.getText().toString() + " Puntos", Toast.LENGTH_LONG).show();
            onClickFinalizar(view);
        } else if (partida.getVidas() == 0){
            Toast.makeText(getApplicationContext(),"Perdiste " + userName, Toast.LENGTH_LONG).show();
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

    /** Guardar la informacion al rotar la pantalla */
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        guardarEstado.putString("user", userName);
        guardarEstado.putBoolean("partidaEmpezada", false);

        if (botonJugar.isEnabled()) {
            guardarEstado.putBoolean("partidaEmpezada", true);
            guardarEstado.putInt("vidas", partida.getVidas());
            guardarEstado.putInt("puntos", partida.getPuntos());
            guardarEstado.putString("palabra", partida.getPalabra());
            guardarEstado.putCharArray("letrasAdivinadas", partida.getLetrasAdivinadas());
            guardarEstado.putStringArrayList("listaPosiciones", partida.getListaPosiciones());
            guardarEstado.putStringArrayList("listaLetras", listaAlfabetica);
        }
    }

    /** Recoger la informacion al rotar la pantalla */
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        userName = recEstado.getString("user");
        textUserName.setText("Jugador: " + userName);

        if(recEstado.getBoolean("partidaEmpezada")){
            partida = new Partida(recEstado.getInt("vidas"), comodin);
            partida.setPuntos(recEstado.getInt("puntos"));
            partida.setPalabra(recEstado.getString("palabra"));
            partida.setLetrasAdivinadas(recEstado.getCharArray("letrasAdivinadas"));
            partida.setListaPosiciones(recEstado.getStringArrayList("listaPosiciones"));

            textVidas.setText(String.valueOf(partida.getVidas()));
            textPuntos.setText(String.valueOf(partida.getPuntos()));
            textPalabra.setText(partida.getImprimirPalabra());

            ArrayAdapter<String> adapterPosiciones = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, partida.generarListaPosiciones());
            posiciones.setAdapter(adapterPosiciones);

            listaAlfabetica = recEstado.getStringArrayList("listaLetras");
            adapterLetras = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaAlfabetica);
            letras.setAdapter(adapterLetras);

            botonJugar.setEnabled(true);
            botonFinalizar.setEnabled(true);
            botonIniciar.setEnabled(false);
            botonOpciones.setEnabled(false);
            botonRegistrar.setEnabled(false);
        }
    }
}
