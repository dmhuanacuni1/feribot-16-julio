package bo.feribot.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MotorFeriBotTest {

    private MotorFeriBot motor;
    private CatalogoFeriBot catalogo;

    @Before
    public void preparar() {
        motor = new MotorFeriBot();

        List<ItemCatalogo> items = Arrays.asList(
                item(1, "Laptop Lenovo ThinkPad T480", "usado", "Electrónica",
                        2800, 3600, "ELE-01", "Calle 1"),
                item(3, "Celular Xiaomi Redmi Note 12", "nuevo", "Electrónica",
                        1200, 1500, "ELE-01", "Calle 1"),
                item(13, "Chamarra de cuero", "usado", "Ropa",
                        80, 200, "ROP-01", "Calle 3"),
                item(21, "Taladro percutor Bosch 650W", "nuevo", "Herramientas",
                        380, 520, "HER-01", "Calle 5"));

        List<Sinonimo> sinonimos = Arrays.asList(
                new Sinonimo("lap", "producto", 1, null),
                new Sinonimo("laptop", "producto", 1, null),
                new Sinonimo("cel", "producto", 3, null),
                new Sinonimo("celular", "producto", 3, null),
                new Sinonimo("chamarra", "producto", 13, null),
                new Sinonimo("taladro", "producto", 21, null),
                new Sinonimo("a cómo está", "intencion", null, "consultar_precio"),
                new Sinonimo("dónde está", "intencion", null, "consultar_ubicacion"),
                new Sinonimo("hola", "intencion", null, "saludo"));

        catalogo = new CatalogoFeriBot(items, sinonimos);
    }

    @Test
    public void respondePrecioConEstructuraCompleta() {
        RespuestaFeriBot respuesta = motor.responder("¿A cómo está la lap?", catalogo);

        assertTrue(respuesta.encontrado);
        assertFalse(respuesta.estimado);
        assertEquals(Intencion.PRECIO, respuesta.intencion);
        assertEquals(Integer.valueOf(1), respuesta.productoId);
        assertTrue(respuesta.producto.contains("ThinkPad"));
        assertTrue(respuesta.precio.contains("Bs 2.800"));
        assertTrue(respuesta.ubicacion.contains("ELE-01"));
        assertNotNull(respuesta.consejo);
        assertTrue(respuesta.mensaje.contains("Producto identificado"));
        assertTrue(respuesta.mensaje.contains("Consejo del casero"));
    }

    @Test
    public void entiendeModismosBolivianos() {
        assertEquals(Integer.valueOf(13),
                motor.responder("chamarra", catalogo).productoId);
        assertEquals(Integer.valueOf(3),
                motor.responder("a cómo está el cel", catalogo).productoId);
        assertEquals(Integer.valueOf(21),
                motor.responder("cuánto cuesta el taladro", catalogo).productoId);
    }

    @Test
    public void detectaIntencionDeUbicacion() {
        RespuestaFeriBot respuesta = motor.responder("¿Dónde está el taladro?", catalogo);

        assertEquals(Intencion.UBICACION, respuesta.intencion);
        assertTrue(respuesta.accion.contains("Ver en Mapa"));
    }

    @Test
    public void saludaSinProducto() {
        RespuestaFeriBot respuesta = motor.responder("hola", catalogo);

        assertEquals(Intencion.SALUDO, respuesta.intencion);
        assertTrue(respuesta.mensaje.contains("FeriBot"));
    }

    @Test
    public void productoDesconocidoSugiereRubroYRegateo() {
        RespuestaFeriBot respuesta = motor.responder("playstation 5", catalogo);

        assertFalse(respuesta.encontrado);
        assertTrue(respuesta.estimado);
        assertTrue(respuesta.mensaje.contains("regateo"));
        assertTrue(respuesta.mensaje.contains("Electrónica"));
    }

    @Test
    public void formatoDePrecioEnBolivianos() {
        assertEquals("Bs 2.800 – 3.600", FormatoMoneda.rango(2800, 3600));
        assertEquals("Bs 95", FormatoMoneda.rango(95, 95));
    }

    @Test
    public void distanciaLevenshtein() {
        assertEquals(1, MotorFeriBot.distancia("lapto", "laptop"));
        assertEquals(0, MotorFeriBot.distancia("taladro", "taladro"));
    }

    private ItemCatalogo item(int id, String nombre, String estado, String rubro,
                              double min, double max, String codigo, String calle) {
        return new ItemCatalogo(id, nombre, estado, rubro, min, max, "BOB",
                "Revisa el producto antes de pagar.",
                codigo, rubro, calle, "Referencia de prueba",
                "jueves,domingo", "08:00-17:00", -16.5, -68.16);
    }
}
