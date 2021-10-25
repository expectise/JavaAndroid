package com.multiventas.app.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.multiventas.app.api.VentasApiClient;
import com.multiventas.app.database.VentasDatabase;
import com.multiventas.app.dto.AsignarProducto;
import com.multiventas.app.dto.PujasIn;
import com.multiventas.app.dto.PujasNewDTO;
import com.multiventas.app.dto.VentadetalladaOutDTO;
import com.multiventas.app.dto.VentasOutDTO;
import com.multiventas.app.entity.Pujas;
import com.multiventas.app.entity.UsuariosAndVentas;
import com.multiventas.app.entity.Ventadetallada;
import com.multiventas.app.entity.Ventas;
import com.multiventas.app.entity.VentasAndVentadetallada;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PujasAdminRepository {
    private VentasDatabase database;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public PujasAdminRepository(VentasDatabase database) {
        this.database = database;
        this.logonValid.setValue(true);
    }

    private MutableLiveData<Boolean> pujaAbierta = new MutableLiveData<>();
    private MutableLiveData<Boolean> productoAsignado = new MutableLiveData<>();

    public LiveData<Boolean> isPujaAbierta() { return this.pujaAbierta; }

    public LiveData<Boolean> getProductoAsignado() { return this.productoAsignado; }


    public LiveData<List<Ventadetallada>> getVentadetallada() { return this.database.VentasDao().getVentadetallada(); }

    public LiveData<List<VentasAndVentadetallada>> getVentasBD() { return this.database.VentasDao().getVentasAndVentadetallada(); }

    public LiveData<List<Pujas>> getPujasBD() { return this.database.VentasDao().getPujas(); }

    public LiveData<List<UsuariosAndVentas>> getUsuariosAndVentas() { return this.database.VentasDao().getUsuariosAndVentas(); }

    private MutableLiveData<Boolean> logonValid = new MutableLiveData<>();

    public LiveData<Boolean> getLogonValid() { return this.logonValid; }

    private MutableLiveData<Boolean> desasignado = new MutableLiveData<>();

    public LiveData<Boolean> getDesasignado() { return this.desasignado; }

    private MutableLiveData<List<Ventas>> ventasByPuja = new MutableLiveData<>();

    public LiveData<List<Ventas>> getVentasByPuja() { return this.ventasByPuja; }

    private MutableLiveData<List<Ventadetallada>> detalleDB = new MutableLiveData<>();

    public LiveData<List<Ventadetallada>> getDetalleDB() { return this.detalleDB; }

    public void processpujaAbierta(String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.pujaAbierta(jwt).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200){
                    pujaAbierta.setValue(response.body());
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void emptyPujas(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyPujas();
                database.VentasDao().emptyVentas();
                database.VentasDao().emptyVentadetallada();
            }
        });
    }

    public void setVentasByPuja(Long puja){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Ventas> ventas = database.VentasDao().getVentasByPuja(puja);

                ventasByPuja.postValue(ventas);
            }
        });
    }

    public void selectVentadetalladaByIdVenta(Long id){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Ventadetallada> detalle = database.VentasDao().filterVentadetalladaByVentas(id);
                detalleDB.postValue(detalle);
            }
        });
    }



    public void insertarPujas(List<Pujas> pujas, List<Ventas> ventas, List<Ventadetallada> ventadetalladas) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarPujas(pujas);
                database.VentasDao().insertarVentas(ventas);
                database.VentasDao().insertarVentadetallada(ventadetalladas);
            }
        });
    }

    public void abrirPuja(String jwt, PujasNewDTO pujasNewDTO){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.abrirPuja(jwt, pujasNewDTO).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200){
                    pujaAbierta.setValue(response.body());
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void cerrarPuja(String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.cerrarPuja(jwt).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200){
                    pujaAbierta.setValue(response.body() ? false : true);
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void getPujaAbierta(String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.getPujaAbierta(jwt).enqueue(new Callback<PujasIn>() {
            @Override
            public void onResponse(Call<PujasIn> call, Response<PujasIn> response) {
                if (response.code() == 200){
                    emptyPujas();

                    PujasIn pujasin = response.body();

                    List<Pujas> pujasList = new ArrayList<>();
                    List<Ventadetallada> detalleList = new ArrayList<>();
                    List<Ventas> ventasList = new ArrayList<>();

                    Pujas pujas = new Pujas(pujasin.getIdPujas(),
                            pujasin.getTitulo(),
                            pujasin.getFechaInicio(),
                            pujasin.getFechaFinal(),
                            pujasin.getFecPriAbo());
                    pujasList.add(pujas);



                    List<VentasOutDTO> ventasOutDTOS = pujasin.getVentasOutDTO();



                    for (VentasOutDTO ventasOutDTO : ventasOutDTOS){
                        Ventas venta = new Ventas(
                                ventasOutDTO.getIdVentas(),
                                ventasOutDTO.getIdUsuarios(),
                                ventasOutDTO.getIdPuja(),
                                ventasOutDTO.getFolio(),
                                ventasOutDTO.getCostoEnvio(),
                                ventasOutDTO.getPrecioExcento(),
                                ventasOutDTO.getEnviado(),
                                ventasOutDTO.getGuia(),
                                ventasOutDTO.getEmpresa(),
                                ventasOutDTO.getCreado(),
                                ventasOutDTO.getActualizado()
                        );

                        ventasList.add(venta);


                        List<VentadetalladaOutDTO> ventadetalladaOutDTO = ventasOutDTO.getVentadetalladaOutDTO();


                        for (VentadetalladaOutDTO detalleIn : ventadetalladaOutDTO){
                            Ventadetallada detalle = new Ventadetallada(
                                    detalleIn.getIdVentaDetallada(),
                                    detalleIn.getIdVentas(),
                                    detalleIn.getPrecio(),
                                    detalleIn.getTitulo(),
                                    detalleIn.getDescripcion(),
                                    detalleIn.getCodigoProducto(),
                                    detalleIn.getCreado(),
                                    detalleIn.getActualizado()
                            );

                            detalleList.add(detalle);
                        }

                    }

                    insertarPujas(pujasList, ventasList, detalleList);


                }else if (response.code() == 404) {
                    emptyPujas();
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<PujasIn> call, Throwable t) {

            }
        });
    }


    public void getPujas(String jwt, Long from, Long to){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.getPujas(jwt, from, to).enqueue(new Callback<List<PujasIn>>() {
            @Override
            public void onResponse(Call<List<PujasIn>> call, Response<List<PujasIn>> response) {
                if (response.code() == 200){
                    emptyPujas();

                    List<PujasIn> pujasinS = response.body();

                    List<Pujas> pujasList = new ArrayList<>();
                    List<Ventadetallada> detalleList = new ArrayList<>();
                    List<Ventas> ventasList = new ArrayList<>();


                    for (PujasIn pujasin : pujasinS) {
                        Pujas pujas = new Pujas(pujasin.getIdPujas(),
                                pujasin.getTitulo(),
                                pujasin.getFechaInicio(),
                                pujasin.getFechaFinal(),
                                pujasin.getFecPriAbo());
                        pujasList.add(pujas);


                        List<VentasOutDTO> ventasOutDTOS = pujasin.getVentasOutDTO();


                        for (VentasOutDTO ventasOutDTO : ventasOutDTOS) {
                            Ventas venta = new Ventas(
                                    ventasOutDTO.getIdVentas(),
                                    ventasOutDTO.getIdUsuarios(),
                                    ventasOutDTO.getIdPuja(),
                                    ventasOutDTO.getFolio(),
                                    ventasOutDTO.getCostoEnvio(),
                                    ventasOutDTO.getPrecioExcento(),
                                    ventasOutDTO.getEnviado(),
                                    ventasOutDTO.getGuia(),
                                    ventasOutDTO.getEmpresa(),
                                    ventasOutDTO.getCreado(),
                                    ventasOutDTO.getActualizado()
                            );

                            ventasList.add(venta);


                            List<VentadetalladaOutDTO> ventadetalladaOutDTO = ventasOutDTO.getVentadetalladaOutDTO();


                            for (VentadetalladaOutDTO detalleIn : ventadetalladaOutDTO) {
                                Ventadetallada detalle = new Ventadetallada(
                                        detalleIn.getIdVentaDetallada(),
                                        detalleIn.getIdVentas(),
                                        detalleIn.getPrecio(),
                                        detalleIn.getTitulo(),
                                        detalleIn.getDescripcion(),
                                        detalleIn.getCodigoProducto(),
                                        detalleIn.getCreado(),
                                        detalleIn.getActualizado()
                                );

                                detalleList.add(detalle);
                            }

                        }

                    }
                    insertarPujas(pujasList, ventasList, detalleList);
                }else if (response.code() == 404) {
                    emptyPujas();
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<PujasIn>> call, Throwable t) {

            }
        });
    }

    public void asignarProducto(AsignarProducto asignarProducto, String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.asignarProducto(jwt, asignarProducto).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200){
                    productoAsignado.setValue(response.body());
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }

    public void desasignarProducto(Long id, String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.deasignarProducto(jwt, id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200){
                    desasignado.setValue(response.body());
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

}
