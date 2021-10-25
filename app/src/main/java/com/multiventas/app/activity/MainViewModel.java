package com.multiventas.app.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.multiventas.app.database.VentasDatabase;
import com.multiventas.app.dto.Login;
import com.multiventas.app.dto.Register;
import com.multiventas.app.entity.Credentials;
import com.multiventas.app.entity.Usuario;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository repository;

    public LiveData<Integer> getUsuarioCreado() {
        return this.repository.getUsuarioCreado();
    }

    public LiveData<Integer> getUsuarioExiste() {
        return this.repository.getUsuarioExiste();
    }

    public LiveData<Usuario> getUsuario(){ return this.repository.getUsuario(); }

    public LiveData<Integer> getLoginSuccess() { return this.repository.getLoginSuccess(); }

    public LiveData<Integer> getRecupera1() { return this.repository.getRecupera1(); }

    public LiveData<Integer> getRecupera2() { return this.repository.getRecupera2(); }

    public void nuleaUsuarioCreado(){
        this.repository.nuleaUsuarioCreado();
    }

    public void nuleaUsuarioExiste(){
        this.repository.nuleaUsuarioExiste();
    }

    public void nuleaLoginSuccess() { this.repository.nuleaLoginSuccess(); }

    public LiveData<Credentials> getCredentials() { return this.repository.getCredentials(); }

    public LiveData<Integer> getUsuarioConfirmado() { return this.repository.getUsuarioConfirmado(); }

    public void recupera1(String email) { this.repository.recupera1(email); }

    public void recupera2(String email, String clave) { this.repository.recuperar2(email, clave); }



    public MainViewModel(@NonNull Application application) {
        super(application);
        VentasDatabase database = VentasDatabase.getDatabase(application);
        this.repository = new MainRepository(database);
    }

    public void crearUsuario(Register register){
        repository.crearUsuario(register);
    }

    public void usuarioExiste(String email){ repository.usuarioExiste(email); }

    public void login(Login login){
        repository.login(login);
    }

    public void guardaUsuarioDB(Usuario usuario){
        repository.guardaUsuarioDB(usuario);
    }

    public void confirmaEmail(String clave, String jwt) { repository.confirmaEmail(clave, jwt); }
}
