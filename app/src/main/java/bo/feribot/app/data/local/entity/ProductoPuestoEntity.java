package bo.feribot.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "producto_puesto",
        primaryKeys = {"producto_id", "puesto_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = ProductoEntity.class,
                        parentColumns = "id",
                        childColumns = "producto_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = PuestoEntity.class,
                        parentColumns = "id",
                        childColumns = "puesto_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = @Index("puesto_id"))
public class ProductoPuestoEntity {

    @ColumnInfo(name = "producto_id")
    public int productoId;

    @ColumnInfo(name = "puesto_id")
    public int puestoId;

    @ColumnInfo(name = "precio_min")
    public double precioMin;

    @ColumnInfo(name = "precio_max")
    public double precioMax;

    public int disponible = 1;
}
