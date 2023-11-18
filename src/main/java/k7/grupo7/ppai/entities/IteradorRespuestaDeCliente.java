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

    }

    @Override
    public boolean haTerminado() {
        return false;
    }

    @Override
    public void siguiente() {

    }

    @Override
    public RespuestaDeCliente actual() {
        return null;
    }

    @Override
    public boolean cumpleFiltros() {
        return true;
    }
}
