package com.example.proyecto_iot.Supervisor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_iot.ChatAdapter;
import com.example.proyecto_iot.ChatItem;
import com.example.proyecto_iot.R;

import java.util.ArrayList;
import java.util.List;

public class ChatSupervisor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_chat);


    }
}
