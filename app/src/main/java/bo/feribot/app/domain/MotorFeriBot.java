package bo.feribot.app.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Motor de intenciones y coincidencias de FeriBot. Es Java puro: no depende de
 * Android ni de Room, por lo que se puede probar con JUnit sin emulador.
 */
public class MotorFeriBot {

    private static final int UMBRAL_COINCIDENCIA = 60;

    public RespuestaFeriBot responder(String texto, CatalogoFeriBot catalogo) {
        String consulta = Normalizador.normalizar(texto);
        if (consulta.isEmpty()) {
            return RespuestaFeriBot.ayuda();
        }

        boolean pideAyuda = contieneAlguno(consulta, "ayuda", "como funciona",
                "que puedes hacer", "que sabes hacer");
        boolean saluda = contieneAlguno(consulta, "hola", "buenas", "buen dia",
                "buenas tardes", "buenas noches");
        boolean pideUbicacion = contieneAlguno(consulta, "donde", "ubicacion", "queda",
                "llegar", "mapa");
        boolean pidePrecio = contieneAlguno(consulta, "cuanto", "precio", "cuesta",
                "vale", "a como", "a cuanto");

        for (Sinonimo sinonimo : catalogo.sinonimos()) {
            if (!"intencion".equals(sinonimo.tipo)
                    || !Normalizador.contieneTermino(consulta, sinonimo.termino)) {
                continue;
            }
            if ("saludo".equals(sinonimo.destino)) {
                saluda = true;
            } else if ("ayuda".equals(sinonimo.destino)) {
                pideAyuda = true;
            } else if ("consultar_ubicacion".equals(sinonimo.destino)) {
                pideUbicacion = true;
            } else if ("consultar_precio".equals(sinonimo.destino)) {
                pidePrecio = true;
            }
        }

        Coincidencia coincidencia = buscarProducto(consulta, catalogo);

        if (coincidencia == null) {
            if (pideAyuda) {
                return RespuestaFeriBot.ayuda();
            }
            if (saluda) {
                return RespuestaFeriBot.saludo();
            }
            return RespuestaFeriBot.noEncontrado(texto, catalogo);
        }

        Intencion intencion = (pideUbicacion && !pidePrecio)
                ? Intencion.UBICACION
                : Intencion.PRECIO;
        return RespuestaFeriBot.desdeItem(coincidencia.item, intencion);
    }

    private Coincidencia buscarProducto(String consulta, CatalogoFeriBot catalogo) {
        Map<Integer, List<String>> terminos = new HashMap<>();
        for (ItemCatalogo item : catalogo.items()) {
            terminos.computeIfAbsent(item.productoId, clave -> new ArrayList<>())
                    .add(Normalizador.normalizar(item.nombre));
        }
        for (Sinonimo sinonimo : catalogo.sinonimos()) {
            if ("producto".equals(sinonimo.tipo) && sinonimo.productoId != null) {
                terminos.computeIfAbsent(sinonimo.productoId, clave -> new ArrayList<>())
                        .add(sinonimo.termino);
            }
        }

        Coincidencia mejor = null;
        for (ItemCatalogo item : catalogo.items()) {
            List<String> opciones = terminos.getOrDefault(item.productoId, Collections.emptyList());
            int puntaje = 0;
            for (String termino : opciones) {
                puntaje = Math.max(puntaje, puntajeTermino(consulta, termino));
            }
            if (puntaje < UMBRAL_COINCIDENCIA) {
                continue;
            }
            if (mejor == null
                    || puntaje > mejor.puntaje
                    || (puntaje == mejor.puntaje && item.precioMin < mejor.item.precioMin)) {
                mejor = new Coincidencia(item, puntaje);
            }
        }
        return mejor;
    }

    private int puntajeTermino(String consulta, String termino) {
        if (termino == null || termino.isEmpty()) {
            return 0;
        }
        if (consulta.equals(termino)) {
            return 100;
        }
        if (Normalizador.contieneTermino(consulta, termino)) {
            return 95;
        }
        if (termino.length() >= 4 && Normalizador.contieneTermino(termino, consulta)) {
            return 85;
        }
        return puntajeDifuso(consulta, termino);
    }

    private int puntajeDifuso(String consulta, String termino) {
        String[] tokensConsulta = consulta.split(" ");
        String[] tokensTermino = termino.split(" ");
        for (String a : tokensConsulta) {
            if (a.length() < 4) {
                continue;
            }
            for (String b : tokensTermino) {
                if (b.length() < 4) {
                    continue;
                }
                int tolerancia = b.length() >= 7 ? 2 : 1;
                if (distancia(a, b) <= tolerancia) {
                    return 70;
                }
            }
        }
        return 0;
    }

    private boolean contieneAlguno(String consulta, String... terminos) {
        for (String termino : terminos) {
            if (Normalizador.contieneTermino(consulta, termino)) {
                return true;
            }
        }
        return false;
    }

    static int distancia(String a, String b) {
        int[] anterior = new int[b.length() + 1];
        int[] actual = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++) {
            anterior[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            actual[0] = i;
            for (int j = 1; j <= b.length(); j++) {
                int costo = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                actual[j] = Math.min(Math.min(actual[j - 1] + 1, anterior[j] + 1),
                        anterior[j - 1] + costo);
            }
            int[] temporal = anterior;
            anterior = actual;
            actual = temporal;
        }
        return anterior[b.length()];
    }

    private static class Coincidencia {
        final ItemCatalogo item;
        final int puntaje;

        Coincidencia(ItemCatalogo item, int puntaje) {
            this.item = item;
            this.puntaje = puntaje;
        }
    }
}
