package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


    ImageView imagen;
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


        imagen = findViewById(R.id.idImagen);
        muestraPalabra = findViewById(R.id.idMuestraPalabra);
        textPuntos = findViewById(R.id.textPuntos);
        textVidas = findViewById(R.id.textVidas);
        botonJugar = findViewById(R.id.btnJugar);
        botonIniciar = findViewById(R.id.btnIniciar);
        botonFinalizar = findViewById(R.id.btnFinalizar);
        botonOpciones = findViewById(R.id.btnOpciones);
        botonRegistrar = findViewById(R.id.btnRegistrar);

        letras = findViewById(R.id.spinnerLetras);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.listaLetras, android.R.layout.simple_spinner_item);
        letras.setAdapter(adapter);

        posicion = findViewById(R.id.spinnerPosicion);
        /*ArrayAdapter adapterPosuicion
        posicion.setAdapter();*/

        listaPalabras = getResources().getStringArray(R.array.listaPalabras);

        botonJugar.setEnabled(false);
        botonFinalizar.setEnabled(false);

        comprobarDatos();
    }

    public void onClickRegister(View view){
        Intent intencion = new Intent(MainActivity.this, RegisterUsers.class);
        startActivity(intencion);
    }

    public void onClickOptions(View view){
        Intent intencion = new Intent(MainActivity.this, Options.class);
        startActivity(intencion);
    }


    public void comprobarDatos(){
        Bundle datosOptions = this.getIntent().getExtras();
        if(datosOptions != null) {
            vidas = datosOptions.getInt("numeroVidas");
            comodin = datosOptions.getBoolean("comodin");
        }

        Bundle datosRegister = this.getIntent().getExtras();
        if(datosRegister != null){
            userName = datosRegister.getString("user");
        }
    }
}
