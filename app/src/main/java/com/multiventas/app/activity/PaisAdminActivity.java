package com.multiventas.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.multiventas.app.databinding.ActivityPaisAdminBinding;
import com.multiventas.app.dto.Pais;
import com.multiventas.app.entity.Paises;
import com.multiventas.app.utils.Converters;

import java.util.List;

public class PaisAdminActivity extends AppCompatActivity implements Validator.ValidationListener  {
    private AdminViewModel viewModel;
    private ActivityPaisAdminBinding binding;
    private String jwt;
    private Validator validator;
    private Paises paises;



    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 3, max = 45,  message = "Debe ser de 3 a 45 caracteres")
    private EditText pais;

    @NotEmpty(message = "No puede estar vacío")
    @Pattern(regex = "^[0-9]{0,8}[.]{0,1}?[0-9]{0,2}$", message = "Debe ser un dígito decimal")
    private EditText valorEnvio;

    @NotEmpty(message = "No puede estar vacío")
    @Pattern(regex = "^[0-9]{0,8}[.]{0,1}?[0-9]{0,2}$", message = "Debe ser un dígito decimal")
    private EditText precioExcento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaisAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            this.jwt = extras.getString("jwt");
            this.paises = (Paises) extras.getParcelable("paises");
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        initView();
    }

    private void initView() {
        pais = binding.pais;
        valorEnvio = binding.valorEnvio;
        precioExcento = binding.precioExcento;


        if (this.paises != null){
            binding.eliminar.setVisibility(View.VISIBLE);
            binding.creado.setVisibility(View.VISIBLE);
            binding.actualizado.setVisibility(View.VISIBLE);

            binding.pais.setText(this.paises.getPais());
            binding.valorEnvio.setText(String.valueOf(this.paises.getValorEnvio()));
            binding.precioExcento.setText(String.valueOf(this.paises.getPrecioExcento()));

            if (this.paises.getCreado() != null)
                binding.creado.setText("Creado: " + Converters.fromTimestamp(this.paises.getCreado()));

            if (this.paises.getActualizado() != null)
                binding.actualizado.setText("Actualizado: " + Converters.fromTimestamp(this.paises.getActualizado()));
        }else{
            binding.eliminar.setVisibility(View.GONE);
            binding.creado.setVisibility(View.GONE);
            binding.actualizado.setVisibility(View.GONE);
        }



        binding.guardar.setOnClickListener(v->{
            validator.validate();
        });




        viewModel.getPaisCreado().observe(this, paisCreado -> {
            if (paisCreado .equals(1)){
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            if (paisCreado .equals(0)){
                binding.progressBar.setVisibility(View.GONE);
            }



            if (paisCreado .equals(3)){
                binding.progressBar.setVisibility(View.GONE);
                viewModel.nuleaProductoCreado();
                Intent intent = new Intent(this, PaisesAdminActivity.class);
                intent.putExtra("jwt", this.jwt);
                startActivity(intent);
            }

            if (paisCreado .equals(4)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "País Creado/Modificado", Toast.LENGTH_LONG).show();
            }

            if (paisCreado .equals(5)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Error con tu conexión a internet", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getLogonValid().observe(this,v -> {
            if (!v){
                binding.progressBar.setVisibility(View.GONE);
                viewModel.nuleaPaisCreado();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.eliminar.setOnClickListener(v -> {
            this.popUp();
        });
    }


    @Override
    public void onValidationSucceeded() {
        Pais paisIn = new Pais(null,
                pais.getText().toString(),
                new Double(valorEnvio.getText().toString()),
                new Double(precioExcento.getText().toString()));

        if (this.paises != null){
            paisIn.setIdPais(this.paises.getIdPais());
            viewModel.actualizaPais(this.jwt, paisIn);
        }else {
            viewModel.guardarPais(this.jwt, paisIn);
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this, "Error de validación, por favor verifica que los datos ingresados sean correctos", Toast.LENGTH_SHORT).show();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


    private void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Desea Eliminar?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.borrarPais(jwt, paises.getIdPais());
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