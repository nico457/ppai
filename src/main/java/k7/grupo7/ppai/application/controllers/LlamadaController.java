package k7.grupo7.ppai.application.controllers;

import jakarta.annotation.Resource;

import k7.grupo7.ppai.application.response.LlamadaDetalleResponse;
import k7.grupo7.ppai.application.response.LlamadaResponse;
import k7.grupo7.ppai.entities.Llamada;
import k7.grupo7.ppai.repository.LlamadaRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/llamadas")
public class LlamadaController {
    private LlamadaRepository llamadaRepository;

    public LlamadaController(LlamadaRepository llamadaRepository) {
        this.llamadaRepository = llamadaRepository;
    }
    @GetMapping
    public ResponseEntity<List<LlamadaResponse>> getAll(){
        List<Llamada> llamadas= llamadaRepository.findAll();
        return ResponseEntity.ok(llamadas.stream().map(LlamadaResponse::from).toList());
    }
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



}
