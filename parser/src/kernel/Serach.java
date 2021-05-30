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

import static org.apache.lucene.queryparser.classic.QueryParserConstants.AND;

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

//        TermQuery termQuery =
//        BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
//        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(clause).build();
        Query query = new FuzzyQuery(new Term("name", keyword), 1);
        TopDocs topDocs = searcher.search(query, 100);
        System.out.println(topDocs.scoreDocs.length);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document hitDoc = searcher.doc(scoreDoc.doc);
            System.out.println(hitDoc.get("name"));
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

    public String getTypeFiled(String type, int flag) {
        if (flag == 0) {
            if (type.equals("")){
                return  "";
            }
            else {
                return " AND type:" + type;
            }
        }
        else {
            if (type.equals("")){
                return  "AND (type:concept OR type:instance)";
            }
            else {
                return " AND type:" + type;
            }
        }
    }

    public void searchByWord(String keyword, String type) throws IOException {

        Query query = null;
        try {
            IKAnalyzer analyzer = new IKAnalyzer();
            //4创建搜索用的query,指定搜索域
            query = new QueryParser("name", analyzer).parse(keyword + getTypeFiled(type, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Directory directory = FSDirectory.open(new File("indexSource").toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(query, 100);
        System.out.println(topDocs.scoreDocs.length);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document hitDoc = searcher.doc(scoreDoc.doc);
            System.out.println(hitDoc.get("name") + " " + hitDoc.get("type"));
        }
    }

    public void searchWhatRelation(String element1, String element2, String type1, String type2) throws IOException {
        Directory directory = FSDirectory.open(new File("indexSource").toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query1 = null;
        Query query2 = null;
        try {
            IKAnalyzer analyzer = new IKAnalyzer();
            //4创建搜索用的query,指定搜索域
            query1 = new QueryParser("name", analyzer).parse(element1 + );
            query2 = new QueryParser("name", analyzer).parse(element2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TopDocs topDocs1 = searcher.search(query1, 50);
        TopDocs topDocs2 = searcher.search(query1, 50);
        if (topDocs1.scoreDocs.length == 0 || topDocs2.scoreDocs.length == 0) {
            return;
        }
        ScoreDoc[] docList1 = topDocs1.scoreDocs;
        ScoreDoc[] docList2 = topDocs2.scoreDocs;
        for (ScoreDoc scoreDoc1 : docList1) {
            Document doc1 = searcher.doc(scoreDoc1.doc);
            for (ScoreDoc scoreDoc2 : docList2) {
                Document doc2 = searcher.doc(scoreDoc2.doc);
                String id1 = doc1.get("id");
                String id2 = doc2.get("id");
                if (doc1.get("relation").contains(id2) || doc2.get("relation").contains(id1)){
                    System.out.println("related");
                }
                else if (doc1.get("same").contains(id2) || doc2.get("same").contains(id1)) {
                    System.out.println("same");
                }
            }
        }
//        Document doc1 = searcher.doc(topDocs1.scoreDocs[0].doc);
//        Document doc2 = searcher.doc(topDocs1.scoreDocs[0].doc);

    }
}
