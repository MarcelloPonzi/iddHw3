package iddHw3;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Statistiche {

    // numero totale di tabelle nel dataset calcolato durante l'indexing.
    static final int numeroTabelle = 550271;

    private String jsonFilePath = "";

    public Statistiche(String path) {       //costruttore con cui assegnare il path del json a jsonFilePath
        this.jsonFilePath = path;
    }

    //TODO numero di tabelle
    /**
     * Per ora non faccimo un metodo qui dentro perché il numero di tabelle lo abbiamo già.
     */

    //TODO numero medio di righe
    /**
     * Ogni tabella ha un campo maxDimensions che è un Object di due campi: "row" e "column" che riportano
     * rispettivamente il numero di righe e di colonne della tabella.
     * Dobbiamo sommare le righe di tutte le tabelle e dividere il totale per il numero di tabelle.
     */
    public void calcolaNumeroMedioDiDimensioni() throws IOException {
        int numeroMedioDiRighe = 0;
        int numeroMedioDiColonne = 0;
        int righeTotali = 0;
        int colonneTotali = 0;

        BufferedReader br = new BufferedReader(new FileReader(jsonFilePath));
        String line;


        while ((line=br.readLine()) != null) {
            JsonObject table = JsonParser.parseString(line).getAsJsonObject();
            JsonObject maxDimensions = table.get("maxDimensions").getAsJsonObject();
            righeTotali += maxDimensions.get("row").getAsInt();
            colonneTotali += maxDimensions.get("column").getAsInt();
        }

        numeroMedioDiRighe = righeTotali/numeroTabelle;
        numeroMedioDiColonne = colonneTotali/numeroTabelle;
        System.out.println("Numero medio di righe = "+numeroMedioDiRighe);
        System.out.println("Numero medio di colonne = "+numeroMedioDiColonne);
    }


    //TODO numero medio di valori nulli per tabella
    /**
     * In alcune tabelle c'è il campo "cleanedText":"Null" quindi andiamo a contare tutti i valori nulli
     * in tutte le tabelle e dividiamo per il numero di tabelle
     */
    public void calcolaNumeroMedioValoriNulli() throws IOException {
        int valoriNulliTotali = 0;
        int numeroMedioValoriNulli = 0;

        BufferedReader br = new BufferedReader(new FileReader(jsonFilePath));
        String line;

        while ((line=br.readLine()) != null) {
            JsonObject table = JsonParser.parseString(line).getAsJsonObject();
            JsonArray celle = (JsonArray) table.get("cells");

            for (Object c : celle) {
                JsonObject cella = (JsonObject) c;
                if(cella.get("cleanedText").getAsString().equals("")) {
                    valoriNulliTotali++;
                }
            }
        }

        numeroMedioValoriNulli = valoriNulliTotali/numeroTabelle;
        System.out.println("Numero medio di valori nulli per tabella = "+numeroMedioValoriNulli);
    }




    //TODO Distribuzione numero di righe (quante tabelle hanno 1,2,3,4, etc. righe)
    /**
     * Creiamo una mappa che ha come chiave il numero di righe preso da maxDimensions.
     * Scorriamo tutte le tabelle e ogni volta che troviamo una tabella che ha un numero di righe presente
     * nella mappa, incrementiamo il valore corrispondente a tale chiave.
     */




    //TODO Distribuzione numero di colonne (quante tabelle hanno 1, 2, 3, 4, etc. colonne)
    /**
     * Analogo a quello sopra ma col numero di colonne
     */

    //TODO Distribuzione valori distinti (quante colonne hanno 1, 2, 3, 4, etc valori distinti)
    /**
     * Altra mappa che usa come chiave il valore di cleanedText e ogni volta che trova esattamente quella chiave
     * in una tabella ne incrementa il valore.
     */

    //TODO Altro a vostra scelta

}
