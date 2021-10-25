package com.multiventas.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.multiventas.app.databinding.ActivityRegisterBinding;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.multiventas.app.dto.Register;


import java.util.List;



public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    private ActivityRegisterBinding binding;
    private Context context;
    private FusedLocationProviderClient mFusedLocationClient;
    private int PERMISSION_ID = 44;
    private double latitude;
    private double longitude;

    @NotEmpty(message = "No puede estar vacío, debe ser el nombre como apareces en FaceBook")
    @Length(min = 3, max = 50,  message = "Debe ser de 3 a 30 caracteres")
    private EditText nickname;
    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 3, max = 50,  message = "Debe ser de 3 a 50 caracteres")
    private EditText nombres;

    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 3, max = 50,  message = "Debe ser de 3 a 50 caracteres")
    private EditText appaterno;

    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 3, max = 50,  message = "Debe ser de 3 a 50 caracteres")
    private EditText apmaterno;

    @NotEmpty(message = "No puede estar vacío")
    @Email(message = "No es un E-Mail válido")
    private EditText email;

    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 8, max = 20,  message = "Debe ser de 8 a 20 caracteres")
    private EditText contrasena;

    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 8, max = 20,  message = "Debe ser de 8 a 20 caracteres")
    private EditText contrasenaConfirm;

    @NotEmpty(message = "No puede estar vacío")
    @Length(min = 10, max = 14,  message = "Debe ser de 10 a 14 números")
    private EditText telefono;

    private Validator validator;

    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        initView();
        validator = new Validator(this);
        validator.setValidationListener(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
    }

    private void initView() {
        nickname = binding.nickname;
        nombres = binding.nombres;
        appaterno = binding.appaterno;
        apmaterno = binding.apmaterno;
        email = binding.email;
        contrasena = binding.contrasena;
        contrasenaConfirm = binding.contrasenaConfirm;
        telefono = binding.telefono;


        binding.registrarse.setOnClickListener(v -> {
            registrarse();
        });

        binding.regresar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        viewModel.getUsuarioCreado().observe(this, usuarioCreado -> {
            if (usuarioCreado.equals(1)){
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            if (usuarioCreado.equals(0)){
                binding.progressBar.setVisibility(View.GONE);
            }

            if (usuarioCreado.equals(2)){
                viewModel.nuleaUsuarioCreado();
                viewModel.nuleaUsuarioExiste();
                Intent intent = new Intent(this, RegisterSuccess.class);
                startActivity(intent);
            }

            if (usuarioCreado.equals(3)){
                Toast.makeText(this, "Error con tu conexión a internet o el captcha", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registrarse(){
        if (email.getText().length() > 0) {
            viewModel.usuarioExiste(email.getText().toString());

        }

        viewModel.getUsuarioExiste().observe(this, v -> {
            if (v.equals(1)){
                binding.progressBar.setVisibility(View.VISIBLE);
            }else if (v.equals(3)) {
                email.setError("El E-mail seleccionado ya existe");
                binding.progressBar.setVisibility(View.GONE);
            }else if(v.equals(4)){
                binding.progressBar.setVisibility(View.GONE);
                validator.validate();
            }else if(v.equals(5)){
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Error con tu conexión a internet", Toast.LENGTH_LONG).show();
            }else{
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onValidationSucceeded() {
        if (contrasena.getText().toString().equals(contrasenaConfirm.getText().toString())){

            SafetyNet.getClient(this).verifyWithRecaptcha("")
                    .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                        @Override
                        public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                            // Indicates communication with reCAPTCHA service was
                            // successful.
                            String userResponseToken = recaptchaTokenResponse.getTokenResult();
                            if (!userResponseToken.isEmpty()) {
                                // Validate the user response token using the
                                // reCAPTCHA siteverify API.


                                // method to get the location
                                getLastLocation();

                                Register register = new Register(nickname.getText().toString(),
                                        email.getText().toString(),
                                        contrasena.getText().toString(),
                                        contrasenaConfirm.getText().toString(),
                                        nombres.getText().toString(),
                                        appaterno.getText().toString(),
                                        apmaterno.getText().toString(),
                                        telefono.getText().toString(),
                                        userResponseToken,
                                        latitude,
                                        longitude);


                                viewModel.crearUsuario(register);

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof ApiException) {
                                // An error occurred when communicating with the
                                // reCAPTCHA service. Refer to the status code to
                                // handle the error appropriately.
                                ApiException apiException = (ApiException) e;
                                int statusCode = apiException.getStatusCode();
                                Log.e("Error", "Error: " + CommonStatusCodes
                                        .getStatusCodeString(statusCode));
                            } else {
                                // A different, unknown type of error occurred.
                                Log.e("Error", "Error: " + e.getMessage());
                            }
                        }
                    });


        }else{
            contrasena.setError("Las contraseñas no coinciden");
            contrasenaConfirm.setError("Las contraseñas no coinciden");
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

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}