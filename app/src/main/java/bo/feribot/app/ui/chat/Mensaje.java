package bo.feribot.app.ui.chat;

/**
 * Mensaje mostrado en el chat.
 */
public class Mensaje {

    public final boolean deUsuario;
    public final String texto;

    public Mensaje(boolean deUsuario, String texto) {
        this.deUsuario = deUsuario;
        this.texto = texto;
    }
}
