package org.example.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Collections.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class CollectionManager {

    public String fileName;
    public static JSONArray json_file;
    //Конструктор
    public CollectionManager(String fileName){
        this.fileName = fileName;
    }
    public CollectionManager(){
        this.fileName = "/home/danp1t/github/Labs/lab5/src/main/java/org/example/Files/Collection.json";
    }
    public JSONArray read_json_file(){
        try {
            JSONArray json_file = (JSONArray) new JSONParser().parse(new FileReader(fileName));
            for (int i = 0; i < json_file.size(); i++){
                JSONObject object = (JSONObject) json_file.get(i);
                System.out.println(object);

                //int id = (int) object.get("id");
                //String name = (String) object.get("name");

                //Реализовать toString для Coordinates
                //Coordinates coordinates = (Coordinates) object.get("coordinates");
                //System.out.println(coordinates);
            }

            return json_file;
    
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка в команде show");}
        return null;
    }
    public String beatiful_output_json(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json_string = gson.toJson(read_json_file());
        return json_string;
    }
}
