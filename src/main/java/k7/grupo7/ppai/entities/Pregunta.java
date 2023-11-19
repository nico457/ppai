package k7.grupo7.ppai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private List<RespuestaPosible> respuestasPosibles;



}
