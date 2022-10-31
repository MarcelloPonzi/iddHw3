package iddHw3;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {
	
	public static void main(String[] args) throws IOException {
		LuceneIndexWriter liw = new LuceneIndexWriter();
		liw.createIndex();
		System.out.println("MAIN: Sono stati indicizzati "+liw.tableCounter+" documenti.\n");

		Path path = Paths.get("target/idx");
		Directory directory = FSDirectory.open(path);


		//lista di prova da dare in input al merger - DA CANCELLARE POI
		LinkedList<String> listaTest = new LinkedList<>();
		listaTest.add("Harry Potter e il calice di fuoco - Mike Newell");
		listaTest.add("Il padrino - Francis Ford Coppola");
		listaTest.add("Il gattopardo - Luchino Visconti");


		//va creata la funzione che salvi come LinkedList la query da fare in modo da passarla in input al merge
		Merger m = new Merger(directory);
		m.merge(listaTest, liw.tableCounter);
	}
}
