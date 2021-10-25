package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.multiventas.app.databinding.ActivityAdminVentaDetalladaBinding;
import com.multiventas.app.entity.Usuarios;

public class AdminVentaDetalladaActivity extends AppCompatActivity {
    private ActivityAdminVentaDetalladaBinding binding;
    private PujasAdminViewModel pujasViewModel;
    private AdminViewModel adminviewModel;
    private String jwt;
    private Long puja;
    private Long venta;
    private Long usuario;
    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminVentaDetalladaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.jwt = extras.getString("jwt");
            this.puja = extras.getLong("puja");
            this.venta = extras.getLong("venta");
            this.usuario = extras.getLong("usuario");
        }

        adminviewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);
        pujasViewModel = new ViewModelProvider(this, new PujasAdminViewModelFactory(getApplication())).get(PujasAdminViewModel.class);

        VentadetalladaAdapter adapter = new VentadetalladaAdapter();

        binding.ventadetalladaRecycler.setLayoutManager(new LinearLayoutManager(this));

        binding.ventadetalladaRecycler.setAdapter(adapter);


        adminviewModel.selectUsuarioById(this.usuario);

        adminviewModel.getUsuarioDB().observe(this, usuario ->{
            this.usuarios = usuario;
        });

        pujasViewModel.selectVentadetalladaByIdVenta(this.venta);

        pujasViewModel.getDetalleDB().observe(this, detalle -> {
            adapter.submitList(detalle);
        });


    }
}