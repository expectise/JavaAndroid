package com.multiventas.app.api;

import com.multiventas.app.dto.AsignarProducto;
import com.multiventas.app.dto.Login;
import com.multiventas.app.dto.LoginDetails;
import com.multiventas.app.dto.Pais;
import com.multiventas.app.dto.PaisesIn;
import com.multiventas.app.dto.Producto;
import com.multiventas.app.dto.ProductoIn;
import com.multiventas.app.dto.PujasIn;
import com.multiventas.app.dto.PujasNewDTO;
import com.multiventas.app.dto.Register;
import com.multiventas.app.dto.UsuariosIn;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class VentasApiClient {
    public interface VentasService {
        @POST("/Usuarios/nuevoUsuario")
        Call<Boolean> nuevoUsuario(@Body Register register);

        @GET("/Usuarios/usuarioExiste/{email}")
        Call<Boolean> usuarioExiste(@Path("email") String email);

        @POST("/Usuarios/login")
        Call<LoginDetails> login(@Body Login login);

        @GET("/api/Confirma/email/{clave}")
        Call<Boolean> confirmaEmail(@Path("clave") String clave, @Header("Authorization") String authHeader);

        @GET("/Usuarios/solClaveContra/{email}")
        Call<Boolean> solClaveContra(@Path("email") String email);

        @GET("/Usuarios/reseteaContra/{email}/{clave}")
        Call<Boolean> reseteaContra(@Path("email") String email, @Path("clave") String clave);

        @POST("/api/Admin/Producto/nuevoProducto")
        Call<ProductoIn> nuevoProducto(@Header("Authorization") String authHeader, @Body Producto producto);

        @GET("/api/Admin/Producto/productos")
        Call<List<ProductoIn>> getProductos(@Header("Authorization") String authHeader);

        @POST("/api/Admin/Producto/actualizarProducto")
        Call<ProductoIn> actualizaProducto(@Header("Authorization") String authHeader, @Body Producto producto);

        @GET("/api/Admin/Producto/borrarProducto/{id}")
        Call<Long> borrarProducto(@Header("Authorization") String authHeader, @Path("id") Long id);

        @GET("/api/Admin/Paises/paises")
        Call<List<PaisesIn>> getPaises(@Header("Authorization") String authHeader);

        @POST("/api/Admin/Paises/nuevoPais")
        Call<PaisesIn> nuevoPais(@Header("Authorization") String authHeader, @Body Pais pais);

        @POST("/api/Admin/Paises/actualizarPais")
        Call<PaisesIn> actualizaPais(@Header("Authorization") String authHeader, @Body Pais pais);

        @GET("/api/Admin/Paises/borrarPais/{id}")
        Call<Long> borrarPais(@Header("Authorization") String authHeader, @Path("id") Long id);

        @GET("/api/Admin/Usuarios/usuarios")
        Call<List<UsuariosIn>> getUsuarios(@Header("Authorization") String authHeader);
        
        @GET("/api/Admin/Pujas/pujas/{from}/{to}")
        Call<List<PujasIn>> getPujas(@Header("Authorization") String authHeader, @Path("from") Long from, @Path("to") Long to);

        @GET("/api/Admin/Pujas/pujaAbierta")
        Call<PujasIn> getPujaAbierta(@Header("Authorization") String authHeader);

        @POST("/api/Admin/Pujas/abrir")
        Call<Boolean> abrirPuja(@Header("Authorization") String authHeader, @Body PujasNewDTO pujasNewDTO);

        @GET("/api/Admin/Pujas/abierta")
        Call<Boolean> pujaAbierta(@Header("Authorization") String authHeader);

        @GET("/api/Admin/Pujas/cerrar")
        Call<Boolean> cerrarPuja(@Header("Authorization") String authHeader);

        @POST("/api/Admin/Pujas/asignar")
        Call<Boolean> asignarProducto(@Header("Authorization") String authHeader, @Body AsignarProducto asignarProducto);

        @GET("/api/Admin/Pujas/desasignar/{id}")
        Call<Boolean> deasignarProducto(@Header("Authorization") String authHeader, @Path("id") Long id);
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.5:8725")
            .addConverterFactory(MoshiConverterFactory.create())
            .build();


    private VentasService service;

    private static final VentasApiClient ourInstance = new VentasApiClient();

    public static VentasApiClient getInstance() {
        return ourInstance;
    }


    public VentasService getService() {
        if (service == null) {
            service = retrofit.create(VentasService.class);
        }
        return service;
    }


}
