package iddHw3;

public class Main {
	
	public static void main(String[] args) {
		LuceneIndexWriter liw = new LuceneIndexWriter();
		liw.createIndex();
		System.out.println("MAIN: Sono stati indicizzati "+liw.tableCounter+" documenti.");

		//va creata la funzione che salvi come LinkedList la query da fare in modo da passarla in input al merge
		//Merger merge = new Merger("target/idx");
		//merge(listaDaMettere, liw.tableCounter);
	}
}
