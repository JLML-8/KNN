
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luis_
 */
public class MultipleLinearRegression {
    
    private double b0; 
    private double b1; 
    private double b2;
    
    public void metodoMatricial(double[][] datos)
    {
            //cargamos los datos
            Matriz ej = new Matriz(datos, datos.length, datos[0].length);
            double[][] matrizY = ej.getDatosY();
            double[][] transp = ej.transpuesta();
            System.out.println("Imprimiendo producto de transpuesta por la matriz principal");
            //multiplicamos la transpuesta por la matriz principal
            double[][] prodTransp = ej.multiplicarMatrices(transp, datos);
            //le sacamos la determinante a el producto de la transpuesta por la original
            double determ = ej.determinante3x3(prodTransp);
            //obtenemos la matriz adjunta del producto de la transpuesta por la original
            double[][] adjunta = ej.adjunta3x3(prodTransp);
            //Obtenemos la inversa con la adjunta y la determinante
            double[][] inversa = ej.matrizXEscalar(adjunta, 1 / determ);
            //multiplicamos los datos de la variable dependiente por la transpuesta
            double[][] productoXtY = ej.multiplicarMatrices(transp, matrizY);
            //multiplicamos la matriz inversa por el producto de la transpuesta por yi 
            double[][] resultado = ej.multiplicarMatrices(inversa, productoXtY);
            //y este es el resultado de cada variable
            b0 = resultado[0][0];
            b1 = resultado[1][0];
            b2 = resultado[2][0];
            
            System.out.println("y_hat = "+b0+"+"+b1+"X1"+b2+"X2");       
    }
    
    public void cramer(double[][] matrizDatos)
    {
        ArrayList<Double> x1 = new ArrayList();
        ArrayList<Double> x2 = new ArrayList();
        ArrayList<Double> y= new ArrayList();
        //descomponemos la matriz en 3 vectores independientes
        for(int i=0; i<matrizDatos.length;i++)
        {
            for(int j=0; j<matrizDatos[0].length-2; j++)
            {
                x1.add(matrizDatos[i][j]);
                x2.add(matrizDatos[i][j+1]);
                y.add(matrizDatos[i][j+2]);
            }
        }
        //creamos las sumatorias de cada uno para los cálculos
        Sumatoria sumx1 = new Sumatoria(x1);
        Sumatoria sumx2 = new Sumatoria(x2);
        Sumatoria sumY = new Sumatoria(y);
        //obtenemos los coeficientes para el sistema de ecuaciones
        double totalX1 = sumx1.obtenerSum();
        double totalX2 = sumx2.obtenerSum();
        double totalY = sumY.obtenerSum();
        double sumX1X2 = sumx1.sumarProductos(x2);
        double sumX1Y = sumx1.sumarProductos(y);
        double sumX1cuad = sumx1.sumarProductos(x1);
        double sumX2cuad = sumx2.sumarProductos(x2);
        double sumX2Y = sumx2.sumarProductos(y);
       

        double[][] matriz = new double[3][4];
        //primera ecuación
        matriz[0][0]= totalX1;
        matriz[0][1] = totalX2; 
        matriz[0][2] = y.size(); 
        matriz[0][3] = totalY; //igual a 
        
        matriz[1][0] = sumX1cuad;
        matriz[1][1] = sumX1X2;
        matriz[1][2] = totalX1;
        matriz[1][3] = sumX1Y; //igual a
        
        matriz[2][0] = sumX1X2;
        matriz[2][1] = sumX2cuad;
        matriz[2][2] = totalX2;
        matriz[2][3] = sumX2Y;//igual a
        
        double[][] determSistema = new double[3][3];
        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++)
            {
                determSistema[i][j] = matriz[i][j];

            }
        }
        //obtenemos la determinante del sistema mediante sarrus
        Sarrus sarrus = new Sarrus();
        double determinanteSistema = sarrus.determinante3x3(determSistema);
        //despues la de x1
        double[][] determX1 = new double[3][3];
        //se sustituyen los terminos de x1 por los terminos independendientes para la determinante
        determX1[0][0] = matriz[0][3];
        determX1[1][0] = matriz[1][3];
        determX1[2][0] = matriz[2][3];
        
        for(int i=0; i<3; i++)
        {
            for(int j=1; j<3; j++)
            {
                determX1[i][j] = matriz[i][j];
            }
        }
        //ahora se sustituyen los de x2 por los T.I
        double determinanteX1 = sarrus.determinante3x3(determX1);
        double[][] determX2 = new double[3][3];
        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++)
            {
                determX2[i][j] = matriz[i][j];
            }
        }
        determX2[0][1] = matriz[0][3];
        determX2[1][1] = matriz[1][3];
        determX2[2][1] = matriz[2][3];
        
        double determinanteX2 = sarrus.determinante3x3(determX2);
        //por ultimo resolvemos los de la ultima columna
        double[][] determY = new double[3][3];
        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++)
            {
                determY[i][j] = matriz[i][j];
            }
        }
        determY[0][2]=matriz[0][3];
        determY[1][2]=matriz[1][3];
        determY[2][2]=matriz[2][3];
        
        double determinanteY = sarrus.determinante3x3(determY);
        
        double resultadoBeta1 = determinanteX1/determinanteSistema;
        double resultadoBeta2 = determinanteX2/determinanteSistema;
        double resultadoBeta0 = determinanteY/determinanteSistema;
        this.b0 = resultadoBeta0;
        this.b1 = resultadoBeta1;
        this.b2 = resultadoBeta2;
        
        System.out.println("Resultado beta0= "+resultadoBeta0);
        System.out.println("Resultado beta1= "+resultadoBeta1);
        System.out.println("Resultado beta2= "+resultadoBeta2);
        System.out.println("y_hat = "+b0+"+"+b1+"X1"+b2+"X2");
            

    }
    
    public void calcularYCramer(double x_1, double x_2)
    {
        double resultadoY = b0+((b1)*(x_1))+((b2)*(x_2));
        System.out.println("y_hat = "+b0+" + ("+b1+")("+x_1+")+("+b2+")("+x_2+") = "+resultadoY);
        
    }
    
        public void calcularYMatricial(double x_1, double x_2)
    {
        double resultadoY = b0+((b1)*(x_1))+((b2)*(x_2));
        System.out.println("y_hat = "+b0+" + ("+b1+")("+x_1+")+("+b2+")("+x_2+") = "+resultadoY);
        
    }
    
}
