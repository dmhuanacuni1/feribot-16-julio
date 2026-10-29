package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Foto tomada en el puesto y asociada a una cotización del chat.
 */
@Entity(tableName = "fotos_cotizacion")
public class FotoCotizacionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "producto_id")
    public Integer productoId;

    @NonNull
    public String producto = "";

    public String precio;

    @NonNull
    public String ruta = "";

    @NonNull
    @ColumnInfo(name = "creado_en")
    public String creadoEn = "";
}
