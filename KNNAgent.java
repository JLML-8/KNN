
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author luis_
 */
public class KNNAgent extends Agent {

    boolean seguir = true;
    private double x1 = 0;
    private double x2 = 0;
    private int vecinos = 0;

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started ");
        addBehaviour(new KNNBehaviour());

    }

    private class OptionPaneX {

        JFrame f;

        OptionPaneX() {
            f = new JFrame();
            x1 = Double.parseDouble(JOptionPane.showInputDialog(f, "Ingresa el valor de X1 para clasificar el nuevo dato"));
            x2 = Double.parseDouble(JOptionPane.showInputDialog(f, "Ingresa el valor de X2 para clasificar el nuevo dato"));
            vecinos = Integer.parseInt(JOptionPane.showInputDialog(f, "Ingresa el número de vecinos"));
        }
    }

    private class KNNBehaviour extends Behaviour {

        public void action() {
            System.out.println("Agent's action method is executed");
            DataLoader data = new DataLoader("C:\\Users\\luis_\\Documents\\plantas.txt");
            ArrayList<Dato> datos = data.leerDatos();
            KNN knn = new KNN(datos);
            int option = JOptionPane.showConfirmDialog(null, "¿Las unidades de medida son diferentes?");
            System.out.println(option);
            if (option == 0) {
                knn.estandarizar();
            }
            new OptionPaneX();
            Dato nuevo = new Dato(x1, x2, "");
            knn.clasificarDato(nuevo, vecinos);
            int seguirAux = JOptionPane.showConfirmDialog(null, "¿Quiere clasficar otro dato?");
            if (seguirAux != 0) {
                seguir = false;
            }
        }

        public boolean done() {
            if (seguir) {
                return false;
            } else {
                return true;
            }
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }

}
