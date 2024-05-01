package com.example.proyecto_final_iot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.HistorialSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.TipoEquipoSupervisorActivity;

public class BarraSuperior extends Fragment {

    private boolean isChatSelected = false;
    private boolean isPerfilSelected = false;

    private ImageButton imageButtonChat, imageButtonPerfil;
    private TextView textViewChat, textViewPerfil;
    private LinearLayout layoutChat, layoutPerfil;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.barra_superior, container, false);

        layoutChat = view.findViewById(R.id.layoutChat);
        layoutPerfil = view.findViewById(R.id.layoutPerfil);

        imageButtonChat = view.findViewById(R.id.imageButtonChat);
        imageButtonPerfil = view.findViewById(R.id.imageButtonPerfil);

        textViewChat = view.findViewById(R.id.textViewChat);
        textViewPerfil = view.findViewById(R.id.textViewPerfil);

        configurarListeners();

        return view;
    }

    private void configurarListeners() {
        imageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(true, false);
            }
        });

        imageButtonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, true);
            }
        });

    }

    private void handleOptionClick(boolean isChat, boolean isPerfil) {
        resetOtherOptions();

        if (isChat) {
            isChatSelected = true;
            layoutChat.setBackgroundColor(Color.parseColor("#3F2D40"));
            imageButtonChat.setScaleX(0.8f);
            imageButtonChat.setScaleY(0.8f);
            textViewChat.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), ChatContactoListaActivity.class);
            startActivity(intent);
        }

        if (isPerfil) {
            isPerfilSelected = true;
            layoutPerfil.setBackgroundColor(Color.parseColor("#3F2D40"));
            imageButtonPerfil.setScaleX(0.8f);
            imageButtonPerfil.setScaleY(0.8f);
            textViewPerfil.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            startActivity(intent);
        }


    }

    private void resetOtherOptions() {
        if (isChatSelected) {
            isChatSelected = false;
            layoutChat.setBackgroundColor(Color.TRANSPARENT);
            imageButtonChat.setScaleX(1.0f);
            imageButtonChat.setScaleY(1.0f);
            textViewChat.setVisibility(View.GONE);
        }

        if (isPerfilSelected) {
            isPerfilSelected = false;
            layoutPerfil.setBackgroundColor(Color.TRANSPARENT);
            imageButtonPerfil.setScaleX(1.0f);
            imageButtonPerfil.setScaleY(1.0f);
            textViewPerfil.setVisibility(View.GONE);
        }


    }


}
