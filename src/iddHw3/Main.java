package iddHw3;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {
	
	public static void main(String[] args) throws IOException, ParseException {

		// questo costruttore invoca a sua volta il costrutture del parser con il path inserito
		LuceneIndexWriter liw = new LuceneIndexWriter("Resources/tables.json");

		/**
		 * Metodo commentato perché funziona soltanto con il JSON di prova formattato.
		 * Il nostro JSON è Line-separated quindi bisogna usare il metodo apposito della classe
		 * LuceneIndexWriter chiamato parseAndCreateIndex() per evitare problemi di memoria
		 */
		//liw.createIndex();

		/**
		 * Questo metodo dovrebbe evitare il problema della memoria facendo commit ad ogni riga
		 */
		//liw.parseAndCreateIndex("Resources/tables.json");

		//System.out.println("MAIN: Sono stati indicizzati "+liw.tableCounter+" documenti.\n");

		/**
		 * Calcolo delle statistiche
		 */
		Statistiche statistiche = new Statistiche("Resources/tables.json");
		//statistiche.calcolaNumeroMedioDiDimensioni();
		//statistiche.calcolaNumeroMedioValoriNulli();
		statistiche.calcolaDistribuzioneRighe();


		/*

		Path path = Paths.get("target/idx");
		Directory directory = FSDirectory.open(path);

		//lista di prova da dare in input al merger - DA CANCELLARE POI
		LinkedList<String> listaTest = new LinkedList<>();
		listaTest.add("Harry Potter");
		listaTest.add("PADRINO");
		listaTest.add("GATTOPARDO");
		listaTest.add("qualcosa prova");

		Merger m = new Merger(directory);

		// Il numero di tabelle è 550271, trovato durante l'indicizzazione
		m.merge(listaTest, 550271);	//come secondo parametro ci va il numero di tabelle (liw.tableCounter)

		 */
	}
}
