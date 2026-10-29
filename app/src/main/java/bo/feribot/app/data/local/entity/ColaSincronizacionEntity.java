package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cola_sincronizacion")
public class ColaSincronizacionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String entidad = "";

    @ColumnInfo(name = "entidad_id")
    public int entidadId;

    @NonNull
    public String operacion = "INSERT";

    public String payload;

    @NonNull
    public String estado = "pendiente";

    public int intentos;

    @NonNull
    @ColumnInfo(name = "creado_en")
    public String creadoEn = "";

    @ColumnInfo(name = "enviado_en")
    public String enviadoEn;
}
