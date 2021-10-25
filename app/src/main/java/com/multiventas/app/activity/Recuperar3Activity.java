package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.multiventas.app.databinding.ActivityRecuperar2Binding;
import com.multiventas.app.databinding.ActivityRecuperar3Binding;

public class Recuperar3Activity extends AppCompatActivity {
    private ActivityRecuperar3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperar3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.salir.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}