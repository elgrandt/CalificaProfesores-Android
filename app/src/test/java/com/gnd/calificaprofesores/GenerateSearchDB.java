package com.gnd.calificaprofesores;

import android.content.Context;

import com.gnd.calificaprofesores.FireSearchOptimized.SearchWord;

import org.junit.Test;

import java.util.Arrays;
import java.util.Vector;


public class GenerateSearchDB {
    private static final String FAKE_STRING = "HELLO_WORLD";

    @Test
    public void RunGenerateSearchDB() {
        // Given a Context object retrieved from Robolectric..
        Vector<String> palabras = new Vector<>();
        palabras.add("Instituto");
        palabras.add("Matemática");
        palabras.add("Historia");
        palabras.add("Histológia");
        palabras.add("Histeria");
        palabras.add("Institución");

        for (String palabra : palabras){
            addWordToDb(palabra);
        }

    }
    private void addWordToDb(String word){
        System.out.println("Procesando '" + word + "' ...");
        if (word.length() < 2){
            /* Nada por hacer */
        }else{
            SearchWord myWord = new SearchWord(word);

            
        }

    }
}