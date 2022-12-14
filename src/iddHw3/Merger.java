package iddHw3;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.HashMap;
import java.util.*;

public class Merger {

    private IndexReader reader;

    private IndexSearcher searcher;

    private Map<String, Integer> set2count;

    public Merger(Directory directory) throws IOException {
        this.reader = DirectoryReader.open(directory);
        this.searcher = new IndexSearcher(reader);
        this.set2count = new HashMap<String, Integer>();
    }

    //prende in input il size restituito da indexDocs all'interno di LuceneIndexWriter
    public void merge(LinkedList<String> termList, int size) throws IOException, ParseException {

        for (String term : termList) {

            System.out.println("\nRicerca in corso sul termine: "+term);
            QueryParser parser = new QueryParser("keywords", new StandardAnalyzer());
            Query query = parser.parse(String.valueOf(new TermQuery(new Term("keywords", term))));
            TopDocs hits = searcher.search(query,size);

            for (int i = 0; i < hits.scoreDocs.length; i++) {
                ScoreDoc scoreDoc = hits.scoreDocs[i];
                Document doc = searcher.doc(scoreDoc.doc);
                String id = String.valueOf(doc.get("id"));

                if (this.set2count.containsKey(id)) {
                    this.set2count.put(id, this.set2count.get(id) + 1);
                    System.out.println("Incremento contatore per la tabella " + id + " contentente il termine " + term);
                }
                else {
                    System.out.println("Aggiungo al set2count la tabella "+ id + " contenente il termine " + term);
                    set2count.put(id, 1);
                }
            }
        }

        System.out.println("\nLa query ha prodotto i seguenti risultati:\n");

        //ordina il set2count in ordine decrescente
        ArrayList<Map.Entry> listaOrdinata = new ArrayList<Map.Entry>();
        set2count
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> {
                    listaOrdinata.add(entry);
                            /**
                             * le due righe seguenti sono commentate perch?? la stampa ?? ora affidata al blocco
                             * successivo in cui si stampano solo i documenti che hanno il punteggio pi?? alto
                             */
                    //if(entry.getValue()>1)
                        //System.out.println("Doc : " + entry.getKey()  + "\t"  + " : "  + entry.getValue());
                }
                );

        /**
         * blocco di codice per la stampa dei risultati migliori.
         * Bisognerebbe aggiungere un'altra condizione che stampi anche le entries che hanno come value
         * maxValue - 1
         */
        Object maxValue = listaOrdinata.get(0).getValue();
        maxValue = (Integer) maxValue;
        for (Map.Entry entry : listaOrdinata) {
            if((entry.getValue() == maxValue)) {
                System.out.println("Doc : " + entry.getKey()  + "\t"  + " : "  + entry.getValue());
            }
        }
    }
}
