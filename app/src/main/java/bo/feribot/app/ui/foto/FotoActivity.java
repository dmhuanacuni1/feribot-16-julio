package bo.feribot.app.ui.foto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bo.feribot.app.R;
import bo.feribot.app.data.RepositorioFeriBot;
import bo.feribot.app.databinding.ActivityFotoBinding;
import bo.feribot.app.domain.RespuestaFeriBot;

/**
 * Captura una foto del producto y la asocia a la cotización consultada.
 * La imagen queda en el almacenamiento privado de la app (sin permisos extra).
 */
public class FotoActivity extends AppCompatActivity {

    private static final String EXTRA_PRODUCTO_ID = "producto_id";
    private static final String EXTRA_PRODUCTO = "producto";
    private static final String EXTRA_PRECIO = "precio";

    private ActivityFotoBinding binding;
    private RepositorioFeriBot repositorio;
    private ImageCapture captura;
    private ExecutorService camara;
    private Integer productoId;
    private String producto;
    private String precio;

    private final ActivityResultLauncher<String> permisoCamara = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            concedido -> {
                if (concedido) {
                    iniciarCamara();
                } else {
                    Toast.makeText(this, R.string.foto_permiso, Toast.LENGTH_LONG).show();
                    finish();
                }
            });

    public static void abrir(Context contexto, RespuestaFeriBot respuesta) {
        Intent intent = new Intent(contexto, FotoActivity.class);
        intent.putExtra(EXTRA_PRODUCTO_ID, respuesta.productoId);
        intent.putExtra(EXTRA_PRODUCTO, respuesta.producto);
        intent.putExtra(EXTRA_PRECIO, respuesta.precio);
        contexto.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repositorio = RepositorioFeriBot.obtener(this);
        camara = Executors.newSingleThreadExecutor();

        productoId = getIntent().getIntExtra(EXTRA_PRODUCTO_ID, Integer.MIN_VALUE);
        if (productoId == Integer.MIN_VALUE) {
            productoId = null;
        }
        producto = getIntent().getStringExtra(EXTRA_PRODUCTO);
        precio = getIntent().getStringExtra(EXTRA_PRECIO);

        binding.toolbarFoto.setNavigationOnClickListener(vista -> finish());
        binding.botonCancelar.setOnClickListener(vista -> finish());
        binding.botonCapturar.setOnClickListener(vista -> tomarFoto());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            iniciarCamara();
        } else {
            permisoCamara.launch(Manifest.permission.CAMERA);
        }
    }

    private void iniciarCamara() {
        ListenableFuture<ProcessCameraProvider> futuro = ProcessCameraProvider.getInstance(this);
        futuro.addListener(() -> {
            try {
                ProcessCameraProvider proveedor = futuro.get();
                Preview vistaPrevia = new Preview.Builder().build();
                vistaPrevia.setSurfaceProvider(binding.vistaPrevia.getSurfaceProvider());

                captura = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                proveedor.unbindAll();
                proveedor.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA,
                        vistaPrevia, captura);
                binding.botonCapturar.setEnabled(true);
            } catch (Exception e) {
                Toast.makeText(this, R.string.foto_sin_camara, Toast.LENGTH_LONG).show();
                finish();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void tomarFoto() {
        if (captura == null) {
            return;
        }
        binding.botonCapturar.setEnabled(false);

        File carpeta = new File(getFilesDir(), "fotos");
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            Toast.makeText(this, R.string.foto_error, Toast.LENGTH_LONG).show();
            binding.botonCapturar.setEnabled(true);
            return;
        }

        File archivo = new File(carpeta, "cotizacion_" + System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions opciones =
                new ImageCapture.OutputFileOptions.Builder(archivo).build();

        captura.takePicture(opciones, camara, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults resultados) {
                guardar(archivo);
            }

            @Override
            public void onError(@NonNull ImageCaptureException excepcion) {
                runOnUiThread(() -> {
                    Toast.makeText(FotoActivity.this, R.string.foto_error,
                            Toast.LENGTH_LONG).show();
                    binding.botonCapturar.setEnabled(true);
                });
            }
        });
    }

    private void guardar(File archivo) {
        repositorio.guardarFoto(productoId, producto, precio, archivo.getAbsolutePath(), () -> {
            Toast.makeText(this, R.string.foto_guardada, Toast.LENGTH_LONG).show();
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        if (camara != null) {
            camara.shutdown();
        }
        super.onDestroy();
    }
}
