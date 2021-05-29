package kernel;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Serach {
    public void queryIndex(String keyword) throws IOException {
//        Query query = null;
//        try {
//            IKAnalyzer analyzer = new IKAnalyzer();
//            //4创建搜索用的query,指定搜索域
//            query = new QueryParser("context", analyzer).parse(ques);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Directory directory = FSDirectory.open(new File("indexSource").toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        TermQuery termQuery = new TermQuery(new Term("id", keyword));
        BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(clause).build();
        TopDocs topDocs = searcher.search(booleanQuery, 100);
        System.out.println(topDocs.scoreDocs.length);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document hitDoc = searcher.doc(scoreDoc.doc);
            System.out.println(hitDoc.get("id"));
//            String j = hitDoc.get("related");
//            System.out.println(j);
//            if (j.length() > 2){
//                String[] rList = j.substring(1, j.length() - 1).replaceAll("\"", "").split(", ");
//                for (String r : rList){
//                    System.out.println(r);
//                    TermQuery termQuery2 = new TermQuery(new Term("id", r));
//                    BooleanClause clause2 = new BooleanClause(termQuery2, BooleanClause.Occur.MUST);
//                    BooleanQuery booleanQuery2 = new BooleanQuery.Builder().add(clause2).build();
//                    TopDocs tmp = searcher.search(booleanQuery, 1);
//                    ScoreDoc sd = tmp.scoreDocs[0];
//                    Document tmpdoc = searcher.doc(sd.doc);
//                    System.out.println(tmpdoc.get("id"));
//                    System.out.println(tmpdoc.get("name"));
//                    System.out.println(tmpdoc.get("type"));
//                }
//            }
//            else {
//                System.out.println(j);
//            }
        }


    }
}
