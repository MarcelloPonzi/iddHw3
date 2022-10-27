package iddHw3;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class LuceneIndexWriter {

	String indexPath = "target/idx";

	String jsonFilePath = "Resources/sampleDataSet.json";

	IndexWriter indexWriter = null;

	public LuceneIndexWriter(String indexPath, String jsonFilePath) {
		this.indexPath = indexPath;
		this.jsonFilePath = jsonFilePath;
	}

	public void createIndex(){
		JSONArray jsonObjects = parseJSONFile();
		openIndex();
		addDocuments(jsonObjects);
		finish();
	}

	/**
	 * Parse a Json file. 
	 */
	public void parseJSONFile(){

		//Get the JSON file, in this case is in ~/target/sampleDataSet.json
		InputStream jsonFile =  getClass().getResourceAsStream(jsonFilePath);
		Reader readerJson = new InputStreamReader(jsonFile);
		JSONParser parser = new JSONParser();
		JSONArray jsonArray;
		try {
			jsonArray = (JSONArray) parser.parse(readerJson); //rivedere possibili reader

			//TO-DO crea doc da indicizzare (invocare qui) e aggiungi ai suoi field id e una stringa che è la concat. delle stringhe
			// dei cleaned text
			for (Object o : jsonArray) {

				JSONObject table = (JSONObject) o;
				String id = (String) table.get("id");

				// loop array of cells
				JSONArray celle = (JSONArray) table.get("cells");

				for (Object c : celle)
				{
					JSONObject cella = (JSONObject) c;
					String text = (String) cella.get("cleanedText"); 
				}
			}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			} 
	}


	//da aggiornare
	public boolean openIndex(){
		try {
			Directory dir = FSDirectory.open(new File(indexPath));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);

			//Always overwrite the directory
			iwc.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(dir, iwc);

			return true;
		} catch (Exception e) {
			System.err.println("Error opening the index. " + e.getMessage());

		}
		return false;
	}

	/**
	 * Add documents to the index
	 */
	public void addDocuments(JSONArray jsonObjects){
		for(JSONObject object : (List<JSONObject>) jsonObjects){
			Document doc = new Document();
			for(String field : (Set<String>) object.keySet()){
				Class type = object.get(field).getClass();
				if(type.equals(String.class)){
					doc.add(new StringField(field, (String)object.get(field), Field.Store.NO));
				}
			}
			try {
				indexWriter.addDocument(doc);
			} catch (IOException ex) {
				System.err.println("Error adding documents to the index. " +  ex.getMessage());
			}
		}
	}

	/**
	 * Write the document to the index and close it
	 */
	public void finish(){
		try {
			indexWriter.commit();
			indexWriter.close();
		} catch (IOException ex) {
			System.err.println("We had a problem closing the index: " + ex.getMessage());
		}
	}


}