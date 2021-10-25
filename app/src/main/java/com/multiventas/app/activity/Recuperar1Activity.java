package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.multiventas.app.databinding.ActivityRecuperar1Binding;
import com.multiventas.app.databinding.ActivityRegisterSuccessBinding;

public class Recuperar1Activity extends AppCompatActivity {
    private ActivityRecuperar1Binding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperar1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(getApplication())).get(MainViewModel.class);


        binding.siguiente.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            if (email.length() > 5) {
                viewModel.recupera1(email);
            }
        });

        binding.regresar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        viewModel.getRecupera1().observe(this,v->{
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
                Intent intent = new Intent(this, Recuperar2Activity.class);
                intent.putExtra("email", binding.email.getText().toString());
                binding.progressBar.setVisibility(View.GONE);
                startActivity(intent);
            }

            if (v.equals(4)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this,"No existe el e-mail ingresado", Toast.LENGTH_LONG).show();
            }

            if (v.equals(5)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this,"Error con tu conexión a internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}