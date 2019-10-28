package com.example.ahorcado.objects;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Partida {

    private int vidas;
    private int puntos;
    private boolean comodin;
    private ArrayList<String> listaPosiciones;
    private ArrayList<Character> listaAlfabetica;
    private String[] listaPalabras = {"FUTBOL", "BICICLETA", "ORDENADOR", "COCHE"};  ;
    private String palabra;
    private String muestraPalabra;
    private char[] letrasAdivinadas;

    public Partida(int vidas, boolean comodin){
        this.vidas = vidas;
        this.comodin = comodin;
        puntos = 0;
    }

    public void iniciarPartida(){
        palabra = seleccionPalabra();
        letrasAdivinadas = new char[palabra.length()];
        for (int x = 0; x < letrasAdivinadas.length; x++){
            letrasAdivinadas[x] = '_';
        }
    }

    public String seleccionPalabra(){
        Random r = new Random();
        int i = r.nextInt(listaPalabras.length);
        return listaPalabras[i];
    }

    public ArrayList<String> getListaPosiciones(){
        listaPosiciones = new ArrayList<>();

        if(comodin)
            listaPosiciones.add("*");

        for(int x = 1; x <= getLonguitudPalabra(); x++){
            listaPosiciones.add(String.valueOf(x));
        }
        return listaPosiciones;
    }

    public ArrayList<Character> getListaAlfabetica(){
        listaAlfabetica = new ArrayList<>();
        for (char x = 65; x <= 90; x++)
            listaAlfabetica.add(x);
        listaAlfabetica.add((char)209);
        return listaAlfabetica;
    }

    public void compararLetra(String posicion, String letra){
        if (posicion.equals("*")){
            boolean adivinado = false;
            for(int x = 0; x < palabra.length(); x++){
                if(letra.charAt(0) == palabra.charAt(x)){
                    Log.i(null, "entré");
                    letrasAdivinadas[x] = palabra.charAt(x);
                    adivinado = true;
                }
            }

            if(adivinado)
                puntos++;
            else
                vidas--;

        } else {
            // Adaptar posicion del usuario a las posiciones del Array de Char
            int pos = Integer.parseInt(posicion) - 1;
            if(letra.charAt(0) == palabra.charAt(pos)){
                letrasAdivinadas[pos] = palabra.charAt(pos);
                puntos+=5;
            } else{
                vidas--;
            }
        }
    }

    public int getLonguitudPalabra(){
        return palabra.length();
    }

    public String getImprimirPalabra(){
        String printPalabra = "";
        for(int x = 0; x < letrasAdivinadas.length; x++){
            printPalabra += " " + letrasAdivinadas[x] + " ";
        }
        return printPalabra;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean getComodin() {
        return comodin;
    }

    public String getPalabra() {
        return palabra;
    }
}
