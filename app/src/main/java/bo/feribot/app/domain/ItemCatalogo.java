package bo.feribot.app.domain;

/**
 * Producto ya resuelto con su mejor oferta y la ubicación del puesto.
 */
public class ItemCatalogo {

    public final int productoId;
    public final String nombre;
    public final String estado;
    public final String rubro;
    public final double precioMin;
    public final double precioMax;
    public final String moneda;
    public final String consejo;
    public final String puestoCodigo;
    public final String sector;
    public final String calle;
    public final String referencia;
    public final String diasAtencion;
    public final String horario;
    public final Double latitud;
    public final Double longitud;

    public ItemCatalogo(int productoId, String nombre, String estado, String rubro,
                        double precioMin, double precioMax, String moneda, String consejo,
                        String puestoCodigo, String sector, String calle, String referencia,
                        String diasAtencion, String horario, Double latitud, Double longitud) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.estado = estado;
        this.rubro = rubro;
        this.precioMin = precioMin;
        this.precioMax = precioMax;
        this.moneda = moneda;
        this.consejo = consejo;
        this.puestoCodigo = puestoCodigo;
        this.sector = sector;
        this.calle = calle;
        this.referencia = referencia;
        this.diasAtencion = diasAtencion;
        this.horario = horario;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
