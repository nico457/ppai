package k7.grupo7.ppai.entities;

import java.time.LocalDate;
import java.util.List;

public class IteradorLlamada implements IIterador{

    private List<Llamada> elementos;
    private int actual;
    private List<Object> filtros;

    public IteradorLlamada(List<Llamada> elementos, List<Object> filtros) {
        this.elementos = elementos;
        this.filtros = filtros;
    }

    @Override
    public void primero() {
        actual = 0;
    }

    @Override
    public boolean haTerminado() {
        return actual >= elementos.size();
    }

    @Override
    public void siguiente() {
        actual++;
    }

    @Override
    public Llamada actual() {
        return (Llamada) elementos.get(actual);
    }

    @Override
    public boolean cumpleFiltros() {
        Llamada ll = actual();
        LocalDate inicio = (LocalDate) filtros.get(0);
        LocalDate fin = (LocalDate) filtros.get(1);

        return ll.esDePeriodo(inicio, fin) && ll.tieneEncuestaEnviada();
    }
}
