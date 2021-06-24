/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luis_
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataLoader {

    private String direccion;

    public DataLoader(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Dato> leerDatos() {
        ArrayList<Dato> datos = new ArrayList();
        File file = new File(direccion);
        double x1, x2;
        String label;
        try (Scanner entrada = new Scanner(file)) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 17; j++) {
                    if (entrada.hasNextDouble()) {
                        x1 = entrada.nextDouble();
                        x2 = entrada.nextDouble();
                        label = entrada.next();
                        datos.add(new Dato(x1, x2, label));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo solicitado");
        }
        return datos;
    }
}
