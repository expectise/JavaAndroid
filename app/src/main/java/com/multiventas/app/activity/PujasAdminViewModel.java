package com.multiventas.app.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.multiventas.app.database.VentasDatabase;
import com.multiventas.app.dto.AsignarProducto;
import com.multiventas.app.dto.PujasNewDTO;
import com.multiventas.app.entity.Pujas;
import com.multiventas.app.entity.UsuariosAndVentas;
import com.multiventas.app.entity.Ventadetallada;
import com.multiventas.app.entity.Ventas;
import com.multiventas.app.entity.VentasAndVentadetallada;

import java.util.List;

public class PujasAdminViewModel extends AndroidViewModel {
    private final PujasAdminRepository repository;

    public PujasAdminViewModel(@NonNull Application application) {
        super(application);
        VentasDatabase database = VentasDatabase.getDatabase(application);
        this.repository = new PujasAdminRepository(database);
    }

    public LiveData<Boolean> isPujaAbierta() { return this.repository.isPujaAbierta(); }

    public void processpujaAbierta(String jwt) { this.repository.processpujaAbierta(jwt); }

    public void abrirPuja(String jwt, PujasNewDTO pujasNewDTO) { this.repository.abrirPuja(jwt, pujasNewDTO); }

    public void cerrarPuja(String jwt){ this.repository.cerrarPuja(jwt); }

    public void getPujaAbierta(String jwt){ this.repository.getPujaAbierta(jwt); }

    public void getPujas(String jwt, Long from, Long to){ this.repository.getPujas(jwt,from,to); }

    public LiveData<List<Ventadetallada>> getVentadetalladaBD() { return this.repository.getVentadetallada(); }

    public LiveData<List<Pujas>> getPujasBD() { return this.repository.getPujasBD(); }

    public LiveData<List<VentasAndVentadetallada>> getVentasDB() { return this.repository.getVentasBD(); }

    public LiveData<List<UsuariosAndVentas>> getUsuariosAndVentas() { return this.repository.getUsuariosAndVentas(); }

    public LiveData<Boolean> getLogonValid() { return this.repository.getLogonValid(); }

    public LiveData<Boolean> getProductoAsignado() { return this.repository.getProductoAsignado(); }

    public void asignarProducto(AsignarProducto asignarProducto, String jwt){ this.repository.asignarProducto(asignarProducto, jwt); }

    public LiveData<Boolean> getDesasignado() { return this.repository.getDesasignado(); }

    public void desasignarProducto(Long id, String jwt) { this.repository.desasignarProducto(id, jwt); }

    public void setVentasByPuja(Long puja){ this.repository.setVentasByPuja(puja); }

    public LiveData<List<Ventas>> getVentasByPuja() { return this.repository.getVentasByPuja(); }

    public LiveData<List<Ventadetallada>> getDetalleDB() { return this.repository.getDetalleDB(); }

    public void selectVentadetalladaByIdVenta(Long id) { this.repository.selectVentadetalladaByIdVenta(id); }
}
