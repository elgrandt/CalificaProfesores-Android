package com.gnd.calificaprofesores;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;

public class ActivityError404 extends AppCompatActivity {

    private MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_404);

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));
    }
    @Override
    protected void onResume() {
        super.onResume();
        menuManager.closeDrawer();
    }
}
