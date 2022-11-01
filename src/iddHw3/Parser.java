package iddHw3;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private String jsonFilePath;

    public Parser(String path) {
        this.jsonFilePath = path;
    }

    /**
     * Parse a Json file.
     */
    public JSONArray parseJSONFile(){

        //Get the JSON file, in this case is in ~/Resources/sampleDataSet.json
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray)new JSONParser().parse(new FileReader(jsonFilePath));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonArray;
    }


}
