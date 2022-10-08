package com.ladeus.empresa25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.Escenario,new SesionFragment()).commit();
    }
}