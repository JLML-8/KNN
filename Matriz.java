
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luis_
 */
public class Matriz {

    private double[][] matriz;
    private double[][] datosY;
    private int filas = 0;
    private int columnas = 0;

    //constructor
    public Matriz(double[][] matriz, int filas, int columnas) {
        this.matriz = new double[filas][columnas];
        datosY = new double[filas][1];
        this.matriz = matriz;
        this.filas = filas;
        this.columnas = columnas;
        for(int i = 0; i<filas; i++)
        {
            datosY[i][0] = this.matriz[i][2];
        }
        for(int j=2; j>0; j--)
        {
            for(int i = 0; i<filas; i++)
                this.matriz[i][j] = this.matriz[i][j-1];
        }
        for(int i=0; i<this.filas; i++)
            this.matriz[i][0] = 1;
        System.out.println("Matriz original");
        printMatrix(this.matriz);
        System.out.println("Datos Y");
        printMatrix(datosY);
    }
    //regresa la transpuesta de la matriz original
    public double[][] transpuesta() {
        double[][] transpuesta = new double[columnas][filas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                transpuesta[j][i] = matriz[i][j];
            }
        }
        System.out.println("Imprimiendo transpuesta");
        printMatrix(transpuesta);
        return transpuesta;
    }
    //multiplica matriz a y b si sus dimensiones lo permiten
    public double[][] multiplicarMatrices(double[][] A, double[][] B)
    {
        /*if(matriz1.length!=matriz2[0].length || matriz1[0].length!=matriz2.length){
            System.out.println("Las matrices no se pueden multiplicar porque sus dimensiones no lo permiten");
            System.exit(0);
        }*/
        /*if(matriz1[0].length == matriz2.length)
        {
        int fila = matriz1.length;
        System.out.println("Filas de la matriz resultante "+fila);
        int column = matriz2[0].length;
        System.out.println("Columnas de la matriz resultante "+column);
        double[][] producto = new double[fila][column];
        for(int i=0; i<fila; i++)
        {
            for(int j = 0; j<column; j++)
            {
                for(int k=0; k<matriz1[0].length; k++)
                    producto[i][j] += matriz1[i][k] * matriz2[k][j];
            }
        }
        System.out.println("Imprimiendo producto");
        printMatrix(producto);
        return producto;
        }
        return null;*/
        int filasA = A.length;
        int columnasA = A[0].length;
        int filasB = B.length;
        int columnasB = B[0].length;
        
        if (columnasA != filasB){
            throw new IllegalArgumentException("La cantidad de columnas de la matriz A no es igual a la cantidad de filas de la matriz B.");
        }
        
        double[][] resultado = new double[filasA][columnasB];
        
        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < columnasA; k++) {
                    resultado[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        printMatrix(resultado);
        return resultado;

    }
    //te regresa la determinante de la matriz enviada siempre y cuando sea 3x3
    public double determinante3x3(double[][] matrix)
    {
        double[][] matrizAux = new double[3][5];
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            matrizAux[i][j] = matrix[i][j];  
        }
        matrizAux[0][3]=matrix[0][0];
        matrizAux[0][4]=matrix[0][1];
        matrizAux[1][3]=matrix[1][0];
        matrizAux[1][4]=matrix[1][1];
        matrizAux[2][3]=matrix[2][0];
        matrizAux[2][4]=matrix[2][1];
        
        double determ=0;
        double positivo=0;
        double negativo=0;
        
        positivo = ((matrizAux[0][0]*matrizAux[1][1]*matrizAux[2][2])+(matrizAux[0][1]*matrizAux[1][2]*matrizAux[2][3])+(matrizAux[0][2]*matrizAux[1][3]*matrizAux[2][4]));
        negativo = ((matrizAux[0][4]*matrizAux[1][3]*matrizAux[2][2])+(matrizAux[0][3]*matrizAux[1][2]*matrizAux[2][1])+(matrizAux[0][2]*matrizAux[1][1]*matrizAux[2][0]));
        determ=positivo-negativo;
        System.out.println("Imprimiendo determinante 3x3");
        printMatrix(matrizAux);
        System.out.println("Valor de la determinante: "+determ);
        return determ;
    }
    //regresa la determinante de una matrix 2x2
    public double determinante2x2(double[][] matrix)
    {
        double determin = (matrix[0][0]*matrix[1][1])-(matrix[0][1]*matrix[1][0]);
        return determin;
    }
    //regresa la matriz adjunta 
    public double[][] adjunta3x3(double[][] matrix)
    {
        double[][] A11 = new double[2][2];
        double[][] A12 = new double[2][2];
        double[][] A13 = new double[2][2];
        double[][] A21 = new double[2][2];
        double[][] A22 = new double[2][2];
        double[][] A23 = new double[2][2];
        double[][] A31 = new double[2][2];
        double[][] A32 = new double[2][2];
        double[][] A33 = new double[2][2];
        
         A11[0][0] = matrix[1][1];
         A11[0][1] = matrix[1][2];
         A11[1][0] = matrix[2][1];
         A11[1][1] = matrix[2][2];
        
         A12[0][0] = matrix[1][0];
         A12[0][1] = matrix[1][2];
         A12[1][0] = matrix[2][0];
         A12[1][1] = matrix[2][2];
         
         A13[0][0] = matrix[1][0];
         A13[0][1] = matrix[1][1];
         A13[1][0] = matrix[2][0];
         A13[1][1] = matrix[2][1];
         
         A21[0][0] = matrix[0][1];
         A21[0][1] = matrix[0][2];
         A21[1][0] = matrix[2][1];
         A21[1][1] = matrix[2][2];
         
         A22[0][0] = matrix[0][0];
         A22[0][1] = matrix[0][2];
         A22[1][0] = matrix[2][0];
         A22[1][1] = matrix[2][2];
         
         A23[0][0] = matrix[0][0];
         A23[0][1] = matrix[0][1];
         A23[1][0] = matrix[2][0];
         A23[1][1] = matrix[2][1];
         
         A31[0][0] = matrix[0][1];
         A31[0][1] = matrix[0][2];
         A31[1][0] = matrix[1][1];
         A31[1][1] = matrix[1][2];
         
         A32[0][0] = matrix[0][0];
         A32[0][1] = matrix[0][2];
         A32[1][0] = matrix[1][0];
         A32[1][1] = matrix[1][2];
         
         A33[0][0] = matrix[0][0];
         A33[0][1] = matrix[0][1];
         A33[1][0] = matrix[1][0];
         A33[1][1] = matrix[1][1];
         
         double[][] adjunta = new double[3][3];
         adjunta[0][0] = (int) Math.pow(-1, 1+1)*determinante2x2(A11);
         adjunta[0][1] = (int) Math.pow(-1, 1+2)*determinante2x2(A12);
         adjunta[0][2] = (int) Math.pow(-1, 1+3)*determinante2x2(A13);
         adjunta[1][0] = (int) Math.pow(-1, 2+1)*determinante2x2(A21);
         adjunta[1][1] = (int) Math.pow(-1, 2+2)*determinante2x2(A22);
         adjunta[1][2] = (int) Math.pow(-1, 2+3)*determinante2x2(A23);
         adjunta[2][0] = (int) Math.pow(-1, 3+1)*determinante2x2(A31);
         adjunta[2][1] = (int) Math.pow(-1, 3+2)*determinante2x2(A32);
         adjunta[2][2] = (int) Math.pow(-1, 3+3)*determinante2x2(A33);
         return adjunta;
    }
    //multiplica la matriz por un número 
    public double[][] matrizXEscalar(double[][] matrix, double escalar)
    {
        double[][] producto = new double[matrix.length][matrix[0].length];
        for(int i = 0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++)
            {
                producto[i][j] = matrix[i][j]*escalar;
            }
        }
        System.out.println("Imprimiendo matrixXEscalar");
        printMatrix(producto);
        return producto;
    }
    //imprime la matriz 
    public void printMatrix(double[][] resultado){
    for (int x = 0; x < resultado.length; x++) {
            System.out.print("|");
            for (int y = 0; y < resultado[x].length; y++) {
                System.out.print(resultado[x][y]);
                if (y != resultado[x].length - 1) {
                    System.out.print("\t");
                }
            }
            System.out.println("|");
        }
}
    public double[][] getDatosY()
    {
        return this.datosY;
    }
    
}
