package bo.feribot.app.ui.chat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import bo.feribot.app.R;
import bo.feribot.app.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatViewModel viewModel;
    private ChatAdapter adaptador;

    private final ActivityResultLauncher<Intent> lanzadorVoz = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            resultado -> {
                if (resultado.getResultCode() != RESULT_OK || resultado.getData() == null) {
                    return;
                }
                ArrayList<String> opciones = resultado.getData()
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (opciones == null || opciones.isEmpty()) {
                    return;
                }
                String texto = opciones.get(0);
                binding.campoMensaje.setText(texto);
                binding.campoMensaje.setSelection(texto.length());
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adaptador = new ChatAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        binding.listaMensajes.setLayoutManager(layoutManager);
        binding.listaMensajes.setAdapter(adaptador);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.getMensajes().observe(this, mensajes -> {
            adaptador.actualizar(mensajes);
            if (mensajes != null && !mensajes.isEmpty()) {
                binding.listaMensajes.scrollToPosition(mensajes.size() - 1);
            }
        });

        binding.botonEnviar.setOnClickListener(vista -> enviar());
        binding.campoMensaje.setOnEditorActionListener((vista, accion, evento) -> {
            if (accion == EditorInfo.IME_ACTION_SEND) {
                enviar();
                return true;
            }
            return false;
        });
        binding.botonMicrofono.setOnClickListener(vista -> dictar());
    }

    private void dictar() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, R.string.chat_mic_no_disponible, Toast.LENGTH_LONG).show();
            return;
        }

        Intent voz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voz.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-BO");
        voz.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.chat_mic_prompt));
        voz.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);

        try {
            lanzadorVoz.launch(voz);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.chat_mic_no_disponible, Toast.LENGTH_LONG).show();
        }
    }

    private void enviar() {
        String texto = binding.campoMensaje.getText().toString();
        if (texto.trim().isEmpty()) {
            return;
        }
        binding.campoMensaje.setText("");
        viewModel.enviar(texto);
    }
}
