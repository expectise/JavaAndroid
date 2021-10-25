package com.multiventas.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.multiventas.app.databinding.ActivityAdminPujaactivaBinding;
import com.multiventas.app.dto.PujaActivaOverview;
import com.multiventas.app.entity.Usuarios;
import com.multiventas.app.entity.Ventadetallada;
import com.multiventas.app.entity.VentasAndVentadetallada;

import java.util.ArrayList;
import java.util.List;

public class AdminPujaactivaActivity extends AppCompatActivity {
    private ActivityAdminPujaactivaBinding binding;
    private AdminViewModel adminviewModel;
    private PujasAdminViewModel pujasViewModel;
    private String jwt;
    private List<Usuarios> usuarios;
    private List<VentasAndVentadetallada> ventas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPujaactivaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            this.jwt = extras.getString("jwt");

        adminviewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);
        pujasViewModel = new ViewModelProvider(this, new PujasAdminViewModelFactory(getApplication())).get(PujasAdminViewModel.class);

        pujasViewModel.getPujaAbierta(this.jwt);

        binding.pujaactivaRecycler.setLayoutManager(new LinearLayoutManager(this));

        PujaactivaAdapter adapter = new PujaactivaAdapter();

        binding.articulo.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminProductoSelectActivity.class);
            intent.putExtra("jwt", this.jwt);
            startActivity(intent);
        });

        binding.pujaactivaRecycler.setAdapter(adapter);

        pujasViewModel.getVentasDB().observe(this, v -> {
            this.ventas = v;


            List<PujaActivaOverview> list = new ArrayList<>();

            for (VentasAndVentadetallada ventasV : v){
                for (Ventadetallada detalle : ventasV.getVentadetallada()){

                        for(Usuarios usuarios : this.usuarios){
                            if (usuarios.getIdUsuarios().equals(ventasV.getVentas().getIdUsuarios())){
                                PujaActivaOverview out = new PujaActivaOverview(
                                        detalle.getIdVentaDetallada(),
                                        usuarios.getNickname(),
                                        detalle.getTitulo(),
                                        detalle.getCodigoProducto());
                                list.add(out);
                            }
                        }
                }
            }

            adapter.submitList(list);
        });

        adapter.setOnItemClickListener(v ->{
            this.popUp(v.getTituloProducto(), v.getNickname(), v.getIdVentaDetallada());
        });

        adminviewModel.insertarUsuarios(this.jwt);


        pujasViewModel.getVentasDB().observe(this, v -> {
            this.ventas = v;
        });

        adminviewModel.getUsuariosDB().observe(this, v -> {
            this.usuarios = v;
        });

        adminviewModel.getLogonValid().observe(this, v -> {
                if (!v){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        });

        pujasViewModel.getDesasignado().observe(this, v->{
            if (v){
                pujasViewModel.getPujaAbierta(this.jwt);
            }
        });

        adminviewModel.selectAllUsuariosConfAndAct();

        pujasViewModel.getLogonValid().observe(this, v ->{
            if (!v){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void popUp(String titulo, String nickName, Long id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Desea Eliminar el producto " + titulo + " con el nickName " + nickName + "?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pujasViewModel.desasignarProducto(id, jwt);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}