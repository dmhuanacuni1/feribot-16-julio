package bo.feribot.app.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bo.feribot.app.data.local.AppDatabase;
import bo.feribot.app.data.local.SeedLoader;
import bo.feribot.app.data.local.entity.HistorialEntity;
import bo.feribot.app.data.local.entity.SinonimoEntity;
import bo.feribot.app.data.local.model.ProductoOferta;
import bo.feribot.app.domain.CatalogoFeriBot;
import bo.feribot.app.domain.ItemCatalogo;
import bo.feribot.app.domain.MotorFeriBot;
import bo.feribot.app.domain.RespuestaFeriBot;
import bo.feribot.app.domain.Sinonimo;

/**
 * Une Room con el motor: carga el catálogo, responde y guarda el historial.
 */
public class RepositorioFeriBot {

    private static final String TAG = "RepositorioFeriBot";

    private static volatile RepositorioFeriBot instancia;

    private final Context contexto;
    private final AppDatabase db;
    private final MotorFeriBot motor = new MotorFeriBot();
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private final Handler principal = new Handler(Looper.getMainLooper());

    public interface Callback {
        void onResultado(RespuestaFeriBot respuesta);
    }

    private RepositorioFeriBot(Context contexto) {
        this.contexto = contexto.getApplicationContext();
        this.db = AppDatabase.obtener(this.contexto);
    }

    public static RepositorioFeriBot obtener(Context contexto) {
        if (instancia == null) {
            synchronized (RepositorioFeriBot.class) {
                if (instancia == null) {
                    instancia = new RepositorioFeriBot(contexto);
                }
            }
        }
        return instancia;
    }

    /**
     * Carga el dataset la primera vez. Se ejecuta en segundo plano y avisa en el
     * hilo principal al terminar.
     */
    public void prepararDatos(Runnable alTerminar) {
        io.execute(() -> {
            try {
                if (db.catalogoDao().contarProductos() == 0) {
                    SeedLoader.cargar(contexto, db);
                }
            } catch (Exception e) {
                Log.e(TAG, "No se pudo cargar el catálogo local", e);
            } finally {
                principal.post(alTerminar);
            }
        });
    }

    public void responder(String texto, Callback callback) {
        io.execute(() -> {
            RespuestaFeriBot respuesta;
            try {
                CatalogoFeriBot catalogo = cargarCatalogo();
                respuesta = motor.responder(texto, catalogo);
                guardarHistorial(texto, respuesta);
            } catch (Exception e) {
                Log.e(TAG, "Error al generar la respuesta", e);
                respuesta = RespuestaFeriBot.noEncontrado(texto,
                        new CatalogoFeriBot(new ArrayList<>(), new ArrayList<>()));
            }
            RespuestaFeriBot resultado = respuesta;
            principal.post(() -> callback.onResultado(resultado));
        });
    }

    private CatalogoFeriBot cargarCatalogo() {
        List<ProductoOferta> filas = db.catalogoDao().listarOfertas();

        Map<Integer, ItemCatalogo> porProducto = new LinkedHashMap<>();
        for (ProductoOferta fila : filas) {
            double minimo = fila.ofertaMin != null ? fila.ofertaMin : fila.precioMin;
            double maximo = fila.ofertaMax != null ? fila.ofertaMax : fila.precioMax;
            ItemCatalogo actual = porProducto.get(fila.productoId);
            if (actual == null || minimo < actual.precioMin) {
                porProducto.put(fila.productoId, new ItemCatalogo(
                        fila.productoId, fila.nombre, fila.estado, fila.rubro,
                        minimo, maximo, fila.moneda, fila.consejo,
                        fila.puestoCodigo, fila.sector, fila.calle, fila.referencia,
                        fila.diasAtencion, fila.horario, fila.latitud, fila.longitud));
            }
        }

        List<Sinonimo> sinonimos = new ArrayList<>();
        for (SinonimoEntity entidad : db.catalogoDao().listarSinonimos()) {
            sinonimos.add(new Sinonimo(entidad.termino, entidad.tipo,
                    entidad.productoId, entidad.destino));
        }

        return new CatalogoFeriBot(new ArrayList<>(porProducto.values()), sinonimos);
    }

    private void guardarHistorial(String texto, RespuestaFeriBot respuesta) {
        try {
            HistorialEntity historial = new HistorialEntity();
            historial.texto = texto;
            historial.intencion = respuesta.intencion.name();
            historial.productoId = respuesta.productoId;
            historial.respuesta = respuesta.mensaje;
            historial.origen = "local";
            historial.creadoEn = LocalDateTime.now().toString();
            db.historialDao().insertar(historial);
        } catch (Exception e) {
            Log.w(TAG, "No se pudo guardar el historial", e);
        }
    }
}
