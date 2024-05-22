package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Adapter.SitioAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.Administrador.Data.Usuario_data;
import com.example.proyecto_final_iot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Admin_lista_usuario extends AppCompatActivity {


    private UsuarioListAdapter adapter;
    private RecyclerView recyclerView;
    FloatingActionButton fab_user;
    private SearchView searchView;

    List<Usuario_data> data_List_user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lista_usuarios);

        searchView = findViewById(R.id.search_usuario);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView_lista_usuarios);
         data_List_user.add(new Usuario_data("maricielo supervisor 1"));
        data_List_user.add(new Usuario_data("Yoselin supervisor 2"));
        data_List_user.add(new Usuario_data("samantha supervisor 3"));
        data_List_user.add(new Usuario_data("Andrea supervisor 4"));
        data_List_user.add(new Usuario_data("Jose supervisor 5"));
        data_List_user.add(new Usuario_data("Andres supervisor 6"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsuarioListAdapter(data_List_user);
        recyclerView.setAdapter(adapter);

        fab_user = findViewById(R.id.fab_user);
        fab_user.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_usuario.this, Admin_nuevo_usuario.class);
                startActivity(intent);
            }
        });

        /*ImageProfileAdmin = findViewById(R.id.imageProfileAdmin);
        imageProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Usuario.this, Admin_perfil.class);
                startActivity(intent);
            }
        });

        ImageView imageChateAdmin = findViewById(R.id.imageChateAdmin);
        imageChateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, ChatActivity.class);
                startActivity(intent);
            }
        });*/
    }

    private void filterList(String text) {
        List<Usuario_data> filteredList = new ArrayList<>();
        for(Usuario_data item : data_List_user ){
            if (item.getNombreUsuarioAdmin().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "Supervisor no encontrado" , Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }


    }


}