package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "productos",
        foreignKeys = @ForeignKey(
                entity = RubroEntity.class,
                parentColumns = "id",
                childColumns = "rubro_id",
                onDelete = ForeignKey.RESTRICT),
        indices = @Index("rubro_id"))
public class ProductoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "rubro_id")
    public int rubroId;

    @NonNull
    public String nombre = "";

    @NonNull
    public String estado = "nuevo";

    @ColumnInfo(name = "precio_min")
    public double precioMin;

    @ColumnInfo(name = "precio_max")
    public double precioMax;

    @NonNull
    public String moneda = "BOB";

    public String descripcion;
    public String consejo;
    public int activo = 1;

    @NonNull
    @ColumnInfo(name = "actualizado_en")
    public String actualizadoEn = "";
}
