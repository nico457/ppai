package k7.grupo7.ppai.entities;

import java.util.List;

    public interface IAgregado<T> {
        IIterador<T> crearIterador(List<T> elementos);
    }

