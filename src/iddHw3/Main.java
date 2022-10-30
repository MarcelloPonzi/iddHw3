package iddHw3;

public class Main {
	
	public static void main(String[] args) {
		LuceneIndexWriter liw = new LuceneIndexWriter();
		liw.createIndex();
		System.out.println("MAIN: Sono stati indicizzati "+liw.tableCounter+" documenti.");
	}
}
