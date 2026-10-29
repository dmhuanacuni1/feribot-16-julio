package bo.feribot.app.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import bo.feribot.app.data.local.dao.CatalogoDao;
import bo.feribot.app.data.local.dao.FotoDao;
import bo.feribot.app.data.local.dao.HistorialDao;
import bo.feribot.app.data.local.entity.ColaSincronizacionEntity;
import bo.feribot.app.data.local.entity.ComercianteEntity;
import bo.feribot.app.data.local.entity.FotoCotizacionEntity;
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
                ColaSincronizacionEntity.class,
                FotoCotizacionEntity.class
        },
        version = 2,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String NOMBRE = "feribot.db";

    private static volatile AppDatabase instancia;

    public abstract CatalogoDao catalogoDao();

    public abstract HistorialDao historialDao();

    public abstract FotoDao fotoDao();

    static final Migration MIGRACION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `fotos_cotizacion` ("
                    + "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`producto_id` INTEGER, "
                    + "`producto` TEXT NOT NULL, "
                    + "`precio` TEXT, "
                    + "`ruta` TEXT NOT NULL, "
                    + "`creado_en` TEXT NOT NULL)");
        }
    };

    public static AppDatabase obtener(Context contexto) {
        if (instancia == null) {
            synchronized (AppDatabase.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(
                                    contexto.getApplicationContext(),
                                    AppDatabase.class,
                                    NOMBRE)
                            .addMigrations(MIGRACION_1_2)
                            .build();
                }
            }
        }
        return instancia;
    }
}
