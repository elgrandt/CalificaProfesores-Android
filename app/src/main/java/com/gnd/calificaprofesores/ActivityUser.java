package com.gnd.calificaprofesores;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkNewsHandler.GotNewsListener;
import com.gnd.calificaprofesores.NetworkNewsHandler.NetworkNewsHandler;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemData;
import com.google.firebase.auth.FirebaseAuth;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

/** activity_user.xml **/
/** Esta es la activity por donde empieza la App **/

public class ActivityUser extends AppCompatActivity {
    private MenuManager menuManager;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private ProgressWheel progressWheel;
    private UserDataManager userDataManager;

    private NetworkNewsHandler networkNewsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = findViewById(R.id.RecyclerView);
        progressWheel = findViewById(R.id.LoadingIcon);
        progressWheel.bringToFront();


        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        networkNewsHandler = new NetworkNewsHandler();
        userDataManager = new UserDataManager("");

        networkNewsHandler.setGotNewsListener(new GotNewsListener() {
            @Override
            public void onGotNews(List<NewsItemData> news) {
                SetLoaded();

                for (NewsItemData newsItem : news){
                    adapter.AddElement(newsItem);
                }
                adapter.notifyDataSetChanged();
                SetLoaded();
            }
        });
        networkNewsHandler.ListenForNews();

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        if (FirebaseAuth.getInstance().getUid() != null){
            userDataManager.setShownName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        }

        SetLoading();

        /** Provisional **/
        /*Intent intent = new Intent(ActivityUser.this, ActivityCompleteSearch.class);
        startActivity(intent);*/
        /** Fin provisional **/
    }
    @Override
    protected void onResume() {
        super.onResume();
        menuManager.closeDrawer();
    }
    private void SetLoading(){
        progressWheel.setVisibility(View.VISIBLE);
    }
    private void SetLoaded(){
        progressWheel.setVisibility(View.INVISIBLE);
    }
}
