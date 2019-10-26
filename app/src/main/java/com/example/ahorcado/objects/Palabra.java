package com.example.ahorcado.objects;

import java.util.ArrayList;
import java.util.Random;

public class Palabra {

    private ArrayList<String> lista;
    private String palabraElegida;

    public Palabra(ArrayList<String> lista){
        this.lista = lista;
        palabraElegida = null;
    }

    public void seleccionPalabra(){
        Random r = new Random();
        int i = r.nextInt(lista.size());
        palabraElegida = lista.get(i);
    }

    public char[] palabraDividia(){
        return palabraElegida.toCharArray();
    }

    public String getPalabraElegida(){
        return palabraElegida;
    }
}
