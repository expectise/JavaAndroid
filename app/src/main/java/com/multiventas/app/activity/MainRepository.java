package com.multiventas.app.activity;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.multiventas.app.api.VentasApiClient;
import com.multiventas.app.database.VentasDatabase;
import com.multiventas.app.dto.Login;
import com.multiventas.app.dto.LoginDetails;
import com.multiventas.app.dto.ProductoIn;
import com.multiventas.app.dto.Register;
import com.multiventas.app.entity.Credentials;
import com.multiventas.app.entity.Usuario;
import com.multiventas.app.utils.Converters;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private VentasDatabase database;

    private Executor mExecutor = Executors.newSingleThreadExecutor();



    public MainRepository(VentasDatabase database) {
        this.database = database;
    }
    public LiveData<Usuario> getUsuario(){
        return database.VentasDao().getUsuario();
    }

    public MainRepository() {
        this.usuarioCreado.setValue(0);
        this.usuarioExiste.setValue(0);
        this.loginSuccess.setValue(0);
        this.usuarioconfirmado.setValue(0);
        this.recupera1.setValue(0);
        this.recupera2.setValue(0);

    }

    private MutableLiveData<Integer> usuarioCreado = new MutableLiveData<>();


    private MutableLiveData<Integer> loginSuccess = new MutableLiveData<>();


    private MutableLiveData<Integer> usuarioExiste = new MutableLiveData<>();

    private MutableLiveData<Integer> usuarioconfirmado = new MutableLiveData<>();

    public LiveData<Integer> getUsuarioConfirmado() { return this.usuarioconfirmado; }

    private MutableLiveData<Integer> recupera1 = new MutableLiveData<>();

    private MutableLiveData<Integer> recupera2 = new MutableLiveData<>();



    public void guardaUsuarioDB(Usuario usuario){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarUsuario(usuario);
            }
        });
    }

    public void deleteUsuario(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().deleteUsuario();
            }
        });
    }

    public void deleteCredentials(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().deleteCredentials();
            }
        });
    }

    public void deleteProductos(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyProducto();
            }
        });
    }

    public void deletePaises(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyPaises();
            }
        });
    }




    private void borraTodo(){
        this.deleteCredentials();
        this.deleteProductos();
        this.deleteUsuario();
        this.deletePaises();
    }


    public void nuleaUsuarioCreado(){
        this.usuarioCreado.setValue(0);
    }

    public void nuleaUsuarioExiste(){
        this.usuarioExiste.setValue(0);
    }

    public LiveData<Integer> getUsuarioCreado() {
        return this.usuarioCreado;
    }

    public LiveData<Integer> getUsuarioExiste() {
        return this.usuarioExiste;
    }

    public LiveData<Integer> getRecupera1() { return this.recupera1; }

    public LiveData<Integer> getRecupera2() { return this.recupera2; }

    public LiveData<Integer> getLoginSuccess() { return this.loginSuccess; }

    public void nuleaLoginSuccess() { this.loginSuccess.setValue(0); }

    public LiveData<Credentials> getCredentials() { return database.VentasDao().getCredentials(); }



    public void crearUsuario(Register register){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        usuarioCreado.setValue(1);

        service.nuevoUsuario(register).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 201) {
                    usuarioCreado.setValue(2);
                }else{
                    usuarioCreado.setValue(3);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                usuarioCreado.setValue(3);
            }
        });

    }

    public void usuarioExiste(String email){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        usuarioExiste.setValue(1);

        service.usuarioExiste(email).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                usuarioExiste.setValue(2);

                if (response.body()){
                    usuarioExiste.setValue(3);
                }else{
                    usuarioExiste.setValue(4);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                usuarioExiste.setValue(5);
            }
        });
    }

    public void login(Login login){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        loginSuccess.setValue(1);
        service.login(login).enqueue(new Callback<LoginDetails>() {
            @Override
            public void onResponse(Call<LoginDetails> call, Response<LoginDetails> response) {
                if (response.code() == 401){
                    borraTodo();
                    loginSuccess.setValue(2);
                }else{
                    Credentials credentials = database.VentasDao().getCredentials().getValue();
                    Credentials credentialsNew;
                    if (credentials == null) {
                        credentialsNew = new Credentials(1, response.body().getBearer(), "", response.body().getRole());
                    }else{
                        credentialsNew = new Credentials(1, response.body().getBearer(), credentials.getFireToken(), response.body().getRole());
                    }

                    insertarCredentials(credentialsNew);
                    loginSuccess.setValue(3);
                }


            }

            @Override
            public void onFailure(Call<LoginDetails> call, Throwable t) {
                loginSuccess.setValue(4);
            }

        });
    }

    public void insertarCredentials(Credentials credentials){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarCredentials(credentials);
            }
        });
    }

    public void confirmaEmail(String clave, String jwt) {
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        usuarioconfirmado.setValue(1);

        service.confirmaEmail(clave, jwt).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 401){
                    usuarioconfirmado.setValue(2);
                }else{
                    usuarioconfirmado.setValue(3);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                usuarioconfirmado.setValue(4);
            }
        });

    }

    public void recupera1(String email){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        recupera1.setValue(1);

        service.solClaveContra(email).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() != 200){
                    recupera1.setValue(2);
                }else{
                    if (response.body()){
                        recupera1.setValue(3);
                    }else{
                        recupera1.setValue(4);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                recupera1.setValue(5);
            }
        });
    }

    public void recuperar2(String email, String clave){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        recupera2.setValue(1);

        service.reseteaContra(email, clave).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() != 200){
                    recupera2.setValue(2);
                }else{
                    Log.d("respuesta", response.body().toString());
                    if (response.body()){
                        recupera2.setValue(3);
                    }else{
                        recupera2.setValue(4);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                recupera2.setValue(5);
            }
        });
    }




}
