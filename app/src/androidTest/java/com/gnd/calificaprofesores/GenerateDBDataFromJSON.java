package com.gnd.calificaprofesores;

/*** https://www.tutorialspoint.com/android/android_json_parser
 *  https://github.com/ralfstx/minimal-json
 */

/*** Mucho cuidado con este archivo porque edita la base de datos ***/


import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.gnd.calificaprofesores.NetworkAdd.AddClassHandler;
import com.gnd.calificaprofesores.NetworkAdd.AddProfessorHandler;
import com.gnd.calificaprofesores.NetworkAdd.CompleteClassData;
import com.gnd.calificaprofesores.NetworkAdd.CompleteProfData;
import com.gnd.calificaprofesores.NetworkAdd.ProfessorAddedListener;
import com.gnd.calificaprofesores.NetworkAdd.SmallMateriaData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchWordMini;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.MiniSearchData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class GenerateDBDataFromJSON{
    private Context appContext;
    private AddProfessorHandler addProfessorHandler;
    private AddClassHandler addClassHandler;

    private FirebaseAuth mAuth;
    private Map<String, String> professors;

    @Test
    public void RunGenerateDBDataFromJSON() throws org.json.JSONException{
        Log.d("CalificaProfesoresLogs", "Iniciando cargado de DB");
        appContext = getApplicationContext();
        addProfessorHandler = new AddProfessorHandler();
        addClassHandler = new AddClassHandler();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("ad@ad.com","laljtyjaytjttjla").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    try {
                        Log.d("CalificaProfesoresLogs", "Login!");
                        //deleteMatFrom("1");
                        addProfessorsDb();
                        //addMateriasDb();
                    }catch (Exception e){
                        Log.d("CalificaProfesoresLogs","org.json.JSONException");
                    }
                }else{
                    Log.d("CalificaProfesoresLogs","Login failed!");
                }
            }
        });
        while (true){

        }

        //Log.d("CalificaProfesoresLogs",jsonString);

    }

    public void addProfessorsDb() throws org.json.JSONException{ /// agregamos la base de datos de profesores del archivo json
        Log.d("CalificaProfesoresLogs", "Cargando profesores");

        InputStream inputStream = appContext.getResources().openRawResource(R.raw.profesores);
        String jsonString = new Scanner(inputStream,"ISO-8859-1").useDelimiter("\\A").next();

        JSONObject reader = new JSONObject(jsonString);
        JSONArray names = reader.names();


        addProfessorHandler.setProfessorAddedListener(new ProfessorAddedListener() {
            @Override
            public void onProfessorAdded() {
                Log.d("CalificaProfesoresLogs", "¡Profesor agregado!");
            }
        });

        Log.d("CalificaProfesoresLogs","Hay " + names.length()+" profesores");

        for (int i = 0;i < names.length();i++){
            String name = names.getString(i);
            //JSONArray facultades = reader.getJSONObject(name).getJSONArray("facultades");

            /*for (int j = 0;j < facultades.length();j++){
                String facultadName = facultades.getString(i);
                Log.d("CalificaProfesoresLogs", facultadName);
            }*/

            Log.d("CalificaProfesoresLogs","Procesando " + name);

            loadProfessor(name);
        }


        Log.d("CalificaProfesoresLogs", "Finalizando carga de profesores");
    }

    public void deleteMatFrom(final String facultadId){
        // delete all professors from faculty

        FirebaseDatabase.
                getInstance().
                getReference("MateriasPorFacultad/"+facultadId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                   // deleteMat((String)child.child("Name").getValue());
                    FirebaseDatabase.getInstance()
                            .getReference("MateriasPorFacultad/"+facultadId+"/"+child.getKey()).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void deleteMat(final String name){
        Log.d("CalificaProfesoresLogs","Eliminando materia " + name);

        SearchWordMini miniName = new SearchWordMini(name);
        FirebaseDatabase.
                getInstance()
                .getReference("Materias")
                .orderByChild("Name")
                .startAt(miniName.getWord())
                .endAt(miniName.getWord()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String id = child.getKey();
                    Log.d("CalificaProfesoresLogs","Codigo de materia = "+ id);



                    //FirebaseDatabase.getInstance()
                    //        .getReference("Materias/"+id).removeValue();



                    /*FirebaseDatabase.getInstance()
                            .getReference("Materias")
                            .getRefe.removeValue();*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void loadProfessor(String name){
        Map<String, String> facultades = new TreeMap<>();
        Map<String, SmallMateriaData> materias = new TreeMap<>();

        addProfessorHandler.addProfessor(new CompleteProfData(
                name,
                "0",
                true,
                facultades,
                materias,
                false
        ));

    }

    public void deleteMatFromFacultad(String name, String facultad){
        // eliminamos las materias de una facultad especifica

    }

    public void addMateriasDb() throws org.json.JSONException{

        InputStream inputStream = appContext.getResources().openRawResource(R.raw.materias);
        String jsonString = new Scanner(inputStream,"ISO-8859-1").useDelimiter("\\A").next();

        JSONObject reader = new JSONObject(jsonString);
        JSONArray names = reader.names();

        for (int i = 0;i < names.length();i++) {

            String name = names.getString(i);

            JSONObject object = reader.getJSONObject(name);

            final String facultadId = object.getString("Facultad");
            final String facultadName = object.getString("ShownName");
            final String classId = "0";

            professors = new HashMap<>();

            final JSONArray profNames = object.getJSONArray("prof");
            Log.d("CalificaProfesoresLogs","Materia: " + name);
            Log.d("CalificaProfesoresLogs","Profesres:");

            for (int j = 0;j < profNames.length();j++){
                final String longProfName = profNames.getString(j);
                SearchWordMini searchProfName = new SearchWordMini(longProfName);
                final String profName = searchProfName.getWord();

                Log.d("CalificaProfesoresLogs",profName);

                FirebaseDatabase.getInstance()
                        .getReference("Prof")
                        .orderByChild("SearchName")
                        .startAt(profName)
                        .endAt(profName)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    String id = child.getKey();
                                    addMateriasDbWithProf(
                                            id,
                                            longProfName,
                                            facultadId,
                                            facultadName,
                                            classId,
                                            profNames.length()
                                    );

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

            }
            Log.d("CalificaProfesoresLogs","----------------------------------");
            /*addClassHandler.addClass(new CompleteClassData(
                    name,
                    facultadId,
                    facultadName,
                    classId,
                    professors
            ));*/
        }
    }
    public void addMateriasDbWithProf(String id, String name, String facultadId, String facultadName, String classId, int length){
        professors.put(id, name);

        if (professors.size() == length) {
            Log.d("CalificaProfesoresLogs", "Professors:");
            for (String profName : professors.keySet()) {
                Log.d("CalificaProfesoresLogs", profName + " " + professors.get(profName));
            }
            Log.d("CalificaProfesoresLogs", "Agregando ...");
            addClassHandler.addClass(new CompleteClassData(
                    name,
                    facultadId,
                    facultadName,
                    classId,
                    professors
            ));
        }

    }

}
