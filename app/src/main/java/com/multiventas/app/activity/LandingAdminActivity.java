package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.multiventas.app.databinding.ActivityLandingAdminBinding;

public class LandingAdminActivity extends AppCompatActivity {
    private ActivityLandingAdminBinding binding;
    private AdminViewModel viewModel;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);

        viewModel.getCredentials().observe(this,credentials -> {
           this.jwt = "Bearer " + credentials.getJwt();

        });

        binding.articulos.setOnClickListener(v -> {
            Intent intent = new Intent(this, ArticulosAdminActivity.class);
            intent.putExtra("jwt", this.jwt);
            startActivity(intent);
        });

        binding.paises.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaisesAdminActivity.class);
            intent.putExtra("jwt", this.jwt);
            startActivity(intent);
        });

        binding.pujas.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminPujasActivity.class);
            intent.putExtra("jwt", this.jwt);
            startActivity(intent);
        });




        viewModel.getLogonValid().observe(this,v -> {
            if (!v){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


}