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

public class RegisterUsers extends AppCompatActivity {

    private EditText name;
    private TextView textPuntuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        name = findViewById(R.id.editNameUser);
        textPuntuaciones = findViewById(R.id.idTextPuntuaciones);

        Bundle datos = this.getIntent().getExtras();
        name.setText(datos.getString("userName"));

        imprimirPuntuaciones(leerFicherPuntuaciones());
    }

    public ArrayList<String> leerFicherPuntuaciones(){
        ArrayList<String> puntuaciones = new ArrayList<>();
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
        return puntuaciones;
    }

    public void imprimirPuntuaciones(ArrayList<String> puntuaciones){
        String mostrarPuntuaciones = "";
        for(int x = 1; x < 5; x++)
            mostrarPuntuaciones += puntuaciones.get(puntuaciones.size()-x) + "\n";
        textPuntuaciones.setText(mostrarPuntuaciones);
    }

    public void onClickVolver(View view){
        Intent intencion = new Intent(RegisterUsers.this, MainActivity.class);
        Bundle datos = new Bundle();
        datos.putString("userName", name.getText().toString());
        intencion.putExtras(datos);
        setResult(RESULT_OK, intencion);
        finish();
    }
}
