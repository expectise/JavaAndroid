package com.multiventas.app.activity;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.multiventas.app.api.VentasApiClient;
import com.multiventas.app.database.VentasDatabase;
import com.multiventas.app.dto.Pais;
import com.multiventas.app.dto.PaisesIn;
import com.multiventas.app.dto.Producto;
import com.multiventas.app.dto.ProductoIn;
import com.multiventas.app.dto.UsuariosIn;
import com.multiventas.app.entity.Credentials;
import com.multiventas.app.entity.Paises;
import com.multiventas.app.entity.Productos;
import com.multiventas.app.entity.Usuario;
import com.multiventas.app.entity.Usuarios;
import com.multiventas.app.entity.Ventadetallada;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRepository {
    private VentasDatabase database;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    private MutableLiveData<List<Productos>> productos = new MutableLiveData<>();

    private MutableLiveData<List<Paises>> paises = new MutableLiveData<>();

    public AdminRepository(VentasDatabase database) {
        this.database = database;
    }

    public LiveData<Credentials> getCredentials() { return database.VentasDao().getCredentials(); }

    private MutableLiveData<Integer> productoCreado = new MutableLiveData<>();

    public LiveData<Integer> getProductoCreado() { return this.productoCreado; }

    public LiveData<List<Productos>> getProductos() { return this.productos; }

    public LiveData<List<Paises>> getPaises() { return this.paises; }

    private MutableLiveData<Boolean> logonValid = new MutableLiveData<>();

    public LiveData<Boolean> getLogonValid() { return this.logonValid; }

    private MutableLiveData<Integer> paisCreado = new MutableLiveData<>();

    public LiveData<Integer> getPaisCreado() { return this.paisCreado; }

    public MutableLiveData<Integer> usuarioStatus = new MutableLiveData<>();

    private MutableLiveData<List<Usuarios>> usuariosDB = new MutableLiveData<>();

    public LiveData<List<Usuarios>> getUsuariosDB() { return this.usuariosDB; }

    private MutableLiveData<Usuarios> usuarioDB = new MutableLiveData<>();

    public LiveData<Usuarios> getUsuarioDB() { return this.usuarioDB; }





    public AdminRepository(){
        productoCreado.setValue(0);
        logonValid.setValue(true);
        usuarioStatus.setValue(0);
    }

    public void nuleaProductoCreado(){ productoCreado.setValue(0); logonValid.setValue(true); }

    public void nuleaPaisCreado() { paisCreado.setValue(0); }

    public  LiveData<Integer> getUsuarioStatus() { return this.usuarioStatus; }

    public void guardaProducto(Productos productos){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarProducto(productos);
            }
        });
    }

    public void guardaProductos(List<Productos> productos){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarProductos(productos);
            }
        });
    }


    public void selectUsuarioById(Long id){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Usuarios usuario = database.VentasDao().getUsuarioById(id);
                usuarioDB.postValue(usuario);
            }
        });
    }



    public void guardaUsuarios(List<Usuarios> usuarios){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarUsuarios(usuarios);
            }
        });
    }

    public void emptyProducto(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyProducto();
            }
        });
    }

    public void emptyProductobyId(Long id){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyProductoById(id);
            }
        });
    }

    public void selectAllProductos(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Productos> productosOut = database.VentasDao().getProductos();

                productos.postValue(productosOut);
            }
        });
    }


    public void filterByCProducto(String cProducto){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String query = "%" + cProducto + "%";
                List<Productos> productosOut = database.VentasDao().filterProductos(query);
                productos.postValue(productosOut);
            }
        });
    }

    public void filterByUsuariosConfAndAct(String nickName){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String query = "%" + nickName + "%";
                List<Usuarios> usuariosOut = database.VentasDao().filterUsuariosConfAndAct(query);
                usuariosDB.postValue(usuariosOut);
            }
        });
    }


    public void selectAllUsuariosConfAndAct(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Usuarios> usuariosOut = database.VentasDao().getUsuariosConfAndAct();
                usuariosDB.postValue(usuariosOut);
            }
        });
    }


    public void selectAllUsuariosConf(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Usuarios> usuariosOut = database.VentasDao().getUsuariosConf();
                usuariosDB.postValue(usuariosOut);
            }
        });
    }


    public void guardaPaisDB(Paises paises){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarPais(paises);
            }
        });
    }

    public void guardaPaisesDB(List<Paises> paises){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().insertarPaises(paises);
            }
        });
    }


    public void emptyPais(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyPaises();
            }
        });
    }

    public void emptyPaisById(Long id){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyPaisById(id);
            }
        });
    }


    public void emptyUsuarios(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.VentasDao().emptyUsuarios();
            }
        });
    }

    public void selectAllPaises(){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Paises> paisesOut = database.VentasDao().getPaises();

                paises.postValue(paisesOut);
            }
        });
    }


    public void filterByPais(String pais){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String query = "%" + pais + "%";
                List<Paises> paisesOut = database.VentasDao().filterPaises(query);
                paises.postValue(paisesOut);
            }
        });
    }


    public void guardarProducto(Producto producto, String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        productoCreado.setValue(1);

        service.nuevoProducto(jwt, producto).enqueue(new Callback<ProductoIn>() {
            @Override
            public void onResponse(Call<ProductoIn> call, Response<ProductoIn> response) {

                if (response.code() != 201) {
                    productoCreado.setValue(2);
                    logonValid.setValue(false);
                }else {
                    productoCreado.setValue(3);
                    ProductoIn productoIn = response.body();

                    Productos productos = new Productos(productoIn.getIdProducto(),
                            productoIn.getTitulo(),
                            productoIn.getPrecio(),
                            productoIn.getCreado(),
                            productoIn.getActualizado(),
                            productoIn.getCodigoProducto(),
                            productoIn.getDescripcion());


                    guardaProducto(productos);

                    productoCreado.setValue(4);


                }


            }

            @Override
            public void onFailure(Call<ProductoIn> call, Throwable t) {
                productoCreado.setValue(5);
            }
        });
    }


    public void actualizaProducto(Producto producto, String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();
        productoCreado.setValue(1);

        service.actualizaProducto(jwt,producto).enqueue(new Callback<ProductoIn>() {
            @Override
            public void onResponse(Call<ProductoIn> call, Response<ProductoIn> response) {
                if (response.code() != 201){
                    productoCreado.setValue(2);
                    logonValid.setValue(false);
                }else {
                    productoCreado.setValue(3);
                    ProductoIn productoIn = response.body();

                    Productos productos = new Productos(productoIn.getIdProducto(),
                            productoIn.getTitulo(),
                            productoIn.getPrecio(),
                            productoIn.getCreado(),
                            productoIn.getActualizado(),
                            productoIn.getCodigoProducto(),
                            productoIn.getDescripcion());


                    guardaProducto(productos);

                    productoCreado.setValue(4);


                }

            }

            @Override
            public void onFailure(Call<ProductoIn> call, Throwable t) {
                productoCreado.setValue(5);
            }
        });
    }

    public void consultaProductos(String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.getProductos(jwt).enqueue(new Callback<List<ProductoIn>>() {
            @Override
            public void onResponse(Call<List<ProductoIn>> call, Response<List<ProductoIn>> response) {
                if (response.code() == 200){
                    emptyProducto();
                    List<Productos> productos = new ArrayList<Productos>();
                    for(ProductoIn productoIn : response.body()){
                        Productos productos_r = new Productos(
                                productoIn.getIdProducto(),
                                productoIn.getTitulo(),
                                productoIn.getPrecio(),
                                productoIn.getCreado(),
                                productoIn.getActualizado(),
                                productoIn.getCodigoProducto(),
                                productoIn.getDescripcion());
                        productos.add(productos_r);
                    }


                    guardaProductos(productos);

                    selectAllProductos();
                }else{
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<ProductoIn>> call, Throwable t) {
                productoCreado.setValue(5);
            }
        });
    }

    public void borrarProducto(String jwt, Long id) {
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.borrarProducto(jwt,id).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.code() == 200 && response.body() != null){
                    productoCreado.setValue(3);
                    //emptyProductobyId(response.body());
                    productoCreado.setValue(4);
                }else{
                    productoCreado.setValue(2);
                    logonValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                productoCreado.setValue(5);
            }
        });
    }

    public void guardarPais(Pais pais, String jwt){
        VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

        service.nuevoPais(jwt, pais).enqueue(new Callback<PaisesIn>() {
            @Override
            public void onResponse(Call<PaisesIn> call, Response<PaisesIn> response) {
                if (response.code() != 201) {
                    paisCreado.setValue(2);
                    logonValid.setValue(false);
                } else {
                    paisCreado.setValue(3);
                    PaisesIn paisesIn = response.body();

                    Paises paises = new Paises(paisesIn.getIdPais(),
                            paisesIn.getPais(),
                            paisesIn.getValorEnvio(),
                            paisesIn.getPrecioExcento(),
                            paisesIn.getCreado(),
                            paisesIn.getActualizado());


                    guardaPaisDB(paises);

                    paisCreado.setValue(4);


                }
            }

            @Override
            public void onFailure(Call<PaisesIn> call, Throwable t) {
                    paisCreado.setValue(5);
            }
        });

        }

        public void consultarPaises(String jwt){
            VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

            service.getPaises(jwt).enqueue(new Callback<List<PaisesIn>>() {
                @Override
                public void onResponse(Call<List<PaisesIn>> call, Response<List<PaisesIn>> response) {
                    if (response.code() == 200){
                        emptyPais();
                        List<Paises> paises = new ArrayList<Paises>();
                        for(PaisesIn paisesIn : response.body()){
                            Paises paises_r = new Paises(paisesIn.getIdPais(),
                                    paisesIn.getPais(),
                                    paisesIn.getValorEnvio(),
                                    paisesIn.getPrecioExcento(),
                                    paisesIn.getCreado(),
                                    paisesIn.getActualizado());
                            paises.add(paises_r);


                        }


                        guardaPaisesDB(paises);
                        selectAllPaises();
                    }else{
                        logonValid.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<List<PaisesIn>> call, Throwable t) {
                    paisCreado.setValue(5);
                }
            });
        }

        public void actualizaPais(String jwt, Pais pais){
            VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

            service.actualizaPais(jwt, pais).enqueue(new Callback<PaisesIn>() {
                @Override
                public void onResponse(Call<PaisesIn> call, Response<PaisesIn> response) {
                    if (response.code() != 201){
                        paisCreado.setValue(2);
                        logonValid.setValue(false);
                    }else {
                        paisCreado.setValue(3);
                        PaisesIn paisIn = response.body();

                        Paises paises = new Paises(paisIn.getIdPais(),
                                paisIn.getPais(),
                                paisIn.getValorEnvio(),
                                paisIn.getPrecioExcento(),
                                paisIn.getCreado(),
                                paisIn.getActualizado());


                        guardaPaisDB(paises);

                        paisCreado.setValue(4);


                    }
                }

                @Override
                public void onFailure(Call<PaisesIn> call, Throwable t) {
                    paisCreado.setValue(5);
                }
            });
        }

        public void borrarPais(String jwt, Long id){
            VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

            service.borrarPais(jwt, id).enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    if (response.code() == 200 && response.body() != null){
                        paisCreado.setValue(3);
                        //emptyPaisById(response.body());
                        paisCreado.setValue(4);
                    }else{
                        paisCreado.setValue(2);
                        logonValid.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    paisCreado.setValue(5);
                }
            });
        }

        public void insertarUsuarios(String jwt) {
            usuarioStatus.setValue(1);
            VentasApiClient.VentasService service = VentasApiClient.getInstance().getService();

            service.getUsuarios(jwt).enqueue(new Callback<List<UsuariosIn>>() {
                @Override
                public void onResponse(Call<List<UsuariosIn>> call, Response<List<UsuariosIn>> response) {
                    if (response.code() == 200) {
                        emptyUsuarios();

                        List<UsuariosIn> usuariosIn = response.body();

                        List<Usuarios> usuariosList = new ArrayList<>();

                        for (UsuariosIn usIn : usuariosIn) {
                            Usuarios usuario = new Usuarios(
                                    usIn.getIdUsuarios(),
                                    usIn.getNickname(),
                                    usIn.getNombre(),
                                    usIn.getAppaterno(),
                                    usIn.getApmaterno(),
                                    usIn.getTelefono(),
                                    usIn.getEmail(),
                                    usIn.getActivo(),
                                    usIn.getConfirmado(),
                                    usIn.getFecConfirma(),
                                    usIn.getCreado(),
                                    usIn.getActualizado(),
                                    usIn.getLatitud(),
                                    usIn.getLongitud()
                            );

                            usuariosList.add(usuario);
                        }
                        guardaUsuarios(usuariosList);

                        usuarioStatus.setValue(2);
                    } else {

                        usuarioStatus.setValue(3);
                    }


                }

                @Override
                public void onFailure(Call<List<UsuariosIn>> call, Throwable t) {
                    usuarioStatus.setValue(4);
                }
            });
        }

}
