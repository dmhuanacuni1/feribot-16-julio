package bo.feribot.app.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import bo.feribot.app.data.local.dao.CatalogoDao;
import bo.feribot.app.data.local.dao.HistorialDao;
import bo.feribot.app.data.local.entity.ColaSincronizacionEntity;
import bo.feribot.app.data.local.entity.ComercianteEntity;
import bo.feribot.app.data.local.entity.HistorialEntity;
import bo.feribot.app.data.local.entity.MetadataEntity;
import bo.feribot.app.data.local.entity.ProductoEntity;
import bo.feribot.app.data.local.entity.ProductoPuestoEntity;
import bo.feribot.app.data.local.entity.PuestoEntity;
import bo.feribot.app.data.local.entity.RubroEntity;
import bo.feribot.app.data.local.entity.SinonimoEntity;

@Database(
        entities = {
                RubroEntity.class,
                PuestoEntity.class,
                ComercianteEntity.class,
                ProductoEntity.class,
                ProductoPuestoEntity.class,
                SinonimoEntity.class,
                HistorialEntity.class,
                MetadataEntity.class,
                ColaSincronizacionEntity.class
        },
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String NOMBRE = "feribot.db";

    private static volatile AppDatabase instancia;

    public abstract CatalogoDao catalogoDao();

    public abstract HistorialDao historialDao();

    public static AppDatabase obtener(Context contexto) {
        if (instancia == null) {
            synchronized (AppDatabase.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(
                                    contexto.getApplicationContext(),
                                    AppDatabase.class,
                                    NOMBRE)
                            .build();
                }
            }
        }
        return instancia;
    }
}
