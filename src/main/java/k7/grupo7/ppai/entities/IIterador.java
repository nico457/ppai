package k7.grupo7.ppai.entities;


public interface IIterador<T> {

    void primero();
    boolean haTerminado();
    void siguiente();
    T actual();
    boolean cumpleFiltros();
}
