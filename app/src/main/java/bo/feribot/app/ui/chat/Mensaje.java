package bo.feribot.app.ui.chat;

import bo.feribot.app.domain.RespuestaFeriBot;

/**
 * Mensaje mostrado en el chat. Cuando viene del bot conserva la respuesta
 * estructurada para poder ofrecer acciones como "Ver en Mapa".
 */
public class Mensaje {

    public final boolean deUsuario;
    public final String texto;
    public final RespuestaFeriBot respuesta;

    public Mensaje(boolean deUsuario, String texto) {
        this(deUsuario, texto, null);
    }

    public Mensaje(boolean deUsuario, String texto, RespuestaFeriBot respuesta) {
        this.deUsuario = deUsuario;
        this.texto = texto;
        this.respuesta = respuesta;
    }
}
