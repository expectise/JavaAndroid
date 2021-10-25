package com.multiventas.app.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.multiventas.app.entity.Credentials;
import com.multiventas.app.entity.Paises;
import com.multiventas.app.entity.Productos;
import com.multiventas.app.entity.Pujas;
import com.multiventas.app.entity.Usuario;
import com.multiventas.app.entity.Usuarios;
import com.multiventas.app.entity.UsuariosAndVentas;
import com.multiventas.app.entity.Ventadetallada;
import com.multiventas.app.entity.Ventas;
import com.multiventas.app.entity.VentasAndVentadetallada;

import java.util.List;

@Dao
public interface VentasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarUsuario(Usuario usuario);

    @Query("SELECT * FROM Usuario where id=1")
    LiveData<Usuario> getUsuario();

    @Query("DELETE FROM Usuario WHERE id=1")
    void deleteUsuario();

    @Query("DELETE FROM Credentials WHERE id=1")
    void deleteCredentials();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarCredentials(Credentials credentials);

    @Query("SELECT * FROM Credentials where id=1")
    LiveData<Credentials> getCredentials();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarProducto(Productos productos);

    @Query("SELECT * FROM  Productos ORDER BY idProducto DESC")
    List<Productos> getProductos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarProductos(List<Productos> productos);

    @Query("DELETE FROM Productos")
    void emptyProducto();

    @Query("DELETE FROM Productos WHERE Idproducto = :id")
    void emptyProductoById(Long id);

    @Query("SELECT * FROM Productos WHERE codigoProducto LIKE :cpProducto")
    List<Productos> filterProductos(String cpProducto);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPais(Paises paises);

    @Query("SELECT * FROM  Paises ORDER BY idPais DESC")
    List<Paises> getPaises();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPaises(List<Paises> paises);

    @Query("DELETE FROM Paises")
    void emptyPaises();

    @Query("DELETE FROM Paises WHERE IdPais = :id")
    void emptyPaisById(Long id);

    @Query("SELECT * FROM Paises WHERE pais LIKE :pais")
    List<Paises> filterPaises(String pais);

    @Query("SELECT * FROM Usuarios where activo=1 and confirmado=1")
    List<Usuarios> getUsuariosConfAndAct();

    @Query("SELECT * FROM Usuarios where confirmado=1")
    List<Usuarios> getUsuariosConf();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarUsuarios(List<Usuarios> usuarios);

    @Query("DELETE FROM Usuarios")
    void emptyUsuarios();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarVentadetallada(List<Ventadetallada> ventadetallada);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarVentas(List<Ventas> ventas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPujas(List<Pujas> pujas);

    @Query("DELETE FROM Ventas")
    void emptyVentas();

    @Query("DELETE FROM Ventadetallada")
    void emptyVentadetallada();

    @Query("DELETE FROM Pujas")
    void emptyPujas();

    @Query("SELECT * FROM Ventadetallada")
    LiveData<List<Ventadetallada>> getVentadetallada();

    @Query("SELECT * FROM Ventadetallada WHERE idVentas =:idVentas")
    List<Ventadetallada> filterVentadetalladaByVentas(Long idVentas);

    @Query("SELECT * FROM Ventas")
    LiveData<List<Ventas>> getVentas();

    @Query("SELECT * FROM Ventas")
    LiveData<List<Pujas>> getPujas();

    @Query("SELECT * FROM Ventas")
    LiveData<List<VentasAndVentadetallada>> getVentasAndVentadetallada();

    @Query("SELECT * FROM Usuarios")
    LiveData<List<UsuariosAndVentas>> getUsuariosAndVentas();

    @Query("SELECT * FROM Usuarios WHERE idUsuarios =:id")
    Usuarios getUsuarioById(Long id);

    @Query("SELECT * FROM Usuarios WHERE nickname LIKE :nickName and activo=1 and confirmado=1")
    List<Usuarios> filterUsuariosConfAndAct(String nickName);

    @Query("SELECT * FROM Ventas WHERE idPujas = :puja")
    List<Ventas> getVentasByPuja(Long puja);
}