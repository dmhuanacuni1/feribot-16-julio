package bo.feribot.app.domain;

import java.util.Locale;

/**
 * Formatea montos en Bolivianos sin decimales, con separador de miles boliviano.
 * Ejemplo: 2800 -> "Bs 2.800".
 */
public final class FormatoMoneda {

    private FormatoMoneda() {
    }

    public static String numero(double valor) {
        return String.format(Locale.US, "%,.0f", valor).replace(",", ".");
    }

    public static String bob(double valor) {
        return "Bs " + numero(valor);
    }

    public static String rango(double minimo, double maximo) {
        if (maximo <= minimo) {
            return bob(minimo);
        }
        return "Bs " + numero(minimo) + " – " + numero(maximo);
    }
}
