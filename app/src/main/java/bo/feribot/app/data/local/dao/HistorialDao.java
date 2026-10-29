package bo.feribot.app.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import bo.feribot.app.data.local.entity.HistorialEntity;

@Dao
public interface HistorialDao {

    @Insert
    long insertar(HistorialEntity consulta);

    @Query("SELECT * FROM historial_consultas ORDER BY creado_en DESC LIMIT :limite")
    List<HistorialEntity> ultimas(int limite);
}
