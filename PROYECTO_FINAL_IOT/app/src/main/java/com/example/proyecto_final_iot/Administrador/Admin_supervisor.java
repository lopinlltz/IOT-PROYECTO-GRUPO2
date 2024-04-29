package com.example.proyecto_final_iot.Administrador;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_supervisor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_supervisor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = findViewById(R.id.lista_supervisor);
        ArrayList<String> arrayList = getList();
        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<>(Admin_supervisor.this , android.R.layout.select_dialog_singlechoice, arrayList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String items = (String) adapterView.getItemAtPosition(1);
                Toast.makeText(Admin_supervisor.this, "Escogiste a "+items+" como Supervisor para este Sitio" , Toast.LENGTH_SHORT).show();

            }
        });

    }

    private ArrayList<String> getList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=1 ; i<=10; i++){
            arrayList.add("Supervisor " + i);
        }

        return arrayList;
    }
}