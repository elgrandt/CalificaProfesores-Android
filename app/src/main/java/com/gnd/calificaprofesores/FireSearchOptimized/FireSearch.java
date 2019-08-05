package com.gnd.calificaprofesores.FireSearchOptimized;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/*** Buscador de la app, génerico ***/
/*** Busca por las primeras tres letras en orden, el resto en desorden ***/


public class FireSearch {
    private String dbTitle;
    GotSearchListener listener;

    public FireSearch(String dbTitle){
        this.dbTitle = dbTitle;
    }

    public String getDbTitle() {
        return dbTitle;
    }

    public void setDbTitle(String dbTitle) {
        this.dbTitle = dbTitle;
    }

    public GotSearchListener getListener() {
        return listener;
    }

    public void setListener(GotSearchListener listener) {
        this.listener = listener;
    }

    public void runSearch(final String word){
        Log.d("CalificaProfesoresLogs", "Search: " + word);
        Log.d("CalificaProfesoresLogs","db: " + dbTitle);
        FirebaseDatabase.getInstance()
                .getReference(dbTitle)
                .orderByKey()
                .startAt(word)
                .endAt(word + "\uf8ff")
                .limitToFirst(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Set<SearchWordContent> data = new TreeSet<>();

                        for (final DataSnapshot words : dataSnapshot.getChildren()) {
                            for (final DataSnapshot child : words.getChildren()) {
                                String id = (String) child.child("id").getValue();
                                String title = (String) child.child("title").getValue();
                                String subtitle = (String) child.child("subtitle").getValue();

                                data.add(
                                        new SearchWordContent(
                                                title,
                                                subtitle,
                                                id
                                        )
                                );
                            }
                        }

                        getListener().onGotSearch(
                                data
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
