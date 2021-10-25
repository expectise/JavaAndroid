package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.multiventas.app.databinding.ActivityAdminPujaSelectBinding;

public class AdminPujaSelectActivity extends AppCompatActivity {
    private ActivityAdminPujaSelectBinding binding;
    private PujasAdminViewModel pujasViewModel;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminPujaSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            this.jwt = extras.getString("jwt");


        pujasViewModel = new ViewModelProvider(this, new PujasAdminViewModelFactory(getApplication())).get(PujasAdminViewModel.class);

        binding.pujasRecycler.setLayoutManager(new LinearLayoutManager(this));

        PujasAdapter adapter = new PujasAdapter();

        binding.pujasRecycler.setAdapter(adapter);

        pujasViewModel.getPujasBD().observe(this, pujas -> {
            adapter.submitList(pujas);
        });


        adapter.setOnItemClickListener(v -> {
            Intent intent = new Intent(this, AdminVentasSelectActivity.class);
            intent.putExtra("jwt", this.jwt);
            intent.putExtra("puja", v.getIdPujas());
            startActivity(intent);
        });


    }
}