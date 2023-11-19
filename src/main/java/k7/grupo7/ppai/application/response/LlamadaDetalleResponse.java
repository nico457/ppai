package k7.grupo7.ppai.application.response;

import k7.grupo7.ppai.entities.Llamada;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LlamadaDetalleResponse {
    private String cliente;
    private String estado;
    private int duracion;
    private String descripcionEncuesta;
    private List<String> descripcionPreguntas;
    private List<String> respuestasSeleccionadas;


    public LlamadaDetalleResponse(String nombreCliente, String nombreEstado, int duracion){
        this.cliente = nombreCliente;
        this.estado = nombreEstado;
        this.duracion = duracion;
        this.descripcionPreguntas = new ArrayList<>();
        this.respuestasSeleccionadas = new ArrayList<>();
    }

    public void setResponse(String[][] matriz){
        descripcionEncuesta = matriz[0][0];
        for (String[] strings : matriz) {
            descripcionPreguntas.add(strings[1]);
            respuestasSeleccionadas.add(strings[2]);
        }
        }
}
