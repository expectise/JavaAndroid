package com.multiventas.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.multiventas.app.entity.Credentials;
import com.multiventas.app.entity.Paises;
import com.multiventas.app.entity.Productos;
import com.multiventas.app.entity.Pujas;
import com.multiventas.app.entity.PujasAndVentas;
import com.multiventas.app.entity.Usuario;
import com.multiventas.app.entity.Usuarios;
import com.multiventas.app.entity.UsuariosAndVentas;
import com.multiventas.app.entity.Ventadetallada;
import com.multiventas.app.entity.Ventas;
import com.multiventas.app.entity.VentasAndVentadetallada;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Usuario.class, Credentials.class, Productos.class, Paises.class, Usuarios.class, Pujas.class, Ventas.class, Ventadetallada.class}, version = 1)
public abstract class VentasDatabase  extends RoomDatabase {

    public abstract VentasDao VentasDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile VentasDatabase INSTANCE;
    public static VentasDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VentasDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VentasDatabase.class, "VentasDatabase")
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}