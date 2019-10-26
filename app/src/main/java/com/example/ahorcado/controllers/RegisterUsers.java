package com.example.ahorcado.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ahorcado.R;

public class RegisterUsers extends AppCompatActivity {

    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        name = findViewById(R.id.editNameUser);
        cargarPreferencias();
    }

    public void onClickVolver(View view){
        guardarPreferencias();
        finish();
    }

    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        name.setText(preferences.getString("nameUser", "Desconocido"));
    }

    public void guardarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("infoApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nameUser", name.getText().toString());
        editor.commit();
    }
}
