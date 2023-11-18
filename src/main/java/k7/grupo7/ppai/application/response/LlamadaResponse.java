package k7.grupo7.ppai.application.response;

import k7.grupo7.ppai.entities.Llamada;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class LlamadaResponse {
    private int id;
    private String fecha;
    private String cliente;
    private ArrayList<String> respuestas;


    public static LlamadaResponse from (Llamada llamada){
//        return LlamadaResponse.builder()
//                .id(llamada.getId())
//                .fecha(llamada.getCambiosEstado().get(0).getFechaHoraInicio().toString())
//                .cliente(llamada.getCliente().getNombreCompleto())
//                .build();
        LlamadaResponseBuilder llamadaResponseBuilder = LlamadaResponse.builder()
                .id(llamada.getId())
                .fecha(llamada.getCambiosEstado().get(0).getFechaHoraInicio().toString())
                .cliente(llamada.getCliente().getNombreCompleto());
        if (!llamada.getRespuestasDeCliente().isEmpty()){
            ArrayList<String> descripciones = new ArrayList<>();
            for (int i = 0; i < llamada.getRespuestasDeCliente().size(); i++) {
                descripciones.add(llamada.getRespuestasDeCliente().get(i).getRespuestaSeleccionada().getDescripcion());
            }
            llamadaResponseBuilder.respuestas(descripciones);
        }
        else {
            llamadaResponseBuilder.respuestas(null);
        }

        return llamadaResponseBuilder.build();


    }
}

