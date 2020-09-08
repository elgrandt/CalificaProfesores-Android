package com.gnd.calificaprofesores;
/** Archivo viejo - para eliminar **/

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.SentCommentListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.ButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.EditTextData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.SimpleScoreSelectorData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.TitleData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class ActivityOpinarMateria extends AppCompatActivity {


    Button ButtonOpinar;
    EditText TextOpinion;
    RatingBar Rating;
    CourseCommentsDataManager mCourseCommentsDatamanager;
    FirebaseAuth mAuth;
    UserDataManager mUserDataManager;
    Toast toast;
    IntentCourseManager intentCourseManager;

    RecyclerView recyclerView;
    Adapter adapter;
    IntentCourseManager intent;

    SimpleScoreSelectorData scoreSelector;
    EditTextData sendText;
    ButtonData button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinar_materia);

        intent = new IntentCourseManager(getIntent());

        TextView title = findViewById(R.id.TextTitle);
        title.setText(intent.GetCourseName());

        TextView uni = findViewById(R.id.UniversityText);
        uni.setText(intent.getUniName());

        recyclerView = findViewById(R.id.RecyclerView);
        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.AddElement(
                new TitleData("CALIFICACION")
        );
        scoreSelector = new SimpleScoreSelectorData();


        adapter.AddElement(
                scoreSelector
        );
        adapter.AddElement(
                new TitleData("EN PALABRAS")
        );
        sendText = new EditTextData("Ej.: Tiene 2 parciales, el primero es más difícil!");
        adapter.AddElement(
                sendText
        );
        button = new ButtonData("ENVIAR");
        adapter.AddElement(
                button
        );

        intentCourseManager = new IntentCourseManager(getIntent());

        mCourseCommentsDatamanager = new CourseCommentsDataManager(intentCourseManager.GetCourseId(),intentCourseManager.GetCourseName());
        mAuth = FirebaseAuth.getInstance();
        mUserDataManager = new UserDataManager("");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOpinion();
            }
        });

        if (intent.GetIntent().getBooleanExtra("PrevComment",false)){
            scoreSelector.setStars(intent.GetIntent().getFloatExtra("Score",0f));
            sendText.setText(intent.GetIntent().getStringExtra("TextContent"));
            sendText.setAnonimo(intent.GetIntent().getBooleanExtra("IsAnonimo",true));
            sendText.setHasText(intent.GetIntent().getBooleanExtra("HasText",false));
        }else{
            sendText.setAnonimo(false);
            sendText.setHasText(true);
        }
    }
    private void SendOpinion(){
        mUserDataManager.listenForUserProfileData(null);

        mUserDataManager.setmGotUserExtraDataListener(new GotUserExtraDataListener() {
            @Override
            public void gotExtraData(UserExtraData extraData) {
                CourseComment comment = new CourseComment(
                        extraData.GetShownName(),
                        sendText.getEditable().toString(),
                        (long)(scoreSelector.getStars()*2f),
                        0L
                );
                comment.setAnonimo(sendText.isAnonimo());
                comment.setConTexto(sendText.isHasText());


                mCourseCommentsDatamanager.SendComment(comment, new SentCommentListener() {
                            @Override
                            public void onSentComment() {
                                toast = Toast.makeText(getApplicationContext(), "Opinión enviada", Toast.LENGTH_SHORT);
                                toast.show();
                                startActivity(
                                        intentCourseManager.ConvertIntent(
                                        ActivityOpinarMateria.this,
                                        ActivityClassFrontPageV2.class )
                                        .GetIntent()
                                );
                            }

                            @Override
                            public void onFailedComment(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                toast = Toast.makeText(getApplicationContext(), "Error enviando opinión ", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                });
            }
        });

    }

}
