package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "comerciantes",
        foreignKeys = @ForeignKey(
                entity = PuestoEntity.class,
                parentColumns = "id",
                childColumns = "puesto_id",
                onDelete = ForeignKey.SET_NULL),
        indices = @Index("puesto_id"))
public class ComercianteEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String nombre = "";

    public String telefono;

    @androidx.room.ColumnInfo(name = "puesto_id")
    public Integer puestoId;
}
