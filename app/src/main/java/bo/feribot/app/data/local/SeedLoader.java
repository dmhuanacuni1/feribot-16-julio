package bo.feribot.app.data.local;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bo.feribot.app.data.local.entity.ComercianteEntity;
import bo.feribot.app.data.local.entity.MetadataEntity;
import bo.feribot.app.data.local.entity.ProductoEntity;
import bo.feribot.app.data.local.entity.ProductoPuestoEntity;
import bo.feribot.app.data.local.entity.PuestoEntity;
import bo.feribot.app.data.local.entity.RubroEntity;
import bo.feribot.app.data.local.entity.SinonimoEntity;

/**
 * Carga el catálogo desde app/src/main/assets/dataset.json la primera vez que
 * se abre la base. El JSON es la misma fuente que genera database/seed.sql.
 */
public final class SeedLoader {

    private static final String ASSET = "dataset.json";

    private SeedLoader() {
    }

    public static void cargar(Context contexto, AppDatabase db) throws IOException, JSONException {
        JSONObject raiz = new JSONObject(leerAsset(contexto));

        List<PuestoEntity> puestos = new ArrayList<>();
        List<ComercianteEntity> comerciantes = new ArrayList<>();
        List<RubroEntity> rubros = new ArrayList<>();
        List<ProductoEntity> productos = new ArrayList<>();
        List<ProductoPuestoEntity> ofertas = new ArrayList<>();
        List<SinonimoEntity> sinonimos = new ArrayList<>();
        Map<String, Integer> idPuestoPorCodigo = new HashMap<>();

        JSONArray puestosJson = raiz.getJSONArray("puestos");
        for (int i = 0; i < puestosJson.length(); i++) {
            JSONObject json = puestosJson.getJSONObject(i);
            PuestoEntity puesto = new PuestoEntity();
            puesto.id = json.getInt("id");
            puesto.codigo = json.getString("codigo");
            puesto.sector = json.getString("sector");
            puesto.calle = json.optString("calle", null);
            puesto.referencia = json.optString("referencia", null);
            puesto.latitud = json.isNull("latitud") ? null : json.getDouble("latitud");
            puesto.longitud = json.isNull("longitud") ? null : json.getDouble("longitud");
            puesto.diasAtencion = json.optString("dias_atencion", "jueves,domingo");
            puesto.horario = json.optString("horario", null);
            puesto.activo = 1;
            puestos.add(puesto);
            idPuestoPorCodigo.put(puesto.codigo, puesto.id);
        }

        JSONArray comerciantesJson = raiz.optJSONArray("comerciantes");
        if (comerciantesJson != null) {
            for (int i = 0; i < comerciantesJson.length(); i++) {
                JSONObject json = comerciantesJson.getJSONObject(i);
                ComercianteEntity comerciante = new ComercianteEntity();
                comerciante.id = json.getInt("id");
                comerciante.nombre = json.getString("nombre");
                comerciante.telefono = json.optString("telefono", null);
                Integer idPuesto = idPuestoPorCodigo.get(json.optString("puesto", ""));
                comerciante.puestoId = idPuesto;
                comerciantes.add(comerciante);
            }
        }

        String ahora = LocalDateTime.now().toString();
        JSONArray rubrosJson = raiz.getJSONArray("rubros");
        for (int i = 0; i < rubrosJson.length(); i++) {
            JSONObject jsonRubro = rubrosJson.getJSONObject(i);
            RubroEntity rubro = new RubroEntity();
            rubro.id = jsonRubro.getInt("id");
            rubro.nombre = jsonRubro.getString("nombre");
            rubro.descripcion = jsonRubro.optString("descripcion", null);
            rubros.add(rubro);

            JSONArray productosJson = jsonRubro.getJSONArray("productos");
            for (int j = 0; j < productosJson.length(); j++) {
                JSONObject jsonProducto = productosJson.getJSONObject(j);
                ProductoEntity producto = new ProductoEntity();
                producto.id = jsonProducto.getInt("id");
                producto.rubroId = rubro.id;
                producto.nombre = jsonProducto.getString("nombre");
                producto.estado = jsonProducto.optString("estado", "nuevo");
                producto.precioMin = jsonProducto.getDouble("precio_min");
                producto.precioMax = jsonProducto.getDouble("precio_max");
                producto.moneda = "BOB";
                producto.descripcion = jsonProducto.optString("descripcion", null);
                producto.consejo = jsonProducto.optString("consejo", null);
                producto.activo = 1;
                producto.actualizadoEn = ahora;
                productos.add(producto);

                JSONArray sinonimosJson = jsonProducto.optJSONArray("sinonimos");
                if (sinonimosJson != null) {
                    for (int k = 0; k < sinonimosJson.length(); k++) {
                        sinonimos.add(crearSinonimo(sinonimosJson.getString(k), "producto",
                                producto.id, null));
                    }
                }

                JSONArray ofertasJson = jsonProducto.getJSONArray("puestos");
                for (int k = 0; k < ofertasJson.length(); k++) {
                    JSONObject jsonOferta = ofertasJson.getJSONObject(k);
                    Integer idPuesto = idPuestoPorCodigo.get(jsonOferta.getString("codigo"));
                    if (idPuesto == null) {
                        continue;
                    }
                    ProductoPuestoEntity oferta = new ProductoPuestoEntity();
                    oferta.productoId = producto.id;
                    oferta.puestoId = idPuesto;
                    oferta.precioMin = jsonOferta.getDouble("precio_min");
                    oferta.precioMax = jsonOferta.getDouble("precio_max");
                    oferta.disponible = 1;
                    ofertas.add(oferta);
                }
            }
        }

        JSONArray modismosJson = raiz.optJSONArray("modismos");
        if (modismosJson != null) {
            for (int i = 0; i < modismosJson.length(); i++) {
                JSONObject json = modismosJson.getJSONObject(i);
                sinonimos.add(crearSinonimo(json.getString("termino"),
                        json.getString("tipo"), null, json.optString("destino", null)));
            }
        }

        String version = raiz.optString("version", "1.0.0");
        String feriaNombre = "";
        JSONObject feriaJson = raiz.optJSONObject("feria");
        if (feriaJson != null) {
            feriaNombre = feriaJson.optString("nombre", "");
        }
        String feria = feriaNombre;

        db.runInTransaction(() -> {
            db.catalogoDao().insertarRubros(rubros);
            db.catalogoDao().insertarPuestos(puestos);
            db.catalogoDao().insertarComerciantes(comerciantes);
            db.catalogoDao().insertarProductos(productos);
            db.catalogoDao().insertarOfertas(ofertas);
            db.catalogoDao().insertarSinonimos(sinonimos);
            db.catalogoDao().insertarMetadata(new MetadataEntity("dataset_version", version));
            db.catalogoDao().insertarMetadata(new MetadataEntity("feria_nombre", feria));
        });
    }

    private static SinonimoEntity crearSinonimo(String termino, String tipo,
                                                Integer productoId, String destino) {
        SinonimoEntity sinonimo = new SinonimoEntity();
        sinonimo.termino = termino;
        sinonimo.tipo = tipo;
        sinonimo.productoId = productoId;
        sinonimo.destino = destino;
        return sinonimo;
    }

    private static String leerAsset(Context contexto) throws IOException {
        try (InputStream entrada = contexto.getAssets().open(ASSET);
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int leidos;
            while ((leidos = entrada.read(buffer)) != -1) {
                salida.write(buffer, 0, leidos);
            }
            return salida.toString("UTF-8");
        }
    }
}
