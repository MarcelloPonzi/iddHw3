package iddHw3;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LuceneIndexWriter {

	private String indexPath = "target/idx";
	private String jsonFilePath = "Resources/sampleDataSet.json";
	private IndexWriter indexWriter = null;

	public int tableCounter = 0;

	public LuceneIndexWriter() {}

	public void createIndex(){
		JSONArray jsonArray = parseJSONFile();
		openIndex(indexPath);		
		tableCounter = indexDocs(jsonArray);
		finish();
	}

	private int indexDocs(JSONArray jsonArray) {
		int counter = 0;
		try {
			for (Object o : jsonArray) {	//questo for scorre le tabelle
				counter++;
				JSONObject table = (JSONObject) o;
				String id = (String) table.get("id");
				Document doc = new Document();


				// loop array of cells (righe)
				JSONArray celle = (JSONArray) table.get("cells");
				doc.add(new TextField("id", id, Field.Store.YES ));
				System.out.println("Creato doc con id: " + id);
				String cleanedCells = "";

				for (Object c : celle) {
					JSONObject cella = (JSONObject) c;
					cleanedCells = cleanedCells.concat(cella.get("cleanedText").toString()+"\n");
				}

				doc.add(new TextField("keywords",cleanedCells ,Field.Store.YES));
				System.out.println("Aggiunte al doc le celle con parole chiave " + cleanedCells);
				indexWriter.addDocument(doc);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.println("INDEXWRITER: Sono stati indicizzati "+counter+" documenti.");
		return counter;
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

	public boolean openIndex(String path){
		Path indexPath = Paths.get(path);
		try {
			Directory dir = FSDirectory.open(indexPath);						
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setCodec(new SimpleTextCodec());

			//Riscrivi la directory
			iwc.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(dir, iwc);

			return true;
		} catch (Exception e) {
			System.err.println("Error opening the index. " + e.getMessage());
		}
		return false;
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