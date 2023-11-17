package k7.grupo7.ppai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Llamada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
   @Transient
    @OneToMany(mappedBy = "llamada")
    private ArrayList<RespuestaDeCliente> respuestasDeCliente;

    @Transient
    @OneToMany(mappedBy = "llamada")
    private List<CambioEstado> cambiosEstado;
    @OneToOne
    private Cliente cliente;
    private int duracion;
    private boolean encuestaEnviada;
    
    

    
    //Consulta si esta llamada tiene una encuesta enviada (true or false)
    public boolean tieneEncuestaEnviada() {
        if (respuestasDeCliente != null) {
            this.encuestaEnviada = true;
            return true;
        } else {
            this.encuestaEnviada = false;
            return false;
        }
    }
    
    //Busca las llamadas iniciadas en el periodo comprendido entre las 2 fechas ingresadas por el usuario
   /* public boolean esDePeriodo(Date fechaInicio, Date fechaFin, Llamada llamada) {
        CambioEstado cambioEstadoInicial = null;
        for (CambioEstado cambioEstado : this.cambiosEstado) {
            if (cambioEstado.esIniciada(this.cambiosEstado)) {
                cambioEstadoInicial = cambioEstado;
            }
            
        }
        Date fecha = cambioEstadoInicial.getFechaHoraInicio();
        return fecha.compareTo(fechaInicio) >= 0 && fecha.compareTo(fechaFin) <= 0;

    }
    
    //Imprime las respuestas
    public String mostrarRespuestasSeleccionadas(){
        String respuestasClientesString = "";
        for (RespuestaDeCliente respuesta : this.respuestasDeCliente){
            String res =respuesta.getRespuestaSeleccionada().getDescripcion();
            respuestasClientesString += res + " \n ";
        }return respuestasClientesString;
    }
*/
}
