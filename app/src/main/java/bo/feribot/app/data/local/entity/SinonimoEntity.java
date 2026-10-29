package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "sinonimos",
        foreignKeys = @ForeignKey(
                entity = ProductoEntity.class,
                parentColumns = "id",
                childColumns = "producto_id",
                onDelete = ForeignKey.CASCADE),
        indices = {
                @Index(value = "termino", unique = true),
                @Index("producto_id")
        })
public class SinonimoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String termino = "";

    @NonNull
    public String tipo = "producto";

    @ColumnInfo(name = "producto_id")
    public Integer productoId;

    public String destino;
}
