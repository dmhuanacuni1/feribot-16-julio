package bo.feribot.app.domain;

/**
 * Sinónimo o modismo cargado desde la base de datos.
 * tipo = "producto"   -> productoId apunta al producto.
 * tipo = "intencion"  -> destino = consultar_precio, consultar_ubicacion, saludo, ayuda.
 * tipo = "modismo"    -> destino = término canónico (informativo).
 */
public class Sinonimo {

    public final String termino;
    public final String tipo;
    public final Integer productoId;
    public final String destino;

    public Sinonimo(String termino, String tipo, Integer productoId, String destino) {
        this.termino = Normalizador.normalizar(termino);
        this.tipo = tipo;
        this.productoId = productoId;
        this.destino = destino;
    }
}
