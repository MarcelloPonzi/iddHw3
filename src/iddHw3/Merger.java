package iddHw3;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
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

    public void merge(LinkedList<String> termList, int size) throws IOException {

        for (String term : termList) {

            System.out.println("Ricerca in corso sul termine: "+term);

            Query query = new TermQuery(new Term("keywords", term));
            TopDocs hits = searcher.search(query,size);

            for(ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                String id = String.valueOf(doc.get("id"));

                if (this.set2count.containsKey(id)) {
                    this.set2count.put(id, this.set2count.get(id) + 1);
                    System.out.println("Incremento contatore per termine " + term);
                }
                else {
                    set2count.put(id, 1);
                }
            }

            // a questo punto set2count completato
        }

        //ordina il set2count in ordine decrescente (DA TESTARE)
        set2count
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(
                        "Doc : " + entry.getKey()  + "\t\t"  + " : "  + entry.getValue()
                ));
    }
}
