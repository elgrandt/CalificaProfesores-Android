package com.gnd.calificaprofesores.Utils.JSONUtils;

import android.content.Context;
import android.util.Log;

import com.gnd.calificaprofesores.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class UniJSONFile {
    private Map<String, String> getUniId;

    public UniJSONFile(int file, Context appContext) throws org.json.JSONException{
        getUniId = new HashMap<>();

        InputStream inputStream = appContext.getResources().openRawResource(file);
        String jsonString = new Scanner(inputStream,"ISO-8859-1").useDelimiter("\\A").next();

        JSONObject reader = new JSONObject(jsonString);
        JSONArray names = reader.names();

        for (int i = 0;i < names.length();i++) {
            String name = names.getString(i);
            JSONObject object = reader.getJSONObject(name);

            String id = object.getString("id");

            Log.d("CalificaProfesoresLogs","Cargando " + name + "(id=" + id+ ")");
            getUniId.put(name, id);
        }

    }
    public String getUniIdFromName(String completeName){
       return getUniId.get(completeName);
    }
}
