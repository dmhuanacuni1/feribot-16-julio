package bo.feribot.app.data.local.model;

/**
 * Fila resultante del JOIN entre producto, oferta y puesto.
 * Room llena estos campos con los alias de la consulta de CatalogoDao.
 */
public class ProductoOferta {

    public int productoId;
    public String nombre;
    public String estado;
    public double precioMin;
    public double precioMax;
    public String moneda;
    public String consejo;
    public String rubro;
    public String puestoCodigo;
    public String sector;
    public String calle;
    public String referencia;
    public Double latitud;
    public Double longitud;
    public String diasAtencion;
    public String horario;
    public Double ofertaMin;
    public Double ofertaMax;
}
