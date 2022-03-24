package com.entelgy.retotecnico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Data {
    @JsonIgnore
    private List<Person> personas;
    private List<String> data = new ArrayList<>();

    public Data() {
    }

    public Data(List<Person> personas) {
        this.personas = personas;
    }

    public List<Person> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Person> personas) {
        this.personas = personas;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void createConcat(){
        for (int i = 0; i<personas.size(); i++){
            Person p = personas.get(i);
            String d = p.getId().toString() + "|" + p.getLast_name() + "|" + p.getEmail();
            data.add(d);
        }
    }
}
