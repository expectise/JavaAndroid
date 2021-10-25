package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.multiventas.app.databinding.ActivityAdminVentasSelectBinding;
import com.multiventas.app.dto.Venta;
import com.multiventas.app.entity.Usuarios;
import com.multiventas.app.entity.Ventadetallada;
import com.multiventas.app.entity.Ventas;

import java.util.ArrayList;
import java.util.List;

public class AdminVentasSelectActivity extends AppCompatActivity {
    private ActivityAdminVentasSelectBinding binding;
    private String jwt;
    private Long puja;
    private List<Ventas> ventas;
    private PujasAdminViewModel pujasViewModel;
    private AdminViewModel adminviewModel;
    private List<Usuarios> usuarios;
    private List<Ventadetallada> ventadetallada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminVentasSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.jwt = extras.getString("jwt");
            this.puja = extras.getLong("puja");
        }

        pujasViewModel.setVentasByPuja(this.puja);

        adminviewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);
        pujasViewModel = new ViewModelProvider(this, new PujasAdminViewModelFactory(getApplication())).get(PujasAdminViewModel.class);

        VentasAdapter adapter = new VentasAdapter();

        binding.ventasRecycler.setLayoutManager(new LinearLayoutManager(this));

        binding.ventasRecycler.setAdapter(adapter);

        adminviewModel.selectAllUsuariosConf();

        adminviewModel.getUsuariosDB().observe(this, v -> {
            this.usuarios = v;

                pujasViewModel.getVentasByPuja().observe(this, vr -> {
                    this.ventas = vr;

                    pujasViewModel.getVentadetalladaBD().observe(this, vr2 -> {
                        List<Venta> ventaList = new ArrayList<>();


                        for (Ventas ventas : this.ventas){

                            String nickname = "";
                            double precioTotal = 0;
                            double precioEnvio = 0;

                            for (Usuarios usuario : this.usuarios){
                                if (ventas.getIdUsuarios().equals(usuario.getIdUsuarios()))
                                    nickname = usuario.getNickname();
                            }

                            for (Ventadetallada detalle : this.ventadetallada){
                                if (detalle.getIdVentas().equals(ventas.getIdVentas()))
                                    precioTotal += detalle.getPrecio();
                            }


                            if (precioTotal < ventas.getPrecioExcento())
                            precioEnvio = ventas.getCostoEnvio();


                            Venta venta = new Venta(
                                    ventas.getIdVentas(),
                                    ventas.getIdUsuarios(),
                                    nickname,
                                    precioTotal,
                                    precioEnvio);


                            ventaList.add(venta);

                        }

                        adapter.submitList(ventaList);
                    });

                });
        });


        adapter.setOnItemClickListener(v->{
            Intent intent = new Intent(this, AdminVentaDetalladaActivity.class);
            intent.putExtra("jwt", this.jwt);
            intent.putExtra("puja", this.puja);
            intent.putExtra("venta", v.getIdVentas());
            intent.putExtra("usuario", v.getIdUsuarios());
            startActivity(intent);
        });

    }
}