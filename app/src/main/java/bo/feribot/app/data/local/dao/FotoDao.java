package bo.feribot.app.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import bo.feribot.app.data.local.entity.FotoCotizacionEntity;

@Dao
public interface FotoDao {

    @Insert
    long insertar(FotoCotizacionEntity foto);

    @Query("SELECT * FROM fotos_cotizacion ORDER BY creado_en DESC LIMIT :limite")
    List<FotoCotizacionEntity> ultimas(int limite);
}
