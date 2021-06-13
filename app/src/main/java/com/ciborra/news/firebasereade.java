package com.ciborra.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class firebasereade extends AppCompatActivity {
List <data> fetchdata;
RecyclerView recyclerView;
helperadapter Helper;
DatabaseReference databaseReference;
EditText name, url;
Button añadir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasereade);
        recyclerView= findViewById(R.id.Recicler);
        BottomNavigationView bnvBotonera = null;
        name = findViewById(R.id.names);
        url = findViewById(R.id.urls);
        añadir = findViewById(R.id.button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bnvBotonera = findViewById(R.id.bnvBotoneraVistaMaps);
        fetchdata = new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("recyclerview");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    data database= ds.getValue(data.class);
                    fetchdata.add(database);

                }

                Helper = new helperadapter(fetchdata);
                recyclerView.setAdapter(Helper);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString();
                String urls = url.getText().toString();
                Map<String, Object> datos = new HashMap();
                datos.put("name", names);
                datos.put("url", urls);
                databaseReference.push().setValue(datos);
            }
        });
        bnvBotonera.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getTitle().equals("Diaris")) {
                    Intent intent = new Intent(firebasereade.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });
    }
}