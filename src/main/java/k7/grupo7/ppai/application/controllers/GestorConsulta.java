package k7.grupo7.ppai.application.controllers;

import k7.grupo7.ppai.application.response.LlamadaDetalleResponse;
import k7.grupo7.ppai.application.response.LlamadaResponse;
import k7.grupo7.ppai.entities.IAgregado;
import k7.grupo7.ppai.entities.IIterador;
import k7.grupo7.ppai.entities.IteradorLlamada;
import k7.grupo7.ppai.entities.Llamada;
import k7.grupo7.ppai.repository.LlamadaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/llamadas")
public class GestorConsulta implements IAgregado<Llamada> {
    private LlamadaRepository llamadaRepository;
    private List<Llamada> llamadas;
    private List<Llamada> llamadasPeriodo;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public GestorConsulta(LlamadaRepository llamadaRepository) {
        this.llamadaRepository = llamadaRepository;
        this.llamadasPeriodo = new ArrayList<>();
    }

//    @GetMapping
//    public ResponseEntity<List<LlamadaResponse>> getAll(){
//        List<Llamada> llamadas= llamadaRepository.findAll();
//        return ResponseEntity.ok(llamadas.stream().map(LlamadaResponse::from).toList());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<LlamadaDetalleResponse> getById(@PathVariable Integer id){
        Llamada llamada = llamadaRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(LlamadaDetalleResponse.from(llamada));
    }


    @GetMapping("/{id}/csv")
    public ResponseEntity<byte[]> generarCsv(@PathVariable Integer id) {
        Llamada llamada = llamadaRepository.findById(id).orElseThrow();

        try {
            String archivo = "archivo.csv";
            FileWriter fileWriter = new FileWriter(archivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.println("ID: " + llamada.getId());

            printWriter.flush();
            printWriter.close();

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

    @GetMapping
    public List<Llamada> tomarPeriodo(@RequestParam String desde, @RequestParam String hasta){
        //desde = desde + " 00:00:00";
        this.fechaDesde = LocalDate.parse(desde);
        //hasta = hasta + " 00:00:00";
        this.fechaHasta = LocalDate.parse(hasta);
        this.llamadas = llamadaRepository.findAll();
        buscarLlamadasConEncuestasEnviadas(llamadas);
        return llamadasPeriodo;
    }

    public void buscarLlamadasConEncuestasEnviadas(List<Llamada> llamadas){

        IIterador<Llamada> iterator = crearIterador(llamadas);
        iterator.primero();
        while (!iterator.haTerminado()){
            iterator.actual();
            if (iterator.cumpleFiltros()){
                llamadasPeriodo.add((Llamada) iterator.actual());
            }
            iterator.siguiente();
        }

    }

    @Override
    public IIterador<Llamada> crearIterador(List<Llamada> elementos) {
        return new IteradorLlamada(llamadas, List.of(fechaDesde,fechaHasta));
    }
}
