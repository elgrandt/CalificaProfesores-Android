package com.gnd.calificaprofesores;

/*** https://www.tutorialspoint.com/android/android_json_parser
 *  https://github.com/ralfstx/minimal-json
 */

/*** Mucho cuidado con este archivo porque edita la base de datos ***/


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.gnd.calificaprofesores.NetworkAdd.AddClassHandler;
import com.gnd.calificaprofesores.NetworkAdd.AddProfessorHandler;
import com.gnd.calificaprofesores.NetworkAdd.CompleteClassData;
import com.gnd.calificaprofesores.NetworkAdd.CompleteProfData;
import com.gnd.calificaprofesores.NetworkAdd.ProfessorAddedListener;
import com.gnd.calificaprofesores.NetworkAdd.SmallMateriaData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchWordMini;
import com.gnd.calificaprofesores.Utils.JSONUtils.UniJSONFile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private int count;

    private FirebaseAuth mAuth;
    private UniJSONFile uniJSONFile;
    private JSONArray names;
    private JSONObject reader;
    private Map<String, String> getProfessorId;

    @Test
    public void RunGenerateDBDataFromJSON() throws org.json.JSONException{
        Log.d("CalificaProfesoresLogs", "Iniciando cargado de DB");
        appContext = getApplicationContext();
        addProfessorHandler = new AddProfessorHandler();
        addClassHandler = new AddClassHandler();
        uniJSONFile = new UniJSONFile(R.raw.get_uni_id, appContext);
        count = 0;
        getProfessorId = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("ad@ad.com","la456454356446768a").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    try {
                        Log.d("CalificaProfesoresLogs", "Login!");
                        //deleteMatFrom("1");
                        //addProfessorsDb();
                        LoadProfessorsId();
                        addMateriasDb();
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

    public void LoadProfessorsId() throws org.json.JSONException {
        // cargamos el id de los profesores de un archivo json con la información
        InputStream inputStream = appContext.getResources().openRawResource(R.raw.profesores_with_id);
        String jsonString = new Scanner(inputStream, "ISO-8859-1").useDelimiter("\\A").next();

        reader = new JSONObject(jsonString);

        JSONArray names = reader.names();
        for (int i = 0;i < names.length();i++) {
            String name = names.getString(i);
            JSONObject object = reader.getJSONObject(name);
            getProfessorId.put(name, object.getString("id"));

        }
    }

    // conseguimos el id de un profesor
    public String getProfId(String profName){
         return getProfessorId.get(profName);
    }

    public void addMateriasDb() throws org.json.JSONException {

        InputStream inputStream = appContext.getResources().openRawResource(R.raw.materias);
        String jsonString = new Scanner(inputStream, "ISO-8859-1").useDelimiter("\\A").next();

        reader = new JSONObject(jsonString);
        names = reader.names();

        /*count = 0;
        // primero calculamos nombres
        for (final String longProfName : professors.keySet()) {
            SearchWordMini searchProfName = new SearchWordMini(longProfName);
            final String profName = searchProfName.getWord();

            Log.d("CalificaProfesoresLogs", profName);

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
                                professors.replace(longProfName, id);

                                count++;
                                if (count == professors.size()) {
                                    // cuanto colocamos todos los profesores
                                    try {
                                        loadMateriasWithProf();
                                    }catch (org.json.JSONException e){
                                        Log.d("CalificaProfesoresLogs", "org.json.JSONException");
                                    }
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        }*/
        loadMateriasWithProf();
    }

    private void loadMateriasWithProf() throws org.json.JSONException {

        for (int i = 0;i < names.length();i++) {
            final  String name = names.getString(i);
            JSONObject object = reader.getJSONObject(name);

            final String facultadFullName = object.getString("Facultad"); //object.getString("Facultad");
            final String facultadName = object.getString("FacultadName");

            final String facultadId = uniJSONFile.getUniIdFromName(facultadFullName);
            final String classId = "0";

            Map <String, String> profList = new HashMap<>();

            JSONArray profArray = object.getJSONArray("prof");

            for (int j = 0;j < profArray.length();j++){
                String profName = profArray.getString(j);
                profList.put(getProfId(profName), profName);
            }

            addMateriasDbWithProf(
                    name,
                    facultadId,
                    facultadName,
                    classId,
                    profList
            );

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
    public void addMateriasDbWithProf(String name, String facultadId, String facultadName, String classId, Map<String, String> profList){
        //professors.put(id, profName);

        //if (professors.size() == length) {
        Log.d("CalificaProfesoresLogs", "Professors:");

        for (String profName : profList.keySet()) {
            Log.d("CalificaProfesoresLogs", profName + " " + profList.get(profName));
        }

        Log.d("CalificaProfesoresLogs", "Agregando ...");
        addClassHandler.addClass(new CompleteClassData(
                name,
                facultadId,
                facultadName,
                classId,
                profList
        ));
        //}

    }
}
