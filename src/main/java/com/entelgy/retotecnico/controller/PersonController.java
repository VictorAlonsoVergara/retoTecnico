package com.entelgy.retotecnico.controller;

import com.entelgy.retotecnico.model.Data;
import com.entelgy.retotecnico.model.Person;
import com.entelgy.retotecnico.service.PersonService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    public PersonService getPersonService() {
        return personService;
    }

    @PostMapping("/restructure")
    public Data restructure(){
        JsonObject json = personService.getJSON();
        JsonArray jsonArray = json.getAsJsonArray("data");
        List<Person> personas = new ArrayList<Person>();
        for(int i=0; i<jsonArray.size();i++){
            Person p = new Person();
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            p.setId(jsonObject.get("id").getAsInt());
            p.setEmail(jsonObject.get("email").getAsString());
            p.setFirst_name(jsonObject.get("first_name").getAsString());
            p.setLast_name(jsonObject.get("last_name").getAsString());
            p.setAvatar(jsonObject.get("avatar").getAsString());
            personas.add(p);
        }
        Data data = new Data(personas);
        data.createConcat();
        return data;
    }

}
