package com.example.ahorcado.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ahorcado.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class RegisterUsers extends AppCompatActivity {

    private EditText name;
    private TextView textPuntuaciones;
    private ArrayList<String> puntuaciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        name = findViewById(R.id.editNameUser);
        textPuntuaciones = findViewById(R.id.idTextPuntuaciones);

        Bundle datos = this.getIntent().getExtras();
        name.setText(datos.getString("userName"));

        leerFicherPuntuaciones();
        imprimirPuntuaciones();

    }

    /** Lee el contenido del fichero y lo devuelve en un ArrayList por cada puntuación */
    public void leerFicherPuntuaciones(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("puntuacion.txt")));
            String linea;
            while ((linea = reader.readLine()) != null)
                puntuaciones.add(linea);
            reader.close();
        }
        catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }

    /** Muestra en la actividad el contenido del ArrayList con las puntuaciones
      * y las ordena de mas reciente a mas antiguo */
    public void imprimirPuntuaciones(){
        String mostrarPuntuaciones = "";
        int tamPunt;

        if (puntuaciones.size() < 5) {
            tamPunt = puntuaciones.size();
        } else {
            tamPunt = 4;
        }
        // Da la vuelta al ArrayList para coger primero las ultimas puntuaciones
        Collections.reverse(puntuaciones);
        for (int x = 0; x < tamPunt; x++)
            mostrarPuntuaciones += puntuaciones.get(x) + "\n";
        textPuntuaciones.setText(mostrarPuntuaciones);
    }

    /** Vuelve a la aplicacion con un Bundle con la informacion del nombre del usuario */
    public void onClickVolver(View view){
        Intent intencion = new Intent(RegisterUsers.this, MainActivity.class);
        Bundle datos = new Bundle();
        datos.putString("userName", name.getText().toString());
        intencion.putExtras(datos);
        setResult(RESULT_OK, intencion);
        finish();
    }
}
