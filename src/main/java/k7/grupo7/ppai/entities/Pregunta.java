package k7.grupo7.ppai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String pregunta;


    @ManyToOne
    @JoinColumn(name = "encuesta_id")
    @JsonIgnore
    private Encuesta encuesta;

    @OneToMany(mappedBy = "pregunta")
    private ArrayList<RespuestaPosible> respuestasPosibles;


    //Itera en todas las respuestas posibles para saber si pertenecen a la pregunta
    public boolean esTuRespuesta(Object respuestaPosible) {
        for (Object r : respuestasPosibles) {
            if (r == respuestaPosible) {
                return true;
            }
        }
        return false;
    }

}
