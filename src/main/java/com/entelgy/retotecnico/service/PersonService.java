package com.entelgy.retotecnico.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PersonService {
    @Value("${service.url}")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JsonObject getJSON(){
        try
        {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200)
            {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = br.read()) != -1)
            {
                sb.append((char) cp);
            }
            String output = sb.toString();
            JsonObject json = new Gson().fromJson(output,JsonObject.class);
            conn.disconnect();
            return json;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
