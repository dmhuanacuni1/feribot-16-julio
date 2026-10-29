package bo.feribot.app.ui.mapa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.GroundOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.List;
import java.util.Locale;

import bo.feribot.app.R;
import bo.feribot.app.data.RepositorioFeriBot;
import bo.feribot.app.data.local.entity.PuestoEntity;
import bo.feribot.app.databinding.ActivityMapaBinding;
import bo.feribot.app.domain.RespuestaFeriBot;

/**
 * Mapa offline de la feria. La imagen base se dibuja con tools/generar-mapa.ps1
 * y se superpone como GroundOverlay sobre un MapView de osmdroid sin conexión.
 */
public class MapaActivity extends AppCompatActivity {

    private static final String EXTRA_LATITUD = "latitud";
    private static final String EXTRA_LONGITUD = "longitud";
    private static final String EXTRA_PRODUCTO = "producto";
    private static final String EXTRA_PRECIO = "precio";
    private static final String EXTRA_UBICACION = "ubicacion";

    private static final int CODIGO_PERMISO_UBICACION = 41;

    /** Límites geográficos del plano generado por tools/generar-mapa.ps1. */
    private static final BoundingBox LIMITES_MAPA =
            new BoundingBox(-16.5000, -68.1580, -16.5100, -68.1710);
    private static final double CENTRO_LAT = -16.5046;
    private static final double CENTRO_LON = -68.1644;

    private ActivityMapaBinding binding;
    private RepositorioFeriBot repositorio;
    private double latitudDestino;
    private double longitudDestino;
    private Marker marcadorUsuario;

    public static void abrir(Context contexto, RespuestaFeriBot respuesta) {
        Intent intent = new Intent(contexto, MapaActivity.class);
        intent.putExtra(EXTRA_LATITUD, respuesta.latitud);
        intent.putExtra(EXTRA_LONGITUD, respuesta.longitud);
        intent.putExtra(EXTRA_PRODUCTO, respuesta.producto);
        intent.putExtra(EXTRA_PRECIO, respuesta.precio);
        intent.putExtra(EXTRA_UBICACION, respuesta.ubicacion);
        contexto.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration.getInstance().load(getApplicationContext(),
                getSharedPreferences("osmdroid", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());

        super.onCreate(savedInstanceState);
        binding = ActivityMapaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        latitudDestino = getIntent().getDoubleExtra(EXTRA_LATITUD, CENTRO_LAT);
        longitudDestino = getIntent().getDoubleExtra(EXTRA_LONGITUD, CENTRO_LON);

        binding.toolbarMapa.setNavigationOnClickListener(vista -> finish());
        binding.botonCerrar.setOnClickListener(vista -> finish());
        binding.botonMiUbicacion.setOnClickListener(vista -> pedirUbicacion());

        binding.textoProducto.setText(getIntent().getStringExtra(EXTRA_PRODUCTO));
        binding.textoPrecio.setText(getIntent().getStringExtra(EXTRA_PRECIO));
        binding.textoUbicacion.setText(getIntent().getStringExtra(EXTRA_UBICACION));

        configurarMapa();
        cargarPuestos();
    }

    private void configurarMapa() {
        binding.mapa.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapa.setUseDataConnection(false);
        binding.mapa.setMultiTouchControls(true);
        binding.mapa.setTilesScaledToDpi(true);
        binding.mapa.setMinZoomLevel(15.0);
        binding.mapa.setMaxZoomLevel(19.0);
        binding.mapa.getController().setZoom(17.0);
        binding.mapa.getController().setCenter(new GeoPoint(latitudDestino, longitudDestino));

        Drawable plano = ContextCompat.getDrawable(this, R.drawable.mapa_feria);
        if (plano != null) {
            GroundOverlay capa = new GroundOverlay();
            capa.setImage(bitmapDe(plano));
            capa.setPosition(new GeoPoint(LIMITES_MAPA.getLatNorth(), LIMITES_MAPA.getLonWest()),
                    new GeoPoint(LIMITES_MAPA.getLatSouth(), LIMITES_MAPA.getLonEast()));
            binding.mapa.getOverlays().add(capa);
        }
    }

    private Bitmap bitmapDe(Drawable drawable) {
        int ancho = drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : 1600;
        int alto = drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : 1400;
        Bitmap bitmap = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);
        Canvas lienzo = new Canvas(bitmap);
        drawable.setBounds(0, 0, ancho, alto);
        drawable.draw(lienzo);
        return bitmap;
    }

    private void cargarPuestos() {
        repositorio = RepositorioFeriBot.obtener(this);
        repositorio.puestos(this::dibujarPuestos);
    }

    private void dibujarPuestos(List<PuestoEntity> puestos) {
        for (PuestoEntity puesto : puestos) {
            if (puesto.latitud == null || puesto.longitud == null) {
                continue;
            }
            boolean esDestino = Math.abs(puesto.latitud - latitudDestino) < 0.00005
                    && Math.abs(puesto.longitud - longitudDestino) < 0.00005;

            Marker marcador = new Marker(binding.mapa);
            marcador.setPosition(new GeoPoint(puesto.latitud, puesto.longitud));
            marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marcador.setTitle(puesto.codigo + " · " + puesto.sector);
            marcador.setSnippet(describirPuesto(puesto));
            marcador.setIcon(ContextCompat.getDrawable(this,
                    esDestino ? R.drawable.ic_pin_destino : R.drawable.ic_pin_puesto));
            marcador.setOnMarkerClickListener((m, mapa) -> {
                m.showInfoWindow();
                mapa.getController().animateTo(m.getPosition());
                return true;
            });
            binding.mapa.getOverlays().add(marcador);

            if (esDestino) {
                marcador.showInfoWindow();
                binding.mapa.getController().setCenter(marcador.getPosition());
            }
        }
        binding.mapa.invalidate();
        if (permisoUbicacionConcedido()) {
            ubicarUsuario();
        }
    }

    private String describirPuesto(PuestoEntity puesto) {
        StringBuilder texto = new StringBuilder();
        if (puesto.calle != null && !puesto.calle.isEmpty()) {
            texto.append(puesto.calle);
        }
        if (puesto.referencia != null && !puesto.referencia.isEmpty()) {
            if (texto.length() > 0) {
                texto.append(" · ");
            }
            texto.append(puesto.referencia);
        }
        if (puesto.horario != null && !puesto.horario.isEmpty()) {
            texto.append("\n").append(puesto.horario);
        }
        return texto.toString();
    }

    private void pedirUbicacion() {
        if (permisoUbicacionConcedido()) {
            ubicarUsuario();
            return;
        }
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                CODIGO_PERMISO_UBICACION);
    }

    private boolean permisoUbicacionConcedido() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PERMISO_UBICACION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ubicarUsuario();
            } else {
                Toast.makeText(this, R.string.mapa_permiso_ubicacion, Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void ubicarUsuario() {
        LocationManager gestor = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (gestor == null) {
            binding.textoDistancia.setText(R.string.mapa_sin_gps);
            return;
        }

        Location mejor = null;
        for (String proveedor : new String[]{LocationManager.GPS_PROVIDER,
                LocationManager.NETWORK_PROVIDER}) {
            try {
                if (!gestor.isProviderEnabled(proveedor)) {
                    continue;
                }
                Location candidata = gestor.getLastKnownLocation(proveedor);
                if (candidata != null
                        && (mejor == null || candidata.getTime() > mejor.getTime())) {
                    mejor = candidata;
                }
            } catch (SecurityException | IllegalArgumentException ignored) {
                // Proveedor no disponible en este equipo.
            }
        }

        if (mejor != null) {
            mostrarUsuario(mejor);
            return;
        }

        binding.textoDistancia.setText(R.string.mapa_buscando_gps);
        LocationListener escucha = this::mostrarUsuario;
        try {
            gestor.requestSingleUpdate(LocationManager.GPS_PROVIDER, escucha,
                    Looper.getMainLooper());
        } catch (SecurityException | IllegalArgumentException e) {
            binding.textoDistancia.setText(R.string.mapa_sin_gps);
        }
    }

    private void mostrarUsuario(Location ubicacion) {
        GeoPoint punto = new GeoPoint(ubicacion.getLatitude(), ubicacion.getLongitude());
        if (marcadorUsuario == null) {
            marcadorUsuario = new Marker(binding.mapa);
            marcadorUsuario.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            marcadorUsuario.setTitle(getString(R.string.mapa_tu_ubicacion));
            marcadorUsuario.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_pin_usuario));
            binding.mapa.getOverlays().add(marcadorUsuario);
        }
        marcadorUsuario.setPosition(punto);
        binding.mapa.invalidate();

        float[] distancia = new float[1];
        Location.distanceBetween(ubicacion.getLatitude(), ubicacion.getLongitude(),
                latitudDestino, longitudDestino, distancia);
        binding.textoDistancia.setText(
                getString(R.string.mapa_distancia_a, formatearDistancia(distancia[0])));
    }

    private String formatearDistancia(float metros) {
        if (metros < 1000) {
            return String.format(Locale.US, "%.0f m", metros);
        }
        return String.format(Locale.US, "%.1f km", metros / 1000f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (binding != null) {
            binding.mapa.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (binding != null) {
            binding.mapa.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (binding != null) {
            binding.mapa.onDetach();
        }
        super.onDestroy();
    }
}
