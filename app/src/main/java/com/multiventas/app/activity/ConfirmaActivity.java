package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.multiventas.app.databinding.ActivityConfirmaBinding;

public class ConfirmaActivity extends AppCompatActivity {
    private ActivityConfirmaBinding binding;
    private MainViewModel viewModel;

    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityConfirmaBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());



        viewModel = new ViewModelProvider(this, new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        viewModel.getCredentials().observe(this,credentials -> {
            this.jwt = "Bearer " + credentials.getJwt();
        });

        binding.confirmar.setOnClickListener(v ->{
            String clave = binding.confirmacion.getText().toString();
            if (clave.length() == 4){
                viewModel.confirmaEmail(clave, jwt);
            }
        });



        viewModel.getUsuarioConfirmado().observe(this, v -> {
            if (v.equals(2) || v.equals(3)){
                binding.progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else if(v.equals(4)) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Problemas con tu conexión a internet", Toast.LENGTH_LONG).show();
            }else if(v.equals(1)) {
                binding.progressBar.setVisibility(View.VISIBLE);
            }else{
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}