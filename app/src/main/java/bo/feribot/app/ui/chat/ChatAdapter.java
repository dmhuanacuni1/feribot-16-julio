package bo.feribot.app.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bo.feribot.app.R;
import bo.feribot.app.domain.RespuestaFeriBot;
import bo.feribot.app.ui.foto.FotoActivity;
import bo.feribot.app.ui.mapa.MapaActivity;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MensajeViewHolder> {

    private static final int TIPO_USUARIO = 1;
    private static final int TIPO_BOT = 0;

    private final List<Mensaje> mensajes = new ArrayList<>();

    public void actualizar(List<Mensaje> nuevos) {
        mensajes.clear();
        mensajes.addAll(nuevos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mensajes.get(position).deUsuario ? TIPO_USUARIO : TIPO_BOT;
    }

    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = viewType == TIPO_USUARIO
                ? R.layout.item_mensaje_usuario
                : R.layout.item_mensaje_bot;
        View vista = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MensajeViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
        Mensaje mensaje = mensajes.get(position);
        holder.texto.setText(mensaje.texto);

        if (holder.botonMapa == null) {
            return;
        }

        RespuestaFeriBot respuesta = mensaje.respuesta;
        boolean tieneUbicacion = respuesta != null
                && respuesta.latitud != null
                && respuesta.longitud != null;
        boolean tieneProducto = respuesta != null
                && respuesta.productoId != null;

        if (tieneUbicacion) {
            holder.botonMapa.setVisibility(View.VISIBLE);
            holder.botonMapa.setOnClickListener(vista ->
                    MapaActivity.abrir(vista.getContext(), respuesta));
        } else {
            holder.botonMapa.setVisibility(View.GONE);
            holder.botonMapa.setOnClickListener(null);
        }

        if (tieneProducto) {
            holder.botonFoto.setVisibility(View.VISIBLE);
            holder.botonFoto.setOnClickListener(vista ->
                    FotoActivity.abrir(vista.getContext(), respuesta));
        } else {
            holder.botonFoto.setVisibility(View.GONE);
            holder.botonFoto.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    static class MensajeViewHolder extends RecyclerView.ViewHolder {

        final TextView texto;
        final Button botonMapa;
        final Button botonFoto;

        MensajeViewHolder(@NonNull View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.textoMensaje);
            botonMapa = itemView.findViewById(R.id.botonMapa);
            botonFoto = itemView.findViewById(R.id.botonFoto);
        }
    }
}
