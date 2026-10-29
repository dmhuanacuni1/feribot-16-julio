package bo.feribot.app.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import bo.feribot.app.data.local.entity.ComercianteEntity;
import bo.feribot.app.data.local.entity.MetadataEntity;
import bo.feribot.app.data.local.entity.ProductoEntity;
import bo.feribot.app.data.local.entity.ProductoPuestoEntity;
import bo.feribot.app.data.local.entity.PuestoEntity;
import bo.feribot.app.data.local.entity.RubroEntity;
import bo.feribot.app.data.local.entity.SinonimoEntity;
import bo.feribot.app.data.local.model.ProductoOferta;

@Dao
public interface CatalogoDao {

    @Query("SELECT COUNT(*) FROM productos")
    int contarProductos();

    @Query("SELECT p.id AS productoId, "
            + "p.nombre AS nombre, "
            + "p.estado AS estado, "
            + "p.precio_min AS precioMin, "
            + "p.precio_max AS precioMax, "
            + "p.moneda AS moneda, "
            + "p.consejo AS consejo, "
            + "r.nombre AS rubro, "
            + "u.codigo AS puestoCodigo, "
            + "u.sector AS sector, "
            + "u.calle AS calle, "
            + "u.referencia AS referencia, "
            + "u.latitud AS latitud, "
            + "u.longitud AS longitud, "
            + "u.dias_atencion AS diasAtencion, "
            + "u.horario AS horario, "
            + "pp.precio_min AS ofertaMin, "
            + "pp.precio_max AS ofertaMax "
            + "FROM productos p "
            + "INNER JOIN rubros r ON r.id = p.rubro_id "
            + "LEFT JOIN producto_puesto pp ON pp.producto_id = p.id AND pp.disponible = 1 "
            + "LEFT JOIN puestos u ON u.id = pp.puesto_id AND u.activo = 1 "
            + "WHERE p.activo = 1")
    List<ProductoOferta> listarOfertas();

    @Query("SELECT * FROM sinonimos")
    List<SinonimoEntity> listarSinonimos();

    @Query("SELECT * FROM puestos WHERE activo = 1 ORDER BY codigo")
    List<PuestoEntity> listarPuestos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarRubros(List<RubroEntity> rubros);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPuestos(List<PuestoEntity> puestos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarComerciantes(List<ComercianteEntity> comerciantes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarProductos(List<ProductoEntity> productos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarOfertas(List<ProductoPuestoEntity> ofertas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarSinonimos(List<SinonimoEntity> sinonimos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarMetadata(MetadataEntity metadata);
}
