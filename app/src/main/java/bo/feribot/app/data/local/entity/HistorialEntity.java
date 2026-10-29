package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "historial_consultas",
        foreignKeys = @ForeignKey(
                entity = ProductoEntity.class,
                parentColumns = "id",
                childColumns = "producto_id",
                onDelete = ForeignKey.SET_NULL),
        indices = @Index("producto_id"))
public class HistorialEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String texto = "";

    public String intencion;

    @ColumnInfo(name = "producto_id")
    public Integer productoId;

    public String respuesta;

    @NonNull
    public String origen = "local";

    @NonNull
    @ColumnInfo(name = "creado_en")
    public String creadoEn = "";
}
