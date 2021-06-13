package com.ciborra.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.ciborra.news.LectorRss;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public String as ;
    BottomNavigationView bnvBotonera;
    RecyclerView recyclerView;
    List<data> fetchdata;
    helperadapter Helper;
    DatabaseReference databaseReference;
    Spinner spinner;
    String urlSelect="";
    LectorRss lectorRss;
    TextView a;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LectorRss lectorRss = new LectorRss(this, recyclerView);
        lectorRss.execute();
        bnvBotonera = findViewById(R.id.bnvBotoneraVistaMaps);
        spinner = findViewById(R.id.spinner);
        String url;
        a = findViewById(R.id.textView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        loada();

        bnvBotonera.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getTitle().equals("Introduir")) {
                    Intent intent = new Intent(MainActivity.this, firebasereade.class);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });


    }
    public void loada(){
        final List<data> dates = new ArrayList<>();

        databaseReference.child("recyclerview").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String id = ds.getKey();
                        String nombre=ds.child("name").getValue().toString();
                        String url= ds.child("url").getValue().toString();
                        dates.add(new data(id,nombre, url));

                    }
                    ArrayAdapter<data> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, dates);
                    spinner.setAdapter(arrayAdapter);
                   spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           urlSelect = parent.getItemAtPosition(position).toString();

                           String url = dates.get(position).getUrl();
                           a.setText(url);
                           as =dates.get(position).getUrl();


                       }

                       @Override
                       public void onNothingSelected(AdapterView<?> parent) {

                       }
                   });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}