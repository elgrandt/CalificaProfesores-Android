package com.gnd.calificaprofesores.FireSearchOptimized;

import com.google.firebase.database.DataSnapshot;

import java.util.Set;
import java.util.Vector;

public interface GotSearchListener {
    void onGotSearch(Set<SearchWordContent> data);
}
