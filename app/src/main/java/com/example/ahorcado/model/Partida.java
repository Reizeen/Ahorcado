package com.example.ahorcado.model;

import android.content.Context;
import android.util.Log;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Partida {

    private int vidas;
    private int puntos;
    private boolean comodin;
    private ArrayList<String> listaPosiciones;
    private String[] listaPalabras = {"FUTBOL", "BICICLETA", "ORDENADOR", "COCHE", "GOLF", "FARMACIA", "MAMUT", "AHORCADO", "MAR", "PAZ"};  ;
    private String palabra;
    private char[] letrasAdivinadas;

    /** Constructor */
    public Partida(int vidas, boolean comodin){
        this.vidas = vidas;
        this.comodin = comodin;
        puntos = 0;
    }

    /** Inicia la partida eligiendo una palabra
      * y estableciendo un array char sin tener ninguna letra adivinada */
    public void iniciarPartida(){
        palabra = seleccionPalabra();
        letrasAdivinadas = new char[palabra.length()];
        for (int x = 0; x < letrasAdivinadas.length; x++){
            letrasAdivinadas[x] = '_';
        }
    }

    /** Elije una palabra al azar del Array listaPalabras */
    public String seleccionPalabra(){
        Random r = new Random();
        int i = r.nextInt(listaPalabras.length);
        return listaPalabras[i];
    }

    /** Establecer un array con el numero de posiciones que tenga la palabra elegida */
    public ArrayList<String> generarListaPosiciones(){
        listaPosiciones = new ArrayList<>();

        if(comodin)
            listaPosiciones.add("*");

        for(int x = 1; x <= palabra.length(); x++){
            listaPosiciones.add(String.valueOf(x));
        }
        return listaPosiciones;
    }

    /** Establece un array con las letras el Abecedario */
    public ArrayList<String> generarListaAlfabetica(){
        ArrayList<String> listaAlfabetica = new ArrayList<>();
        for (char x = 65; x <= 90; x++)
            listaAlfabetica.add(String.valueOf(x));
        char ny = 209;
        listaAlfabetica.add(String.valueOf(ny));
        return listaAlfabetica;
    }

    /** Comparar la letra segun la posicion establecida
      * Si acierta suma los puntos correspondientes.
      * Si falla resta un punto
      * Al acertar una letra, la letra se pondra en el array de letras Adivinadas */
    public void compararLetra(String posicion, String letra){
        if (posicion.equals("*")){
            boolean adivinado = false;
            for(int x = 0; x < palabra.length(); x++){
                if(letra.charAt(0) == palabra.charAt(x)){
                    letrasAdivinadas[x] = palabra.charAt(x);
                    adivinado = true;
                }
            }

            if(adivinado)
                puntos++;
            else
                vidas--;

        } else {
            int pos = Integer.parseInt(posicion) - 1;
            if(letra.charAt(0) == palabra.charAt(pos)){
                letrasAdivinadas[pos] = palabra.charAt(pos);
                puntos+=5;
            } else{
                vidas--;
            }
        }
    }

    /** Borra la letra si no se encuentra en la palabra
      * y borra letra si ya a sido usada al encontrase en la palabra */
    public boolean borrarLetra(String letra){
        for (int x = 0; x < palabra.length(); x++){
            if (letra.charAt(0) == palabra.charAt(x) && letrasAdivinadas[x] == '_')
                return false;
        }
        return true;
    }

    /** Borra posicion del array si la posicion ya a sido usada */
    public boolean borrarPosicion(String letra, String posicion) {
        boolean letraBorrrada = false;
        if (posicion.equals("*")){
            for (int x = 0; x < palabra.length(); x++) {
                if (letra.charAt(0) == palabra.charAt(x)) {
                    listaPosiciones.remove(String.valueOf(x+1));
                    letraBorrrada = true; } }
        } else {
            int pos = Integer.parseInt(posicion);
            if (letra.charAt(0) == palabra.charAt(pos-1)) {
                listaPosiciones.remove(String.valueOf(pos));
                letraBorrrada = true; } }

        return letraBorrrada;
    }

    /** Comprobar si se han acertado todas las letras */
    public boolean palabraCompletada(){
        String palabraAdivinada = "";
        for (int x = 0; x < letrasAdivinadas.length; x++)
            palabraAdivinada += letrasAdivinadas[x];

        if (palabraAdivinada.equals(palabra))
            return true;
        return false;
    }

    /** Guardar la puntuacion en un fichero */
    public void guardarPuntuacion(Context context, String userName){
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = sdf.format(date);
            String puntuacionUsuario = userName + " - " + puntos + " Puntos - " + fecha + "\n";

            OutputStreamWriter file = new OutputStreamWriter(context.openFileOutput("puntuacion.txt", Context.MODE_APPEND ));
            file.write(puntuacionUsuario);
            file.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }

    /** Imprimir la palabra en la interfaz */
    public String getImprimirPalabra(){
        String printPalabra = "";
        for(int x = 0; x < letrasAdivinadas.length; x++){
            printPalabra += " " + letrasAdivinadas[x] + " ";
        }
        return printPalabra;
    }


    /*************************************************
    ************** Getters and Setters ***************
    *************************************************/

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public ArrayList<String> getListaPosiciones() {
        return listaPosiciones;
    }

    public void setListaPosiciones(ArrayList<String> listaPosiciones) {
        this.listaPosiciones = listaPosiciones;
    }

    public char[] getLetrasAdivinadas() {
        return letrasAdivinadas;
    }

    public void setLetrasAdivinadas(char[] letrasAdivinadas) {
        this.letrasAdivinadas = letrasAdivinadas;
    }
}
