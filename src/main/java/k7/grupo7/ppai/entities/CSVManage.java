package k7.grupo7.ppai.entities;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JOptionPane;

public class CSVManage {


    public static void writeCSV(Llamada llamada,String estado, List<String> preguntas,String archivo){
        try {
            FileWriter fileWriter = new FileWriter(archivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(llamada.getCliente().getNombreCompleto() + ", " + estado + ", " + llamada.getDuracion() );
            printWriter.println("");
            for (int i = 0; i< preguntas.size(); i++) {
                List<RespuestaDeCliente> respuestasDeCliente= llamada.getRespuestasDeCliente();
                printWriter.println(preguntas.get(i) +" , "+ respuestasDeCliente.get(i).getRespuestaSeleccionada().getDescripcion());
            }
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error al generar el CSV");
            System.out.println(e);
        }
    }

}
