package bo.feribot.app.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Respuesta estructurada de FeriBot. El campo {@code mensaje} es el texto que se
 * muestra en el chat; los demás campos permiten construir tarjetas o navegar al mapa.
 */
public class RespuestaFeriBot {

    public final boolean encontrado;
    public final boolean estimado;
    public final Intencion intencion;
    public final Integer productoId;
    public final String producto;
    public final String estado;
    public final String precio;
    public final String ubicacion;
    public final String accion;
    public final String consejo;
    public final String mensaje;

    private RespuestaFeriBot(boolean encontrado, boolean estimado, Intencion intencion,
                             Integer productoId, String producto, String estado, String precio,
                             String ubicacion, String accion, String consejo, String mensaje) {
        this.encontrado = encontrado;
        this.estimado = estimado;
        this.intencion = intencion;
        this.productoId = productoId;
        this.producto = producto;
        this.estado = estado;
        this.precio = precio;
        this.ubicacion = ubicacion;
        this.accion = accion;
        this.consejo = consejo;
        this.mensaje = mensaje;
    }

    public static RespuestaFeriBot desdeItem(ItemCatalogo item, Intencion intencion) {
        String precio = FormatoMoneda.rango(item.precioMin, item.precioMax);
        String ubicacion = describirUbicacion(item);
        String accion = intencion == Intencion.UBICACION
                ? "Toca \"Ver en Mapa\" para ir con el GPS hasta el puesto."
                : "Usa la cámara para guardar la foto de esta cotización y comparar en otro puesto.";
        String consejo = item.consejo != null && !item.consejo.isEmpty()
                ? item.consejo
                : "Revisa el producto antes de pagar y pide probarlo en el puesto.";

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Producto identificado: ").append(item.nombre);
        if (item.estado != null && !item.estado.isEmpty()) {
            mensaje.append(" (").append(item.estado).append(")");
        }
        mensaje.append("\nRango de precio estimado: ").append(precio)
                .append("\nUbicación en la feria: ").append(ubicacion)
                .append("\nAcción en la app: ").append(accion)
                .append("\nConsejo del casero: ").append(consejo)
                .append("\n\nPrecio referencial; el final se cierra regateando en el puesto.");

        return new RespuestaFeriBot(true, false, intencion, item.productoId, item.nombre,
                item.estado, precio, ubicacion, accion, consejo, mensaje.toString());
    }

    public static RespuestaFeriBot saludo() {
        String texto = "¡Hola, casero/a! 👋 Soy FeriBot, tu asistente de la Feria 16 de Julio.\n\n"
                + "Puedo ayudarte a cotizar y ubicar productos. Prueba con:\n"
                + "• \"¿A cómo está la lap?\"\n"
                + "• \"Chamarra de cuero\"\n"
                + "• \"¿Dónde está el taladro?\"\n\n"
                + "También entiendo \"cel\", \"casaca\", \"en la 16\" y otros modismos.";
        return new RespuestaFeriBot(true, false, Intencion.SALUDO, null, null, null,
                null, null, null, null, texto);
    }

    public static RespuestaFeriBot ayuda() {
        String texto = "Así funciono, casero/a:\n\n"
                + "1. Escribe o dicta lo que buscas (producto, marca o rubro).\n"
                + "2. Te doy el producto, el rango de precio en BOB, la ubicación del puesto y un consejo.\n"
                + "3. Toca \"Ver en Mapa\" para ir con el GPS.\n\n"
                + "Funciono sin internet porque el catálogo está guardado en tu celular.";
        return new RespuestaFeriBot(true, false, Intencion.AYUDA, null, null, null,
                null, null, null, null, texto);
    }

    public static RespuestaFeriBot noEncontrado(String consulta, CatalogoFeriBot catalogo) {
        String rubro = elegirRubro(Normalizador.normalizar(consulta), catalogo);
        List<ItemCatalogo> ejemplos = new ArrayList<>();
        if (rubro != null) {
            for (ItemCatalogo item : catalogo.items()) {
                if (rubro.equals(item.rubro)) {
                    ejemplos.add(item);
                }
            }
            ejemplos.sort(Comparator.comparingDouble(item -> item.precioMin));
            if (ejemplos.size() > 3) {
                ejemplos = ejemplos.subList(0, 3);
            }
        }

        StringBuilder texto = new StringBuilder();
        texto.append("No encontré \"").append(consulta.trim())
                .append("\" en el catálogo, casero/a. 😕\n\n");
        if (!ejemplos.isEmpty()) {
            texto.append("Lo más parecido es el rubro ").append(rubro).append(":\n");
            for (ItemCatalogo item : ejemplos) {
                texto.append("• ").append(item.nombre)
                        .append(" — ").append(FormatoMoneda.rango(item.precioMin, item.precioMax))
                        .append("\n");
            }
            texto.append("\n");
        }
        texto.append("El precio final depende del regateo en el puesto. ")
                .append("Si me das otra pista (marca, modelo o rubro), lo busco de nuevo.");

        return new RespuestaFeriBot(false, true, Intencion.DESCONOCIDA, null, null, null,
                null, null, null, null, texto.toString());
    }

    private static String describirUbicacion(ItemCatalogo item) {
        StringBuilder ubicacion = new StringBuilder();
        ubicacion.append(item.sector != null && !item.sector.isEmpty()
                ? item.sector
                : "Sector no registrado");
        if (item.calle != null && !item.calle.isEmpty()) {
            ubicacion.append(" · ").append(item.calle);
        }
        if (item.puestoCodigo != null && !item.puestoCodigo.isEmpty()) {
            ubicacion.append(" (puesto ").append(item.puestoCodigo).append(")");
        }
        if (item.referencia != null && !item.referencia.isEmpty()) {
            ubicacion.append(" — ").append(item.referencia);
        }
        return ubicacion.toString();
    }

    private static String elegirRubro(String consulta, CatalogoFeriBot catalogo) {
        Map<String, Integer> puntajes = new LinkedHashMap<>();
        Map<String, Integer> conteos = new LinkedHashMap<>();
        for (ItemCatalogo item : catalogo.items()) {
            if (item.rubro == null || item.rubro.isEmpty()) {
                continue;
            }
            conteos.merge(item.rubro, 1, Integer::sum);
            String nombre = Normalizador.normalizar(item.nombre);
            for (String token : consulta.split(" ")) {
                if (token.length() >= 3 && nombre.contains(token)) {
                    puntajes.merge(item.rubro, 1, Integer::sum);
                }
            }
        }

        String mejor = null;
        int mejorPuntaje = -1;
        int mejorConteo = -1;
        for (Map.Entry<String, Integer> entrada : conteos.entrySet()) {
            String rubro = entrada.getKey();
            int puntaje = puntajes.getOrDefault(rubro, 0);
            int conteo = entrada.getValue();
            if (puntaje > mejorPuntaje || (puntaje == mejorPuntaje && conteo > mejorConteo)) {
                mejor = rubro;
                mejorPuntaje = puntaje;
                mejorConteo = conteo;
            }
        }
        return mejor;
    }
}
