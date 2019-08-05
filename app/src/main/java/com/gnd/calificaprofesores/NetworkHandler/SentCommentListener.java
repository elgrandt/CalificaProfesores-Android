package com.gnd.calificaprofesores.NetworkHandler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public interface SentCommentListener {
    void onSentComment();
    void onFailedComment(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference);
}
