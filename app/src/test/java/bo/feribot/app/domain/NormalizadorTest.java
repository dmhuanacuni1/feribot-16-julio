package bo.feribot.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NormalizadorTest {

    @Test
    public void quitaTildesYSignos() {
        assertEquals("a como esta la lap",
                Normalizador.normalizar("¿A CÓMO ESTÁ LA LAP?"));
    }

    @Test
    public void colapsaEspacios() {
        assertEquals("chamarra de cuero",
                Normalizador.normalizar("  chamarra   de    cuero  "));
    }

    @Test
    public void textoNuloQuedaVacio() {
        assertEquals("", Normalizador.normalizar(null));
    }

    @Test
    public void detectaTerminoComoPalabraCompleta() {
        assertTrue(Normalizador.contieneTermino("a como esta la lap", "lap"));
        assertTrue(!Normalizador.contieneTermino("laptop nueva", "lap"));
    }
}
