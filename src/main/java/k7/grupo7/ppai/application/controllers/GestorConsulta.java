package k7.grupo7.ppai.application.controllers;
import k7.grupo7.ppai.application.response.LlamadaDetalleResponse;
import k7.grupo7.ppai.application.response.LlamadaResponse;
import k7.grupo7.ppai.entities.*;
import k7.grupo7.ppai.repository.LlamadaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/llamadas")
public class GestorConsulta implements IAgregado<Llamada> {
    private LlamadaRepository llamadaRepository;
    private List<Llamada> llamadas;
    private List<Llamada> llamadasPeriodo;
    private Llamada llamadaSeleccionada;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LlamadaDetalleResponse detalleLlamada;

    public GestorConsulta(LlamadaRepository llamadaRepository) {
        this.llamadaRepository = llamadaRepository;
        this.llamadasPeriodo = new ArrayList<>();
    }

    @GetMapping
    public ResponseEntity<List<LlamadaResponse>> tomarPeriodo(@RequestParam String desde, @RequestParam String hasta){
        this.fechaDesde = LocalDate.parse(desde);
        this.fechaHasta = LocalDate.parse(hasta);

        if (fechaHasta.isBefore(fechaDesde)) {
            return ResponseEntity.badRequest().build();
        }

        this.llamadas = llamadaRepository.findAll();
        llamadasPeriodo = buscarLlamadasConEncuestasEnviadas(llamadas);
        List<LlamadaResponse> res = llamadasPeriodo.stream()
                .map(LlamadaResponse::from)
                .toList();
        return ResponseEntity.ok(res);
    }

    public List<Llamada> buscarLlamadasConEncuestasEnviadas(List<Llamada> llamadas){
        List<Llamada> llamadasFiltradas = new ArrayList<>();
        IIterador<Llamada> iterator = crearIterador(llamadas);
        iterator.primero();
        while (!iterator.haTerminado()){
            iterator.actual();
            if (iterator.cumpleFiltros()){
                llamadasFiltradas.add(iterator.actual());
            }
            iterator.siguiente();
        }
        return llamadasFiltradas;

    }

    @GetMapping("/{id}")
    public ResponseEntity<LlamadaDetalleResponse> tomarSeleccionDeLlamada(@PathVariable Integer id){

        try {
            llamadaSeleccionada = llamadaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No existe llamada con esa ID."));
            obtenerDatosLlamada();
             return ResponseEntity.ok(detalleLlamada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }


    }


    public void obtenerDatosLlamada() {


        //Buscamos el nombre del cliente y la duracion de la llamada seleccionada
        String nombreCliente = this.llamadaSeleccionada.getCliente().getNombreCompleto();
        //Obtenemos el estado de la llamada
        String nombreEstado = llamadaSeleccionada.getNombreEstado();
        int duracion = this.llamadaSeleccionada.getDuracion();
        detalleLlamada = new LlamadaDetalleResponse(nombreCliente,nombreEstado,duracion);
        obtenerDatosEncuesta();


    }

    public void obtenerDatosEncuesta(){
        String[][] respuestas =llamadaSeleccionada.getRespuestas();
        detalleLlamada.setResponse(respuestas);

    }



    @GetMapping("/{id}/csv")
    public ResponseEntity<byte[]> generarCsv(@PathVariable Integer id) {

        tomarSeleccionDeLlamada(id);

        try {
            String archivo = "archivo.csv";

            CSVManage.writeCSV(llamadaSeleccionada,detalleLlamada.getEstado(), detalleLlamada.getDescripcionPreguntas(), archivo);


            // Leer contenido del archivo en un byte array
            Path path = Paths.get(archivo);
            byte[] contenido = Files.readAllBytes(path);

            // Configuración de la respuesta para abrir el archivo automáticamente
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=archivo.csv");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(contenido.length)
                    .body(contenido);

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public IIterador<Llamada> crearIterador(List<Llamada> elementos) {
        return new IteradorLlamada(llamadas, List.of(fechaDesde,fechaHasta));
    }

}
