package com.gnd.calificaprofesores.FireSearchOptimized;

import android.util.Log;

import java.util.Arrays;
import java.util.Vector;

public class SearchWord {
    private Vector<String> searchStrings; /** Strings con los que es posible buscar la palabra **/

    private String title, subtitle;
    private String id;
    private String completeString;

    public SearchWord(String word){
        completeString = word;
        String [] split = word.split("\\s+");
        searchStrings = new Vector<>();


        for (int i = 0;i < split.length;i++){
            //StringBuilder builder = new StringBuilder();

                //for (int k = j;k < split.length;k++){

            String processedWord = split[i].toLowerCase();
            processedWord = processedWord.replace("á","a");
            processedWord = processedWord.replace("é","e");
            processedWord = processedWord.replace("í","i");
            processedWord = processedWord.replace("ó","o");
            processedWord = processedWord.replace("ú","u");

            //builder = builder.append(processedWord + " ");
            //}
            searchStrings.add(processedWord); //builder.toString());

        }
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SearchWordContent getContent(){
        return new SearchWordContent(this.title, this.subtitle, this.id);
    }

    public Vector<String> getSearchStrings() {
        return searchStrings;
    }

    public void setSearchStrings(Vector<String> searchStrings) {
        this.searchStrings = searchStrings;
    }

    public String getCompleteString() {
        return completeString;
    }

    public void setCompleteString(String completeString) {
        this.completeString = completeString;
    }

}

