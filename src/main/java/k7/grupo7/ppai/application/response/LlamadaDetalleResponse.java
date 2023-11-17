package k7.grupo7.ppai.application.response;

import k7.grupo7.ppai.entities.Llamada;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LlamadaDetalleResponse {
    private int id;
    private String fecha;
    private String cliente;
    private String estado;
    private int duracion;
    private String descrpicionEncuesta;
    private String descrpicionPreguntas;
    private String respuestasSeleccionadas;

    public static LlamadaDetalleResponse from (Llamada llamada){
        return LlamadaDetalleResponse.builder()
                .id(llamada.getId())
                .fecha("2021-10-10")
                .build();

    }

}
