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

public class MenuBarFragment extends Fragment {

    private boolean isSitioSelected = false;
    private boolean isTipoSelected = false;
    private boolean isEquipoSelected = false;
    private boolean isHistorialSelected = false;

    private ImageButton buttonSitio, buttonTipo, buttonEquipo, buttonHistorial;
    private TextView textViewSitio, textViewTipo, textViewEquipo, textViewHistorial;
    private LinearLayout linearLayoutSitio, linearLayoutTipo, linearLayoutEquipo, linearLayoutHistorial;

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
        View view = inflater.inflate(R.layout.menu_bar_fragment, container, false);

        linearLayoutSitio = view.findViewById(R.id.layoutSitio);
        linearLayoutTipo = view.findViewById(R.id.layoutTipo);
        linearLayoutEquipo = view.findViewById(R.id.layoutEquipo);
        linearLayoutHistorial = view.findViewById(R.id.layoutHistorial);

        buttonSitio = view.findViewById(R.id.imageButtonSitio);
        buttonTipo = view.findViewById(R.id.imageButtonTipo);
        buttonEquipo = view.findViewById(R.id.imageButtonEquipo);
        buttonHistorial = view.findViewById(R.id.imageButtonHistorial);

        textViewSitio = view.findViewById(R.id.textViewSitio);
        textViewTipo = view.findViewById(R.id.textViewTipo);
        textViewEquipo = view.findViewById(R.id.textViewEquipo);
        textViewHistorial = view.findViewById(R.id.textViewHistorial);

        configurarListeners();

        return view;
    }

    private void configurarListeners() {
        buttonSitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(true, false, false, false);
            }
        });

        buttonTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, true, false, false);
            }
        });

        buttonEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, false, true, false);
            }
        });

        buttonHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, false, false, true);
            }
        });

    }

    private void handleOptionClick(boolean isSitio, boolean isTipo, boolean isEquipo, boolean isHistorial) {
        resetOtherOptions();

        if (isSitio) {
            isSitioSelected = true;
            linearLayoutSitio.setBackgroundColor(Color.parseColor("#3F2D40"));
            buttonSitio.setScaleX(0.8f);
            buttonSitio.setScaleY(0.8f);
            textViewSitio.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), SitioSupervisorActivity.class);
            startActivity(intent);
        }

        if (isTipo) {
            isTipoSelected = true;
            linearLayoutTipo.setBackgroundColor(Color.parseColor("#3F2D40"));
            buttonTipo.setScaleX(0.8f);
            buttonTipo.setScaleY(0.8f);
            textViewTipo.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), TipoEquipoSupervisorActivity.class);
            startActivity(intent);
        }

        if (isEquipo) {
            isEquipoSelected = true;
            linearLayoutEquipo.setBackgroundColor(Color.parseColor("#3F2D40"));
            buttonEquipo.setScaleX(0.8f);
            buttonEquipo.setScaleY(0.8f);
            textViewEquipo.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), EquiposSupervisorActivity.class);
            startActivity(intent);
        }

        if (isHistorial) {
            isHistorialSelected = true;
            linearLayoutHistorial.setBackgroundColor(Color.parseColor("#3F2D40"));
            buttonHistorial.setScaleX(0.8f);
            buttonHistorial.setScaleY(0.8f);
            textViewHistorial.setVisibility(View.VISIBLE);

            Intent intent = new Intent(getActivity(), HistorialSupervisorActivity.class);
            startActivity(intent);
        }


    }

    private void resetOtherOptions() {
        if (isSitioSelected) {
            isSitioSelected = false;
            linearLayoutSitio.setBackgroundColor(Color.TRANSPARENT);
            buttonSitio.setScaleX(1.0f);
            buttonSitio.setScaleY(1.0f);
            textViewSitio.setVisibility(View.GONE);
        }

        if (isTipoSelected) {
            isTipoSelected = false;
            linearLayoutTipo.setBackgroundColor(Color.TRANSPARENT);
            buttonTipo.setScaleX(1.0f);
            buttonTipo.setScaleY(1.0f);
            textViewTipo.setVisibility(View.GONE);
        }

        if (isEquipoSelected) {
            isEquipoSelected = false;
            linearLayoutEquipo.setBackgroundColor(Color.TRANSPARENT);
            buttonEquipo.setScaleX(1.0f);
            buttonEquipo.setScaleY(1.0f);
            textViewEquipo.setVisibility(View.GONE);
        }

        if (isHistorialSelected) {
            isHistorialSelected = false;
            linearLayoutHistorial.setBackgroundColor(Color.TRANSPARENT);
            buttonHistorial.setScaleX(1.0f);
            buttonHistorial.setScaleY(1.0f);
            textViewHistorial.setVisibility(View.GONE);
        }


    }
}
