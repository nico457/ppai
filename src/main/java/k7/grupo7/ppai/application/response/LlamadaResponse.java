package k7.grupo7.ppai.application.response;

import k7.grupo7.ppai.entities.Llamada;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LlamadaResponse {
    private int id;
    private String fecha;

    public static LlamadaResponse from (Llamada llamada){
        return LlamadaResponse.builder()
                .id(llamada.getId())
                .fecha("2021-10-10")
                .build();

    }
}

