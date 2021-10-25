package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.multiventas.app.databinding.ActivityAdminProductoSelectBinding;
import com.multiventas.app.entity.Usuarios;

import java.util.List;

public class AdminProductoSelectActivity extends AppCompatActivity {
    private ActivityAdminProductoSelectBinding binding;
    private AdminViewModel viewModel;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminProductoSelectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            this.jwt = extras.getString("jwt");

        viewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);


        binding.productosRecycler.setLayoutManager(new LinearLayoutManager(this));

        ProductosAdapter adapter = new ProductosAdapter();

        adapter.setOnItemClickListener(producto->{
            Intent intent = new Intent(this, AdminUsuarioSelectActivity.class);
            intent.putExtra("jwt", this.jwt);
            intent.putExtra("producto", producto);
            startActivity(intent);
        });

        binding.productosRecycler.setAdapter(adapter);

        viewModel.getProductos().observe(this, productos -> {
            adapter.submitList(productos);
        });

        viewModel.consultaProductos(this.jwt);

        viewModel.selectAllProductos();

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