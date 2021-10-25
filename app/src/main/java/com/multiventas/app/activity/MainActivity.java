package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Validator;
import com.multiventas.app.databinding.ActivityMainBinding;
import com.multiventas.app.dto.Login;
import com.multiventas.app.entity.Credentials;
import com.multiventas.app.entity.Usuario;

public class MainActivity extends AppCompatActivity {
    private Validator validator;

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        viewModel.nuleaLoginSuccess();

        binding.btnregistrate.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.recordar.setOnClickListener(v -> {
            Intent intent = new Intent(this, Recuperar1Activity.class);
            startActivity(intent);
        });

        initView();



    }

    private void initView(){

        viewModel.getUsuario().observe(this, usuario -> {
            if (usuario != null){
                Login login = new Login(usuario.getEmail(), usuario.getContrasena());
                viewModel.login(login);
            }
        });




        viewModel.getLoginSuccess().observe(this, id -> {
            if (id.equals(0)){
                binding.progressBar.setVisibility(View.GONE);
            }

            if (id.equals(1)){
                binding.progressBar.setVisibility(View.VISIBLE);
            }
            if (id.equals(2)){
                Toast.makeText(this, "E-mail o contraseña inválidos", Toast.LENGTH_LONG).show();
                viewModel.nuleaLoginSuccess();
            }

            if (id.equals(4)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Error con tu conexión a internet", Toast.LENGTH_LONG).show();
            }
        });

            viewModel.getCredentials().observe(this, credentials -> {
                if (credentials != null && viewModel.getLoginSuccess().getValue().equals(3)){
                    if (credentials.getRole().equals("Preconfirmado")){
                        Intent intent = new Intent(this, ConfirmaActivity.class);
                        startActivity(intent);
                    }

                    if (credentials.getRole().equals("Usuario")){
                        Intent intent = new Intent(this, LandingUsuarioActivity.class);
                        startActivity(intent);
                    }

                    if (credentials.getRole().equals("Admin")){
                        Intent intent = new Intent(this, LandingAdminActivity.class);
                        startActivity(intent);
                    }
                }
            });


        binding.loginButton.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String contrasena = binding.contrasena.getText().toString();

            if (email.length() > 5 && contrasena.length() > 5){
                Usuario usuarioNew = new Usuario(1, email, contrasena);
                viewModel.guardaUsuarioDB(usuarioNew);
            }
        });
    }
}