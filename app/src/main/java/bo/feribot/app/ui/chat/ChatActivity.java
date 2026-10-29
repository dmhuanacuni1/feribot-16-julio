package bo.feribot.app.ui.chat;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import bo.feribot.app.R;
import bo.feribot.app.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatViewModel viewModel;
    private ChatAdapter adaptador;

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
        binding.botonMicrofono.setOnClickListener(vista ->
                Toast.makeText(this, R.string.chat_mic_proximamente, Toast.LENGTH_SHORT).show());
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
