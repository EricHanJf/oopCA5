package org.example.JSON;

import com.google.gson.Gson;
import org.example.DTOs.Task;

import java.util.List;

public class JsonConv {
    private final Gson gson;

//    public JsonConv(Gson gson) {
//        this.gson = gson;
//    }
    public JsonConv() {
        this.gson = new Gson();
    }

    // Feature 7 - Convert List of Entities to a JSON String
    public String entitiesListToJson(List<Task> list) {
        return gson.toJson(list);
    }

    // Feature 8 – Convert a single Entity by Key as a JSON String
    public String entityToJson(Task t) {
        return gson.toJson(t);
    }
}
