package Dominio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.io.IOException;

//Esta clase es responsable de leer el tablero de un fichero en forma de ceros y unos, ir transitando de estados e ir mostrando dichos estados.
public class Tablero {
    //public static String Matriz = "Matriz.txt";
    //Scanner sc = new Scanner(System.in);
    //public int LIVE = 1;
    //public int DEAD = 0;
    private static int DIMENSION = 30;
    private int[][] estadoActual = new int[DIMENSION][DIMENSION]; //matriz que representa el estado actual.
    private int[][] estadoSiguiente = new int[DIMENSION+2][DIMENSION+2]; // Matriz que representa el estado siguiente.
    private String ficherito = "C:\\Users\\artym\\practicas\\JuegoDeLaVida\\tablero.txt";

    public void leerEstadoActual() throws FileNotFoundException, IOException, StringIndexOutOfBoundsException, NumberFormatException, NullPointerException{
        /********************************************************
         * Lee el estado inicial de un fichero llamado ‘matriz‘.
         ********************************************************/
            /*String ruta = "C:\\Users\\artym\\practicas\\JuegoDeLaVida\\src\\Matriz.txt";
            Scanner sc = new Scanner(ruta);
            char c =  sc.next().charAt(0);
            //char[][] estado = new char[DIMENSION+2][DIMENSION+2];
            while(c !=0){
                //String[] celula = c.split("");
                for (int i = 0; i< DIMENSION; i++) {
                    for (int j = 0; j < DIMENSION; j++) {
                        estadoActual[i][j] = c == '0' ? 0 : 1;
                    }
                }
                c = sc.next().charAt(0);
            }*/
        try
        {
            File fichero = new File(ficherito);
            fichero.createNewFile();
            Scanner sc = new Scanner(fichero);
            int i = 0;
            while(sc.hasNext()) insertarFilaTablero(sc.nextLine(), i++);
            calcularEstadoSiguiente();
        }catch(IOException ex) {
            System.err.println(ex);
        }
    }
    private void insertarFilaTablero(String linea, int i)throws NullPointerException{
        for(int j = 0; j <= linea.length()-1; j++) estadoActual[i][j] = linea.charAt(j) - '0';
    }

    public void generarEstadoActualPorMontecarlo() {
        // La secuencia de ceros y unos generada es guardada en ‘estadoActual‘ y, utilizando las reglas del juego de la vida, se insertan los ceros y unos correspondientes en ‘estadoSiguiente‘.
        /********************************************************
         * Genera un estado inicial aleatorio. Para cada celda genera un número aleatorio en el intervalo [0, 1). Si
         * el número es menor que 0,5, entonces la celda está inicialmente viva. En caso contrario, está muerta.
         *******************************************************/
        /*for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION ; j++) {
                int x = (int) (Math.random() * 2 + 1);
                if (x == 2) {
                    estadoActual[i][j] = LIVE;
                } else {
                    estadoActual[i][j] = DEAD;
                }
            }
        }*/
        Random random = new Random();
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                estadoActual[i][j] = random.nextDouble() < 0.5 ? 1 : 0;
            }
        }

        calcularEstadoSiguiente();
        System.out.println("Generar estado actual");

    }

    public void transitarAlEstadoSiguiente() {
        // La variable ‘estadoActual‘ pasa a tener el contenido de ‘estadoSiguiente‘ y, éste útimo atributo pasar a reflejar el siguiente estado.
        /********************************************************
         * Transita al estado siguiente según las reglas del juego de la vida.
         ********************************************************/
        /*for (int i = 0; i < DIMENSION - 1; i++) {
            for (int j = 0; j < DIMENSION - 1; j++) {
                estadoSiguiente[i][j] = estadoActual[i][j];
                int count = 0;
                try {
                    if (estadoActual[i - 1][j - 1] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);}
                try {
                    if (estadoActual[i - 1][j] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);}
                try {
                    if (estadoActual[i - 1][j + 1] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);}
                try {
                    if (estadoActual[i][j - 1] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);}
                //la casilla x y
                try {
                    if (estadoActual[i][j + 1] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                System.out.println(e);}
                try {
                    if (estadoActual[i + 1][j - 1] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);}
                try {
                    if (estadoActual[i + 1][j] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);}
                try {
                    if (estadoActual[i + 1][j + 1] == LIVE) {
                        count++;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);}
                //Si está muerta y tiene 3 o más vivas
                if (estadoActual[i][j] == DEAD && count >= 3) {
                    estadoActual[i][j] = LIVE;
                    //si está viva y tiene 2 o 3 vivas sigue viva
                } else if (estadoActual[i][j] == LIVE && (count == 2 || count == 3)) {
                    estadoActual[i][j] = LIVE;
                } else {//de lo contrario muere
                    estadoActual[i][j] = DEAD;
                }
            }
        }
        estadoActual = estadoSiguiente;*/
        int[][] aux = estadoActual;
        estadoActual = estadoSiguiente;
        estadoSiguiente = aux;

        calcularEstadoSiguiente();

    }
    /*/*******************************************************
     * @param i cordenada x que sería en un plano tomando como refencia la propia celula
     * @param j cordenada y que sería en un plano tomando como refencia la propia celula

     *******************************************************
    public boolean celulaViva (int i, int j){
        return estadoActual[i][j]== 1;
    }*/
    private int contarVecinasVivas(int fila, int columna) {
        int vecinasVivas = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int filaVecina = (fila + i + DIMENSION) % DIMENSION;
                int columnaVecina = (columna + j + DIMENSION) % DIMENSION;
                vecinasVivas += estadoActual[filaVecina][columnaVecina];
            }
        }
        return vecinasVivas;
    }

    private void calcularEstadoSiguiente() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                int vecinasVivas = contarVecinasVivas(i, j);
                if (estadoActual[i][j] == 1) {
                    estadoSiguiente[i][j] = (vecinasVivas == 2 || vecinasVivas == 3) ? 1 : 0;
                } else {
                    estadoSiguiente[i][j] = (vecinasVivas == 3) ? 1 : 0;
                }
            }
        }
    }


    @Override
    public String toString () {
        /*******************************************************
         * Devuelve, en modo texto, el estado actual.
         * @return el estado actual.
         *******************************************************/
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < DIMENSION; i++){
                for(int j = 0; j < DIMENSION; j++){
                    sb.append(estadoActual[i][j] == 1 ? "x" : "_");
                }
                sb.append('\n');
            }
            return sb.toString();
        }
    }