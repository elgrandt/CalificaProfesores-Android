package com.gnd.calificaprofesores.FireSearchOptimized;

import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;

public class SearchWordContent implements Comparable<SearchWordContent> {
    private String title, subtitle;
    private String id;
    private String type;

    public SearchWordContent(String title, String subtitle, String id) {
        this.title = title;
        this.subtitle = subtitle;
        this.id = id;

        type = "Prof";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    @Override
    public int compareTo(SearchWordContent o) {
        return this.id.compareTo(o.id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        SearchWordContent oUni = (SearchWordContent)o;
        return oUni.id == this.id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
