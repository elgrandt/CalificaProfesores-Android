package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.gnd.calificaprofesores.ActivityOpinarMateria;
import com.gnd.calificaprofesores.IntentsManager.IntentCourseManager;
import com.gnd.calificaprofesores.NetworkHandler.CourseCommentsDataManager;
import com.gnd.calificaprofesores.NetworkHandler.GotCommentListener;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.OpinionCourseData;
import com.google.firebase.database.DatabaseError;

import java.util.List;
import java.util.Vector;


/** Aqui acumulamos todas las opiniones recientes de una clase **/

/** layout_opinion_recent.xml **/

public class ActivityOpinionRecent extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;
    private View mView;
    private Adapter adapter;
    private ViewGroup placeholder, mContainer;
    private LayoutInflater layoutInflater;

    private List<CourseComment> Comments;
    private Vector<CourseComment> commentsModified = new Vector<>();
    private static String CourseName;
    private static Long CourseId;

    private RecyclerView recyclerView;
    CourseCommentsDataManager mCourseCommentsDataManager;
    IntentCourseManager CourseManager; // para saber en que curso estamos

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_loading_icon, container, false);

        mContainer = container;
        layoutInflater = inflater;
        placeholder = (ViewGroup)mView;

        /// el adapter course comemnts va a necesitar los comentarios para mostrar sobre la materia


        /// el recycler view depende del adapter

        CourseManager = new IntentCourseManager(getActivity().getIntent());

        mCourseCommentsDataManager = new CourseCommentsDataManager(
                CourseManager.GetCourseId(),
                CourseManager.GetCourseName()
        );

        mCourseCommentsDataManager.setGotCommentListener(new GotCommentListener() {
            @Override
            public void onGotComment(List<CourseComment> comments) {
                recvComments(comments);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"Error buscando comentarios",Toast.LENGTH_SHORT).show();
            }
        });

        mCourseCommentsDataManager.listenForComments();

        return placeholder;
    }
    public void recvComments(List<CourseComment> comments){
        mView = layoutInflater.inflate(R.layout.layout_recycler_view, mContainer, false);
        placeholder.removeAllViews();
        placeholder.addView(mView);

        recyclerView = mView.findViewById(R.id.RecyclerView);
        adapter = new Adapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (comments.size() == 0){
            adapter.AddElement(new NoInfoData(
                    "No hay opiniones",
                    "¡SÉ EL PRIMERO!",
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            startActivity(
                                    CourseManager.ConvertIntent(
                                            getContext(),
                                            ActivityOpinarMateria.class
                                    ).GetIntent()
                            );
                        }
                    }
            ));
        }
        commentsModified.clear();
        for (CourseComment comment : comments){
            if (comment.getAuthor().equals("Ceitba Ceitba")){
                commentsModified.add(comment);
            }
        }
        for (CourseComment comment : comments){
            if (!comment.getAuthor().equals("Ceitba Ceitba")){
                commentsModified.add(comment);
            }
        }
        for (CourseComment comment : commentsModified){

            adapter.AddElement(new OpinionCourseData(
                    comment.getAuthor(),
                    comment.getContent(),
                    comment.getValoracion(),
                    comment.getTimestampLong()
            ));

        }

        adapter.notifyDataSetChanged();
    }
}
