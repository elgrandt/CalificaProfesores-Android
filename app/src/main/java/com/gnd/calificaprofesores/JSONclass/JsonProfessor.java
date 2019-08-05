package com.gnd.calificaprofesores.JSONclass;

import java.util.ArrayList;
import java.util.Vector;

/*** Aca van clases necesarias para deparsear el archivo json con profesores ***/

public class JsonProfessor {
    private ArrayList<String> facultades;
    private String profName;

    public JsonProfessor(ArrayList<String> facultades, String profName) {
        this.facultades = facultades;
        this.profName = profName;
    }

    public ArrayList<String> getFacultades() {
        return facultades;
    }

    public void setFacultades(ArrayList<String> facultades) {
        this.facultades = facultades;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }
}
