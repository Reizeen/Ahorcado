package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterUsers extends AppCompatActivity {

    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        name = findViewById(R.id.idRegisterUser);
    }

    public void onClickVolver(View view){
        Intent intencion = new Intent(RegisterUsers.this, MainActivity.class);
        if (!name.getText().toString().isEmpty()){
            Bundle datos = new Bundle();
            datos.putString("user", name.getText().toString());
            intencion.putExtras(datos);
        }
        startActivity(intencion);
    }
}
