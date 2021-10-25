package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.multiventas.app.R;
import com.multiventas.app.databinding.ActivityRegisterBinding;
import com.multiventas.app.databinding.ActivityRegisterSuccessBinding;

public class RegisterSuccess extends AppCompatActivity {
    private ActivityRegisterSuccessBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.salir.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }
}