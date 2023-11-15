package k7.grupo7.ppai.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String descripcion;
    private Date fechaFinVigencia;
    @OneToMany(mappedBy = "encuesta")
    private ArrayList<Pregunta> preguntas;

    
    //Itera en todas las preguntas para saber si pertenecen a la encuesta
    public boolean esTuPregunta(Object pregunta){
        for (Object p : preguntas) {
            if (p == pregunta) {
                return true;
            }
        }
        return false;
        
    }
    
    //Imprime las preguntas de la encuesta
    public String mostrarPreguntas(){
        String preguntasString = "";
        for (Pregunta pregunta : this.preguntas){
            String preg =pregunta.getPregunta();
            preguntasString += preg + System.lineSeparator();
        }return preguntasString;
    }
    
    
 
}
