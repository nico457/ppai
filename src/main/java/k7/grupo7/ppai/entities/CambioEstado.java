
package k7.grupo7.ppai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public boolean esUltimoEstado(List<CambioEstado> cambiosEstado){
        boolean bandera = false;
        LocalDate mayor = cambiosEstado.get(0).fechaHoraInicio;
        for (int i = 0; i < cambiosEstado.size(); i++){
            if(this.fechaHoraInicio.isAfter(mayor) || this.fechaHoraInicio.equals(mayor)){
                mayor = this.fechaHoraInicio;
                bandera = true;
            }
        }
       
        return bandera;
    }
    
    //Pregunta si el estado es "Iniciada"
    public boolean esEstadoInicial(List<CambioEstado> cambiosEstado){
        boolean bandera = false;
        LocalDate menor = cambiosEstado.get(0).fechaHoraInicio;
        for (int i = 0; i < cambiosEstado.size(); i++){
             if(this.fechaHoraInicio.isBefore(menor) || this.fechaHoraInicio.equals(menor)){
                 menor = this.fechaHoraInicio;
                 bandera = true;
             }
        }
//        CambioEstado cambioEstado:cambiosEstado
        return bandera;
    }
    
    
}
