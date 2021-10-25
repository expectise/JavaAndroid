package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.multiventas.app.databinding.ActivityLandingUsuarioBinding;
import com.multiventas.app.databinding.ActivityMainBinding;

public class LandingUsuarioActivity extends AppCompatActivity {
    private ActivityLandingUsuarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}