package com.gnd.calificaprofesores.FireSearchOptimized;

import java.util.Arrays;

public class SearchWord {
    private String baseCaracteres;
    private String resto;
    private String restoSorted;

    public SearchWord(String word){
        if (word.length() > 2) {
            baseCaracteres = word.substring(0, 2);
            resto = word.substring(2);

            char []charArray = resto.toCharArray();
            Arrays.sort(charArray);

            restoSorted = String.valueOf(charArray);

        }else{
            /** Probema, deberia tener al menos longitud 2**/
        }
    }

    public String getBaseCaracteres() {
        return baseCaracteres;
    }

    public void setBaseCaracteres(String baseCaracteres) {
        this.baseCaracteres = baseCaracteres;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
    }

    public String getRestoSorted() {
        return restoSorted;
    }

    public void setRestoSorted(String restoSorted) {
        this.restoSorted = restoSorted;
    }
}
