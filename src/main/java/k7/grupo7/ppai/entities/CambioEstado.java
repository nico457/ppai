
package k7.grupo7.ppai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CambioEstado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate fechaHoraInicio;
    @OneToOne
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "llamada_id")
    @JsonIgnore
    private Llamada llamada;
    

    
    
    //Busca el ultimo estado en ser asignado, comparando la fecha y hora de su inicio
    public boolean esUltimoEstado(ArrayList<CambioEstado> cambiosEstado){
        boolean bandera = false;
        for (CambioEstado cambioEstado:cambiosEstado){
             if(this.fechaHoraInicio.compareTo(cambioEstado.getFechaHoraInicio()) >= 0){
                 bandera = true;
             }else{
                 bandera = false;
             }
        }
       
        return bandera;
    }
    
    //Pregunta si el estado es "Iniciada"
    public boolean esIniciada(ArrayList<CambioEstado> cambiosEstado){
        boolean bandera = false;
        for (CambioEstado cambioEstado:cambiosEstado){
             if(this.fechaHoraInicio.compareTo(cambioEstado.getFechaHoraInicio()) <= 0){
                 bandera = true;
             }else{
                 bandera = false;
             }
        }
       
        return bandera;
    }
    
    
}
