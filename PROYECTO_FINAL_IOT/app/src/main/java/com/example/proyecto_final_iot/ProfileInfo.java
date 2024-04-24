package com.example.proyecto_final_iot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        //fragmento de barra de menu uu
        MenuBarFragment menuBarFragment = new MenuBarFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerMenu, menuBarFragment)
                .commit();
    }
}
