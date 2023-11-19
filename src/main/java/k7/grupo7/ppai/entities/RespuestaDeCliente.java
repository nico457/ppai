
package k7.grupo7.ppai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespuestaDeCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate fechaEncuesta;
    @OneToOne
    private RespuestaPosible respuestaSeleccionada;

    @ManyToOne
    @JoinColumn(name = "llamada_id")
    @JsonIgnore
    private Llamada llamada;


    public List<String> getDescripcionRta() {
        String respuesta = respuestaSeleccionada.getDescripcion();
        String pregunta = respuestaSeleccionada.getPregunta().getPregunta();
        String encuesta = respuestaSeleccionada.getPregunta().getEncuesta().getDescripcion();
        return List.of(encuesta, pregunta, respuesta);
    }

}
