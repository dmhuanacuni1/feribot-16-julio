package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rubros")
public class RubroEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String nombre = "";

    public String descripcion;
}
