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
import com.multiventas.app.databinding.ActivityArticuloAdminBinding;
import com.multiventas.app.dto.Producto;
import com.multiventas.app.entity.Productos;
import com.multiventas.app.utils.Converters;

import java.util.List;

public class ArticuloAdminActivity extends AppCompatActivity implements Validator.ValidationListener {
    private AdminViewModel viewModel;
    private ActivityArticuloAdminBinding binding;
    private String jwt;

    private Productos productos;



    private Validator validator;

    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 5, max = 50,  message = "Debe ser de 5 a 50 caracteres")
    private EditText titulo;

    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 5, max = 500,  message = "Debe ser de 5 a 500 caracteres")
    private EditText descripcion;

    @NotEmpty(message = "No puede estar vacío")
    @Pattern(regex = "^[a-zA-Z0-9]{6,6}$", message = "Deben ser 6 números y letras sin carácteres especiales o ñ")
    private EditText codigoProducto;

    @NotEmpty(message = "No puede estar vacío")
    @Pattern(regex = "^[0-9]{0,8}[.]{0,1}?[0-9]{0,2}$", message = "Debe ser un dígito decimal")
    private EditText precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticuloAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new AdminViewModelFactory(getApplication())).get(AdminViewModel.class);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            this.jwt = extras.getString("jwt");
            this.productos = (Productos) extras.getParcelable("producto");
        }

        viewModel.consultaProductos(this.jwt);

        validator = new Validator(this);
        validator.setValidationListener(this);

        initView();

    }

    private void initView() {

    titulo = binding.titulo;
    descripcion = binding.descripcion;
    precio = binding.precio;
    codigoProducto = binding.codigoProducto;

    if (this.productos != null){
        binding.titulo.setText(this.productos.getTitulo());
        binding.descripcion.setText(this.productos.getDescripcion());
        binding.precio.setText(String.valueOf(this.productos.getPrecio()));
        binding.codigoProducto.setText(this.productos.getCodigoProducto());
    }


    binding.guardar.setOnClickListener(v->{
        validator.validate();
    });


        viewModel.getProductoCreado().observe(this, productoCreado -> {
            if (productoCreado.equals(1)){
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            if (productoCreado.equals(0)){
                binding.progressBar.setVisibility(View.GONE);
            }



            if (productoCreado.equals(3)){
                binding.progressBar.setVisibility(View.GONE);
                viewModel.nuleaProductoCreado();
                Intent intent = new Intent(this, ArticulosAdminActivity.class);
                intent.putExtra("jwt", this.jwt);
                startActivity(intent);
            }

            if (productoCreado.equals(4)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Artítulo Creado/Modificado", Toast.LENGTH_LONG).show();
            }

            if (productoCreado.equals(5)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Error con tu conexión a internet", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getLogonValid().observe(this,v -> {
            if (!v){
                binding.progressBar.setVisibility(View.GONE);
                viewModel.nuleaProductoCreado();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (this.productos != null){
            binding.eliminar.setVisibility(View.VISIBLE);
            binding.creado.setVisibility(View.VISIBLE);
            binding.actualizado.setVisibility(View.VISIBLE);

            if (this.productos.getCreado() != null)
            binding.creado.setText("Creado: " + Converters.fromTimestamp(this.productos.getCreado()));

            if (this.productos.getActualizado() != null)
            binding.actualizado.setText("Actualizado: " + Converters.fromTimestamp(this.productos.getActualizado()));
        }else{
            binding.eliminar.setVisibility(View.GONE);
            binding.creado.setVisibility(View.GONE);
            binding.actualizado.setVisibility(View.GONE);
        }

        binding.eliminar.setOnClickListener(v->{
            this.popUp();
        });

    }


    private void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Desea Eliminar?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.borrarProducto(jwt, productos.getIdProducto());
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


    @Override
    public void onValidationSucceeded() {


        Producto producto = new Producto(null,
                titulo.getText().toString(),
                descripcion.getText().toString(),
                new Double(precio.getText().toString()),
                codigoProducto.getText().toString());

        if (this.productos != null){
            producto.setIdProducto(this.productos.getIdProducto());
            viewModel.actualizaProducto(producto, this.jwt);
        }else {
            viewModel.guardarProducto(producto, this.jwt);
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


}