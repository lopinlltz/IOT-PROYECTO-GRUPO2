package com.example.proyecto_final_iot.Administrador.Activity;

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

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.HistorialSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.TipoEquipoSupervisorActivity;

public class MenuBarFragmentAdministrador extends Fragment {

    private boolean isSitio_Selected = false;
    private boolean isUsuario_Selected = false;


    private ImageButton buttonSitio_Admin, buttonUsuario_Admin;
    private TextView textViewSitio_Admin, textViewUsuario_Admin;
    private LinearLayout linearLayoutSitio_Admin, layoutUsuario_admin;

    //Aplicando binding
    //private FragmentMenuBarBinding binding;
    @Nullable
    @Override
    /*public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMenuBarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        configurarListeners();
        return view;
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_bar_fragment_admin, container, false);

        linearLayoutSitio_Admin = view.findViewById(R.id.linearLayoutSitio_Admin);
        layoutUsuario_admin = view.findViewById(R.id.layoutUsuario_admin);


        buttonSitio_Admin = view.findViewById(R.id.buttonSitio_Admin);
        buttonUsuario_Admin = view.findViewById(R.id.buttonUsuario_Admin);


        textViewSitio_Admin = view.findViewById(R.id.textViewSitio_admin);
        textViewUsuario_Admin = view.findViewById(R.id.textViewUsuario_Admin);


        configurarListeners();

        return view;
    }

    private void configurarListeners() {
        buttonSitio_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(true, false);
            }
        });

        buttonUsuario_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, true);
            }
        });


    }

    private void handleOptionClick(boolean isSitio_Selected, boolean isUsuario_Selected) {
        resetOtherOptions();

        if (isSitio_Selected) {
            isSitio_Selected = true;
            linearLayoutSitio_Admin.setBackgroundColor(Color.parseColor("#3F2D40"));
            buttonSitio_Admin.setScaleX(0.8f);
            buttonSitio_Admin.setScaleY(0.8f);
            textViewSitio_Admin.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), Admin_lista_Sitio.class);
            startActivity(intent);
        }

        if (isUsuario_Selected) {
            isUsuario_Selected = true;
            layoutUsuario_admin.setBackgroundColor(Color.parseColor("#3F2D40"));
            buttonUsuario_Admin.setScaleX(0.8f);
            buttonUsuario_Admin.setScaleY(0.8f);
            textViewUsuario_Admin.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), Admin_lista_usuario.class);
            startActivity(intent);
        }




    }

    private void resetOtherOptions() {
        if (isSitio_Selected) {
            isSitio_Selected = false;
            linearLayoutSitio_Admin.setBackgroundColor(Color.TRANSPARENT);
            buttonSitio_Admin.setScaleX(1.0f);
            buttonSitio_Admin.setScaleY(1.0f);
            textViewSitio_Admin.setVisibility(View.GONE);
        }

        if (isUsuario_Selected) {
            isUsuario_Selected = false;
            layoutUsuario_admin.setBackgroundColor(Color.TRANSPARENT);
            buttonUsuario_Admin.setScaleX(1.0f);
            buttonUsuario_Admin.setScaleY(1.0f);
            textViewUsuario_Admin.setVisibility(View.GONE);
        }



    }
}
