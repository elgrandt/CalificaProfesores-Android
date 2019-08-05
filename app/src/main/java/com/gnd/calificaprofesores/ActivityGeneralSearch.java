package com.gnd.calificaprofesores;

import android.os.Bundle;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.View;

import com.gnd.calificaprofesores.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityGeneralSearch extends AppCompatActivity {
    private MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_search);

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

    }

}
