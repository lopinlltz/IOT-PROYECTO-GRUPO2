package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.proyecto_final_iot.R;
import com.google.firebase.database.core.view.View;


public class swich_on_off extends AppCompatActivity {

    TextView txtEti;
    SwitchCompat  switchCompat;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_usuario_item);
        txtEti = findViewById(R.id.etiSeleccion);
        switchCompat = findViewById(R.id.idSwitch_usuario_admin);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    txtEti.setText("Activo");
                }else{
                    txtEti.setText("Desactivo");
                }
            }
        });
    }


}
