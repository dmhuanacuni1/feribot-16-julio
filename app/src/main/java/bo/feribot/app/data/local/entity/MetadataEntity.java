package bo.feribot.app.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "metadata")
public class MetadataEntity {

    @PrimaryKey
    @NonNull
    public String clave = "";

    @NonNull
    public String valor = "";

    public MetadataEntity() {
    }

    @Ignore
    public MetadataEntity(@NonNull String clave, @NonNull String valor) {
        this.clave = clave;
        this.valor = valor;
    }
}
