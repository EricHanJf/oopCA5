package org.example.JSON;

//import com.google.gson.Gson;
//import org.example.DTOs.Task;
//
//import java.util.List;
//
//public class JsonConv {
//    private final Gson gson;
//
////    public JsonConv(Gson gson) {
////        this.gson = gson;
////    }
//    public JsonConv() {
//        this.gson = new Gson();
//    }
//
//    // Feature 7 - Convert List of Entities to a JSON String
//    public String entitiesListToJson(List<Task> list) {
//        return gson.toJson(list);
//    }
//
//    // Feature 8 – Convert a single Entity by Key as a JSON String
//    public String entityToJson(Task t) {
//        return gson.toJson(t);
//    }
//}
import com.google.gson.Gson;
import org.example.DTOs.Task;
//import com.dkit.oop.sd2.DTOs.Task;

//public class JsonConv {
//
//    private Gson gsonParser;
//
//
//
//
//}
import java.util.List;


// meghan <3
public class JsonConv {


    private static Gson gson = new Gson();

    /**
     * Converting our list of entities to json strings <3
     * <p>
     * // meghan keightley
     * JSON representation of the list of tasks
     */

    //will take objects list to output
    public static String TaskConversionToJson(List<Task> list) {
        return gson.toJson(list);
    }

}

