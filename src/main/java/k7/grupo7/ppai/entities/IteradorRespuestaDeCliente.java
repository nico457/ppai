package k7.grupo7.ppai.entities;

import java.util.List;

public class IteradorRespuestaDeCliente implements IIterador<RespuestaDeCliente>{

    private List<RespuestaDeCliente> elementos;
    private int actual;

    public IteradorRespuestaDeCliente(List<RespuestaDeCliente> elementos) {
        this.elementos = elementos;
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
    public RespuestaDeCliente actual() {
        return elementos.get(actual);
    }

    @Override
    public boolean cumpleFiltros() {
        return true;
    }
}
