package k7.grupo7.ppai.application.response;

import k7.grupo7.ppai.entities.Llamada;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class LlamadaResponse {
    private int id;
    private String fecha;

    public static LlamadaResponse from (Llamada llamada){
        LlamadaResponse.LlamadaResponseBuilder llamadaResponseBuilder = LlamadaResponse.builder()
                .id(llamada.getId());
        LocalDate menor = llamada.getCambiosEstado().get(0).getFechaHoraInicio();
        for (int i = 0; i < llamada.getCambiosEstado().size(); i++) {
            if(llamada.getCambiosEstado().get(i).getFechaHoraInicio().isBefore(menor) ||
                    llamada.getCambiosEstado().get(i).getFechaHoraInicio().isEqual(menor)){
                menor = llamada.getCambiosEstado().get(i).getFechaHoraInicio();
            }
        }
        llamadaResponseBuilder.fecha(menor.toString());
        return llamadaResponseBuilder.build();
    }


}

