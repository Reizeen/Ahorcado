package com.example.ahorcado.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ahorcado.R;

public class RegisterUsers extends AppCompatActivity {

    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        name = findViewById(R.id.editNameUser);

        Bundle datos = this.getIntent().getExtras();
        name.setText(datos.getString("userName"));
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
