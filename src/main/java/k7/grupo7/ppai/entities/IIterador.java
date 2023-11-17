package k7.grupo7.ppai.entities;

import java.time.LocalDate;

public interface IIterador {

    void primero();
    boolean haTerminado();
    void siguiente();
    Object actual();
    boolean cumpleFiltros(LocalDate fechaInicioPeriodo, LocalDate fechaFinPerido, Llamada llamada);
}
