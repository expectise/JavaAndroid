package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;


import com.multiventas.app.databinding.ActivityArticulosAdminBinding;


public class ArticulosAdminActivity extends AppCompatActivity {
    private AdminViewModel viewModel;
    private ActivityArticulosAdminBinding binding;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticulosAdminBinding.inflate(getLayoutInflater());
        setContentView(binding .getRoot());
        viewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);

        Bundle extras = getIntent().getExtras();

        if (extras != null)
            this.jwt = extras.getString("jwt");

        binding.productosRecycler.setLayoutManager(new LinearLayoutManager(this));

        ProductosAdapter adapter = new ProductosAdapter();

        adapter.setOnItemClickListener(producto->{
            Intent intent = new Intent(this, ArticuloAdminActivity.class);
            intent.putExtra("jwt", this.jwt);
            intent.putExtra("producto", producto);
            startActivity(intent);
        });

        binding.productosRecycler.setAdapter(adapter);

        binding.articulo.setOnClickListener(v -> {
            Intent intent = new Intent(this, ArticuloAdminActivity.class);
            intent.putExtra("jwt", this.jwt);
            startActivity(intent);
        });

        viewModel.getProductos().observe(this, productos -> {
            adapter.submitList(productos);
        });

        viewModel.consultaProductos(this.jwt);


        binding.cpSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String texto = binding.cpSearch.getText().toString();
                    if (texto.length() > 0) {
                        viewModel.filterByCProducto(texto);
                    }else{
                        viewModel.selectAllProductos();
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