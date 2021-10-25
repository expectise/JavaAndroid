package com.multiventas.app.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.multiventas.app.database.VentasDatabase;
import com.multiventas.app.dto.Pais;
import com.multiventas.app.dto.Producto;
import com.multiventas.app.entity.Credentials;
import com.multiventas.app.entity.Paises;
import com.multiventas.app.entity.Productos;
import com.multiventas.app.entity.Usuarios;

import java.util.List;

public class AdminViewModel extends AndroidViewModel {
    private final AdminRepository repository;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        VentasDatabase database = VentasDatabase.getDatabase(application);
        this.repository = new AdminRepository(database);
    }

    public void selectAllProductos(){ this.repository.selectAllProductos(); }

    public void insertarUsuarios(String jwt) { this.repository.insertarUsuarios(jwt); }

    public void filterByCProducto(String cProducto){ this.repository.filterByCProducto(cProducto); }

    public LiveData<Credentials> getCredentials() { return this.repository.getCredentials(); }

    public LiveData<List<Productos>> getProductos() { return this.repository.getProductos(); }

    public  LiveData<Integer> getUsuarioStatus() { return this.repository.usuarioStatus; }

    public LiveData<Integer> getProductoCreado() { return this.repository.getProductoCreado(); }

    public void guardarProducto(Producto producto, String jwt) { this.repository.guardarProducto(producto, jwt); }

    public void nuleaProductoCreado(){ this.repository.nuleaProductoCreado(); }

    public void consultaProductos(String jwt){ this.repository.consultaProductos(jwt); }

    public LiveData<Boolean> getLogonValid() { return this.repository.getLogonValid(); }

    public void actualizaProducto(Producto producto, String jwt){ this.repository.actualizaProducto(producto, jwt); }

    public void borrarProducto(String jwt, Long id) { this.repository.borrarProducto(jwt, id); }

    public LiveData<Integer> getPaisCreado() { return this.repository.getPaisCreado(); }

    public LiveData<List<Paises>> getPaises() { return this.repository.getPaises(); }

    public void nuleaPaisCreado() { this.repository.nuleaPaisCreado(); }

    public void guardarPais(String jwt, Pais pais){ this.repository.guardarPais(pais, jwt); }

    public void consultarPaises(String jwt){ this.repository.consultarPaises(jwt); }

    public void borrarPais(String jwt, Long id){ this.repository.borrarPais(jwt, id); }

    public void actualizaPais(String jwt, Pais pais){ this.repository.actualizaPais(jwt, pais); }

    public void selectAllPaises(){ this.repository.selectAllPaises(); }

    public void filterByPais(String pais){ this.repository.filterByPais(pais); }

    public LiveData<List<Usuarios>> getUsuariosDB() { return this.repository.getUsuariosDB(); }

    public void filterByUsuariosConfAndAct(String nickName) { this.repository.filterByUsuariosConfAndAct(nickName); }

    public void selectAllUsuariosConfAndAct() { this.repository.selectAllUsuariosConfAndAct(); }

    public void selectAllUsuariosConf() { this.repository.selectAllUsuariosConf(); }

    public void selectUsuarioById(Long id) { this.repository.selectUsuarioById(id); }

    public LiveData<Usuarios> getUsuarioDB() { return this.repository.getUsuarioDB(); }

}
