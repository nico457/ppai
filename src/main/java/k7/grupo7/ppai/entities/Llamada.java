package k7.grupo7.ppai.entities;

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
public class Llamada implements IAgregado<RespuestaDeCliente> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "llamada")
    private List<RespuestaDeCliente> respuestasDeCliente;

    @OneToMany(mappedBy = "llamada")
    private List<CambioEstado> cambiosEstado;

    @OneToOne
    private Cliente cliente;

    private int duracion;

    private boolean encuestaEnviada;

    
    //Consulta si esta llamada tiene una encuesta enviada (true or false)
    public boolean tieneEncuestaEnviada() {
        if (!respuestasDeCliente.isEmpty()) {
            this.encuestaEnviada = true;
            return true;
        } else {
            this.encuestaEnviada = false;
            return false;
        }
    }
    
    //Busca las llamadas iniciadas en el periodo comprendido entre las 2 fechas ingresadas por el usuario
   public boolean esDePeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
       CambioEstado cambioEstadoInicial = null;
       for (CambioEstado cambioEstado : this.cambiosEstado) {
            if (cambioEstado.esEstadoInicial(this.cambiosEstado)) {
                cambioEstadoInicial = cambioEstado;
            }
            
        }
        LocalDate fecha = cambioEstadoInicial.getFechaHoraInicio();
        return fecha.isAfter(fechaInicio) && fecha.isBefore(fechaFin);

    }
    
    //Imprime las respuestas
    public String mostrarRespuestasSeleccionadas(){
        String respuestasClientesString = "";
        for (RespuestaDeCliente respuesta : this.respuestasDeCliente){
            String res =respuesta.getRespuestaSeleccionada().getDescripcion();
            respuestasClientesString += res + " \n ";
        }return respuestasClientesString;
    }

    @Override
    public IIterador<RespuestaDeCliente> crearIterador(List<RespuestaDeCliente> elementos) {
        return new IteradorRespuestaDeCliente(respuestasDeCliente);
    }
}
