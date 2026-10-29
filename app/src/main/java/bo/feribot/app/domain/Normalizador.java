package bo.feribot.app.domain;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Normaliza el texto del usuario: minúsculas, sin tildes, sin signos y con
 * espacios simples. Permite comparar "¿A cómo está la LAP?" con "lap".
 */
public final class Normalizador {

    private Normalizador() {
    }

    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        return sinTildes.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9ñ]+", " ")
                .trim()
                .replaceAll("\\s+", " ");
    }

    /**
     * Indica si el término aparece como palabra completa dentro del texto.
     */
    public static boolean contieneTermino(String textoNormalizado, String terminoNormalizado) {
        if (textoNormalizado == null || terminoNormalizado == null || terminoNormalizado.isEmpty()) {
            return false;
        }
        return (" " + textoNormalizado + " ").contains(" " + terminoNormalizado + " ");
    }
}
