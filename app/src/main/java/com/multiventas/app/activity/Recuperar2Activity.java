package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.multiventas.app.databinding.ActivityRecuperar1Binding;
import com.multiventas.app.databinding.ActivityRecuperar2Binding;

public class Recuperar2Activity extends AppCompatActivity {
    private ActivityRecuperar2Binding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperar2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        viewModel = new ViewModelProvider(this, new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        binding.siguiente.setOnClickListener(v ->{
            String clave = binding.confirmacion.getText().toString();


            if (clave.length() == 6 && extras != null){
                viewModel.recupera2(extras.getString("email"), clave);
            }
        });

        binding.regresar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


        viewModel.getRecupera2().observe(this, v -> {
            if (v.equals(0)){
                binding.progressBar.setVisibility(View.GONE);
            }

            if (v.equals(1)){
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            if (v.equals(2)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this,"Ocurrió un error, inténtalo más tarde", Toast.LENGTH_LONG).show();
            }

            if (v.equals(3)){
                binding.progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(this, Recuperar3Activity.class);
                startActivity(intent);
            }

            if (v.equals(4)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this,"La clave es incorrecta", Toast.LENGTH_LONG).show();
            }

            if (v.equals(5)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this,"Error con tu conexión a internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}