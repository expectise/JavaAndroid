package com.multiventas.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.multiventas.app.databinding.ActivityAdminPujasBinding;
import com.multiventas.app.dto.PujasNewDTO;
import com.multiventas.app.utils.Converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminPujasActivity extends AppCompatActivity {
    private ActivityAdminPujasBinding binding;
    private AdminViewModel adminviewModel;
    private PujasAdminViewModel pujasViewModel;
    private String jwt;
    private final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPujasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        adminviewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);
        pujasViewModel = new ViewModelProvider(this, new PujasAdminViewModelFactory(getApplication())).get(PujasAdminViewModel.class);


        Bundle extras = getIntent().getExtras();

        if (extras != null)
            this.jwt = extras.getString("jwt");


        adminviewModel.insertarUsuarios(jwt);



        pujasViewModel.processpujaAbierta(jwt);

        pujasViewModel.isPujaAbierta().observe(this, is ->{
            if (is){
                binding.Abrirpuja.setVisibility(View.GONE);
                binding.Cerrarpuja.setVisibility(View.VISIBLE);
                binding.Asignar.setVisibility(View.VISIBLE);
                binding.pujas.setVisibility(View.GONE);
                binding.tituloPuja.setVisibility(View.GONE);
                binding.fecPriAbo.setVisibility(View.GONE);
                binding.pujaIni.setVisibility(View.GONE);
                binding.pujaFin.setVisibility(View.GONE);

                //pujasViewModel.getPujaAbierta(this.jwt);
            }else{
                binding.Abrirpuja.setVisibility(View.VISIBLE);
                binding.Cerrarpuja.setVisibility(View.GONE);
                binding.Asignar.setVisibility(View.GONE);
                binding.pujas.setVisibility(View.VISIBLE);
                binding.tituloPuja.setVisibility(View.VISIBLE);
                binding.fecPriAbo.setVisibility(View.VISIBLE);
                binding.pujaIni.setVisibility(View.VISIBLE);
                binding.pujaFin.setVisibility(View.VISIBLE);

                //pujasViewModel.getPujas(this.jwt);
            }
        });

        adminviewModel.getLogonValid().observe(this, v -> {
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

        EditText fecPriAbo = binding.fecPriAbo;
        EditText pujaIni = binding.pujaIni;
        EditText pujaFin = binding.pujaFin;



        binding.Abrirpuja.setOnClickListener(v -> {
            String PriAbo = binding.fecPriAbo.getText().toString();
            try {
                Long PriAboC = Converters.fromDatetoTimeStampNotBeforeToday(PriAbo);
                if (PriAboC != null){
                    PujasNewDTO pujasNewDTO = new PujasNewDTO(PriAboC, binding.tituloPuja.getText().toString());
                    pujasViewModel.abrirPuja(this.jwt, pujasNewDTO);
                }else{
                    Toast.makeText(this, "Error, la fecha del primer abono no puede ser anterior o igual a la fecha actual", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        binding.Cerrarpuja.setOnClickListener(v -> {
            pujasViewModel.cerrarPuja(this.jwt);
        });

        binding.Asignar.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminPujaactivaActivity.class);
            intent.putExtra("jwt", this.jwt);
            startActivity(intent);
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel3();
            }

        };

        fecPriAbo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AdminPujasActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        pujaIni.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AdminPujasActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        pujaFin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AdminPujasActivity.this, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        binding.pujas.setOnClickListener(v->{
            if (binding.pujaIni.getText().toString().isEmpty() || binding.pujaFin.getText().toString().isEmpty()){
                Toast.makeText(this, "Error, las fechas a filtrar no pueden estar vacías", Toast.LENGTH_LONG).show();
            }else{
                String ini = binding.pujaIni.getText().toString();
                String fin = binding.pujaFin.getText().toString();

                try {
                    Long iniL = Converters.fromDatetoTimeStampNotAfterToday(ini);
                    Long finL = Converters.fromDatetoTimeStampNotAfterToday(fin);
                    if (iniL != null && finL != null){

                        pujasViewModel.abrirPuja(this.jwt, pujasNewDTO);
                    }else{
                        Toast.makeText(this, "Error, las fechas no pueden ser mayores a la fecha actual", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        binding.fecPriAbo.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        binding.pujaIni.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel3() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        binding.pujaFin.setText(sdf.format(myCalendar.getTime()));
    }
}