package com.gnd.calificaprofesores;

import androidx.annotation.NonNull;
import android.util.Log;

import com.gnd.calificaprofesores.FireSearchOptimized.SearchWord;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchWordMini;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import java.util.Vector;


public class GenerateSearchDB {
    private static final String FAKE_STRING = "HELLO_WORLD";
    private FirebaseAuth mAuth;
    private int counter = 5;

    @Test
    public void RunGenerateSearchDB() throws Exception{
        // Given a Context object retrieved from Robolectric..
        return;

//        mAuth = FirebaseAuth.getInstance();
//        mAuth.signInWithEmailAndPassword("ad@ad.com","lalala").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Log.d("CalificaProfesoresLogs", "Login!");
//                    AddTestWords();
//                }else{
//                    Log.d("CalificaProfesoresLogs","Login failed!");
//                }
//            }
//        });
//        Log.d("CalificaProfesoresLogs","Test finished");
//        while (true){
//
//        }
    }
    private void AddTestWords(){
        Vector<String> palabras = new Vector<>();
        palabras.add("Martin Orlandez");
        palabras.add("Juan Martinez");
        palabras.add("Rodriguez Horacio");
        palabras.add("Horacio Castaña");
        palabras.add("Julieta Mandon");
        palabras.add("Fernando Oyún Gafa");

        for (String palabra : palabras){
            addWordToDb(palabra);
        }

    }
    private void addWordToDb(String word){
        Log.d("CalificaProfesoresLogs","Procesando '" + word + "' ...");
        if (word.length() < 2){
            /* Nada por hacer */
            Log.d("CalificaProfesoresLogs","Demasiado corto");
        }else {

            SearchWord myWord = new SearchWord(word);
            myWord.setTitle(word);
            myWord.setSubtitle("subtitulo");

            for (String searchWord : myWord.getSearchStrings()) {
                myWord.setId(Integer.toString(counter));

                FirebaseDatabase.
                        getInstance().
                        getReference("SearchWords/Prof")
                        .child(searchWord)
                        .push()
                        .setValue(myWord.getContent());

                Log.d("CalificaProfesoresLogs", "SearchWords/Prof" + myWord.getCompleteString());
                Log.d("CalificaProfesoresLogs", "Sucessful!");


            }
            counter += 1;
        }
    }
}