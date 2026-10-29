package bo.feribot.app.ui.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import bo.feribot.app.data.RepositorioFeriBot;
import bo.feribot.app.domain.RespuestaFeriBot;

public class ChatViewModel extends AndroidViewModel {

    private final RepositorioFeriBot repositorio;
    private final MutableLiveData<List<Mensaje>> mensajes = new MutableLiveData<>();
    private final List<Mensaje> historial = new ArrayList<>();

    public ChatViewModel(@NonNull Application application) {
        super(application);
        repositorio = RepositorioFeriBot.obtener(application);
        mensajes.setValue(new ArrayList<>());
        repositorio.prepararDatos(() -> agregarBot(RespuestaFeriBot.saludo().mensaje));
    }

    public LiveData<List<Mensaje>> getMensajes() {
        return mensajes;
    }

    public void enviar(String texto) {
        String limpio = texto == null ? "" : texto.trim();
        if (limpio.isEmpty()) {
            return;
        }
        agregar(new Mensaje(true, limpio));
        repositorio.responder(limpio, respuesta -> agregarBot(respuesta.mensaje));
    }

    private void agregarBot(String texto) {
        agregar(new Mensaje(false, texto));
    }

    private void agregar(Mensaje mensaje) {
        historial.add(mensaje);
        mensajes.setValue(new ArrayList<>(historial));
    }
}
