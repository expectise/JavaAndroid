package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.multiventas.app.databinding.ActivityPaisesAdminBinding;

public class PaisesAdminActivity extends AppCompatActivity {
    private ActivityPaisesAdminBinding binding;
    private AdminViewModel viewModel;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPaisesAdminBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);

        Bundle extras = getIntent().getExtras();

        if (extras != null)
            this.jwt = extras.getString("jwt");


        PaisesAdapter adapter = new PaisesAdapter();

        adapter.setOnItemClickListener(pais->{

            Intent intent = new Intent(this, PaisAdminActivity.class);
            intent.putExtra("jwt", this.jwt);
            intent.putExtra("paises", pais);
            startActivity(intent);

        });
        binding.paisesRecycler.setLayoutManager(new LinearLayoutManager(this));

        binding.paisesRecycler.setAdapter(adapter);

        binding.paisSearcg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String texto = binding.paisSearcg.getText().toString();
                    if (texto.length() > 0) {
                        viewModel.filterByPais(texto);
                    }else{
                        viewModel.selectAllPaises();
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

        binding.paisAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaisAdminActivity.class);
            intent.putExtra("jwt", this.jwt);
            startActivity(intent);
        });

        viewModel.getPaises().observe(this, paises -> {
            adapter.submitList(paises);
        });

        viewModel.consultarPaises(this.jwt);
    }
}