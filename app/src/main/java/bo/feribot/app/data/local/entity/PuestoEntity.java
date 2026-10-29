package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "puestos")
public class PuestoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String codigo = "";

    @NonNull
    public String sector = "";

    public String calle;
    public String referencia;
    public Double latitud;
    public Double longitud;

    @NonNull
    @androidx.room.ColumnInfo(name = "dias_atencion")
    public String diasAtencion = "jueves,domingo";

    public String horario;

    public int activo = 1;
}
