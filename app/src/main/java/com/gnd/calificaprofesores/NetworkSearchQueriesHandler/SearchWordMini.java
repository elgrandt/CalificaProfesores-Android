package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

public class SearchWordMini {
    private String myWord;
    public SearchWordMini(String word){
        myWord = word.toLowerCase();

        myWord = myWord.replace('á','a');
        myWord = myWord.replace('é','e');
        myWord = myWord.replace('í','i');
        myWord = myWord.replace('ó','o');
        myWord = myWord.replace('ú','u');

    }

    public String getWord(){
        return myWord;
    }

}
