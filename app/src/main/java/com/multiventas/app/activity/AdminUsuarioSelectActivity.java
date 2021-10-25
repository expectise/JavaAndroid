package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;


import com.multiventas.app.databinding.ActivityAdminUsuarioSelectBinding;
import com.multiventas.app.dto.AsignarProducto;
import com.multiventas.app.entity.Productos;

public class AdminUsuarioSelectActivity extends AppCompatActivity {
    private ActivityAdminUsuarioSelectBinding binding;
    private AdminViewModel viewModel;
    private String jwt;
    private Productos productos;
    private PujasAdminViewModel pujasViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminUsuarioSelectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.jwt = extras.getString("jwt");
            this.productos = (Productos) extras.getParcelable("producto");
        }

        viewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);
        pujasViewModel = new ViewModelProvider(this, new PujasAdminViewModelFactory(getApplication())).get(PujasAdminViewModel.class);

        binding.usuariosRecycler.setLayoutManager(new LinearLayoutManager(this));

        UsuariosAdapter adapter = new UsuariosAdapter();

        adapter.setOnItemClickListener(usuario->{

            AsignarProducto asignarProducto = new AsignarProducto(this.productos.getIdProducto(), usuario.getIdUsuarios());
            pujasViewModel.asignarProducto(asignarProducto, this.jwt);

        });

        binding.usuariosRecycler.setAdapter(adapter);

        viewModel.getUsuariosDB().observe(this, usuarios-> {
            adapter.submitList(usuarios);
        });

        viewModel.insertarUsuarios(this.jwt);

        pujasViewModel.getProductoAsignado().observe(this, v->{
            if (v){
                Intent intent = new Intent(this, AdminPujaactivaActivity.class);
                intent.putExtra("jwt", this.jwt);
                startActivity(intent);
            }
        });

        viewModel.selectAllUsuariosConfAndAct();

        viewModel.getLogonValid().observe(this, v -> {
            if (!v){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        pujasViewModel.getLogonValid().observe(this, v ->{
            if (!v){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });


        binding.nickNameSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String texto = binding.nickNameSearch.getText().toString();
                    if (texto.length() > 0) {
                        viewModel.filterByUsuariosConfAndAct(texto);
                    }else{
                        viewModel.selectAllUsuariosConfAndAct();
                    }

                    return true;
                }
                return false;
            }
        });

        viewModel.getLogonValid().observe(this,v -> {
            if (!v){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
