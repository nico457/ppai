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

        return fecha.isAfter(fechaInicio.minusDays(1)) && fecha.isBefore(fechaFin.plusDays(1));

    }

    public String getNombreEstado(){
        for (CambioEstado cambioEstado : cambiosEstado) {
            if (cambioEstado.esUltimoEstado(cambiosEstado)) {
                return cambioEstado.getEstado().getNombre();
            }
        }
        return "";

    }

    public String[][] getRespuestas(){
        String[][] respuestas = new String[respuestasDeCliente.size()][3];
        IIterador<RespuestaDeCliente> iterador = crearIterador(respuestasDeCliente);
        iterador.primero();
        int filas = 0;
        while (!iterador.haTerminado()){

            RespuestaDeCliente actual = iterador.actual();
            List<String> descripciones = actual.getDescripcionRta();
            respuestas[filas][0] = descripciones.get(0);
            respuestas[filas][1] = descripciones.get(1);
            respuestas[filas][2] = descripciones.get(2);
            iterador.siguiente();
            filas++;
            }

        return respuestas;
    }

    @Override
    public IIterador<RespuestaDeCliente> crearIterador(List<RespuestaDeCliente> elementos) {
        return new IteradorRespuestaDeCliente(respuestasDeCliente);
    }


}
