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
    public String getTypeFiled(String type) {
        System.out.println(type);
        if (type.equals("all")){
            return  "";
        }
        else if (type.equals("not concept")){
            return " AND (type:instance OR type:property)";
        }
        else if (type.equals("not instance")){
            return " AND (type:concept OR type:property)";
        }
        else if (type.equals("not property")){
            return " AND (type:concept OR type:instance)";
        }
        else {
            return " AND type:" + type;
        }
    }

//    public String getTypeFiled(String type, int flag) {
//        if (flag == 0) {
//            if (type.equals("")){
//                return  "";
//            }
//            else {
//                return " AND type:" + type;
//            }
//        }
//        else {
//            if (type.equals("")){
//                return  "AND (type:concept OR type:instance)";
//            }
//            else {
//                return " AND type///" + type;
//            }
//        }
//    }

    public String transDoc(Document document) throws IOException {
        String j = "id///";
        j = j + document.get("id") + "%%type///";
        j = j + document.get("type") + "%%name///";
        if (document.get("type").equals("concept")){
            j = j + document.get("name") + "%%url///";
            j = j + document.get("url") + "%%related///";
            j = j + document.get("related") + "%%rname///";
            j = j + transString(document.get("related")) + "%%same///";
            j = j + document.get("same") +  "%%sname///";
            j = j + transString(document.get("same"))+ "%%sub///";
            j = j + document.get("sub") + "%%subname///";
            j = j + transString(document.get("sub")) + "%%instance///";
            j = j + document.get("instance") + "%%iname///";
            j = j + transString(document.get("instance"));
        }
        else if (document.get("type").equals("instance")){
            j = j + document.get("name") + "%%url///";
            j = j + document.get("url") + "%%related///";
            j = j + document.get("related") + "%%rname///";
            j = j + transString(document.get("related")) + "%%same///";
            j = j + document.get("same") + "%%sname///";
            j = j + transString(document.get("same")) + "%%comment///";
            j = j + document.get("comment") + "%%supplement///";
            j = j + document.get("supplement") + "%%alias///";
            j = j + document.get("alias") + "%%pro///";
            j = j + dePro(document.get("pro")) + "%%pname///";
            j = j + transPro(document.get("pro"));
        }
        else {
            j = j + document.get("name") + "%%fullname///";
            j = j + document.get("fullname");
        }
        System.out.println(j);
        return j;
    }

    public String transString(String s) throws IOException {
        if (s.equals("[]")){
            return s;
        }
        String[] slist = s.substring(1, s.length() - 1).split(", ");
        String res = "[";
        for (String tmp : slist){
            String t = tmp.substring(1, tmp.length() - 1);
            String name = searchByID(t, false);
            res = res + name + ", ";
        }
        res = res.substring(0, res.length() - 2) + "]";
        return res;
    }

    public String dePro(String s) {
        String all = "[";
        String[] slist = s.substring(1, s.length() - 1).split(", ");
        for (String tmp : slist){
            if (all.contains(tmp)){
                continue;
            }
            all = all + tmp;
        }
        all = all.substring(0, all.length() - 1) + "]";
        return all;
    }

    public String transPro(String s) throws IOException {
//        System.out.println(s);
        String [] ll = new String[2];
        if (s.equals("[]")){
            return s;
        }
        String[] slist = s.substring(1, s.length() - 1).split(", ");
        String res = "[";
        String all = "";
        for (String tmp : slist){
            if (all.contains(tmp)){
                continue;
            }
            all = all + tmp;
            String t = tmp.substring(1, tmp.length() - 1);
            if (!t.contains("-")){
                continue;
            }
            String[] tlist = t.split("-");
            t = t .replaceFirst(tlist[0] + "-", "");
            String name = searchByID(tlist[0], false);
            res = res + name + ", ";
        }
        res = res.substring(0, res.length() - 2) + "]";
        return res;
    }

    public String transDocLess(Document document){
        String j = "id///";
        j = j + document.get("id") + "%%type///";
        j = j + document.get("type") + "%%name///";
        if (document.get("type").equals("concept")){
            j = j + document.get("name") + "%%url///";
            j = j + document.get("url");
        }
        else if (document.get("type").equals("instance")){
            j = j + document.get("name") + "%%url///";
            j = j + document.get("url") + "%%comment///";
            j = j + document.get("comment");
        }
        else {
            j = j + document.get("name") + "%%name///";
            j = j + document.get("name") + "%%fullname///";
            j = j + document.get("fullname");
        }
        return j;
    }

    public String searchByWord(String keyword, String type) throws IOException {
        Query query = null;
        try {
            IKAnalyzer analyzer = new IKAnalyzer();
            //4创建搜索用的query,指定搜索域
            query = new QueryParser("name", analyzer).parse(keyword + getTypeFiled(type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Directory directory = FSDirectory.open(new File("indexSource").toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(query, 20);
//        System.out.println(topDocs.scoreDocs.length);
        String res = topDocs.scoreDocs.length + "%%%";
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document hitDoc = searcher.doc(scoreDoc.doc);
            res = res + transDocLess(hitDoc) + "%%%";
        }
        return res;
    }
    public String searchByID(String id, boolean flag) throws IOException {
        Directory directory = FSDirectory.open(new File("indexSource").toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = new TermQuery(new Term("id", id));
        TopDocs topDocs = searcher.search(query, 1);
//        System.out.println("id:" + id);
        Document doc = searcher.doc(topDocs.scoreDocs[0].doc);
//        return doc.get("pro");
        if (flag){
            return transDoc(doc);
        }else {
            return doc.get("name");
        }
    }

//    public String getNameById(String id) throws IOException {
//        Directory directory = FSDirectory.open(new File("indexSource").toPath());
//        IndexReader reader = DirectoryReader.open(directory);
//        IndexSearcher searcher = new IndexSearcher(reader);
//        Query query = new TermQuery(new Term("id", id));
//        TopDocs topDocs = searcher.search(query, 1);
//        Document doc = searcher.doc(topDocs.scoreDocs[0].doc);
//        return doc.get("name");
//    }

    public String searchWhatRelation(String element1, String element2, String type1, String type2) throws IOException {
        Directory directory = FSDirectory.open(new File("indexSource").toPath());
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query1 = null;
        Query query2 = null;
        try {
            IKAnalyzer analyzer = new IKAnalyzer();
            //4创建搜索用的query,指定搜索域
            query1 = new QueryParser("name", analyzer).parse(element1 + getTypeFiled(type1));
            query2 = new QueryParser("name", analyzer).parse(element2 + getTypeFiled(type2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String res = "";
        int cnt = 0;
        TopDocs topDocs1 = searcher.search(query1, 50);
        TopDocs topDocs2 = searcher.search(query2, 50);
        if (topDocs1.scoreDocs.length == 0 || topDocs2.scoreDocs.length == 0) {
            return 0 + "%%%";
        }
        ScoreDoc[] docList1 = topDocs1.scoreDocs;
        ScoreDoc[] docList2 = topDocs2.scoreDocs;
        for (ScoreDoc scoreDoc1 : docList1) {
            if (!type2.equals("all")){
                break;
            }
            Document doc = searcher.doc(scoreDoc1.doc);
            if (doc.get("type").equals("instance")){
                if (doc.get("alias").contains(element2)){
                    res = res + "alias" + "=-=" + transDocLess(doc) + "=-=" + element2 + "%%%";
                    cnt++;
//                    System.out.println(doc.get("name") + " alias " + doc.get("alias"));
                }
                if (doc.get("supplement").contains(element2)){
                    res = res + "supplement" + "=-=" + transDocLess(doc) + "=-=" + element2 + "%%%";
                    cnt++;
//                    System.out.println(doc.get("name") + " supplement " + doc.get("supplement"));
                }
                if (doc.get("comment").contains(element2)){
                    res = res + "comment" + "=-=" + transDocLess(doc) + "=-=" + element2 + "%%%";
                    cnt++;
//                    System.out.println(doc.get("name") + " comment " + doc.get("comment"));
                }
            }
        }
        for (ScoreDoc scoreDoc2 : docList2) {
            if (!type1.equals("all")){
                break;
            }
            Document doc = searcher.doc(scoreDoc2.doc);
            if (doc.get("type").equals("instance")){
                if (doc.get("alias").contains(element1)){
                    res = res + "alias" + "=-=" + transDocLess(doc) + "=-=" + element1 + "%%%";
                    cnt++;
//                    System.out.println(doc.get("name") + " alias " + doc.get("alias"));
                }
                if (doc.get("supplement").contains(element1)){
                    res = res + "supplement" + "=-=" + transDocLess(doc) + "=-=" + element1 + "%%%";
                    cnt++;
//                    System.out.println(doc.get("name") + " supplement " + doc.get("supplement"));
                }
                if (doc.get("comment").contains(element1)){
                    res = res + "comment" + "=-=" + transDocLess(doc) + "=-=" + element1 + "%%%";
                    cnt++;
//                    System.out.println(doc.get("name") + " comment " + doc.get("comment"));
                }
            }
        }
        for (ScoreDoc scoreDoc1 : docList1) {
            Document doc1 = searcher.doc(scoreDoc1.doc);
            String id1 = doc1.get("id");
            String name1 = doc1.get("name");
            String Type1 = doc1.get("type");
            for (ScoreDoc scoreDoc2 : docList2) {
                Document doc2 = searcher.doc(scoreDoc2.doc);
                String id2 = doc2.get("id");
                String name2 = doc2.get("name");
                String Type2 = doc2.get("type");
                if (Type1.equals("concept")){
                    if (doc1.get("related").contains(id2)){
                        res = res + "related" + "=-=" + transDocLess(doc1) + "=-=" + transDocLess(doc2) + "%%%";
                        cnt++;
//                        System.out.println(name1 + " related " + name2);
                    }
                    if (doc1.get("same").contains(id2)){
                        res = res + "same" + "=-=" + transDocLess(doc1) + "=-=" + transDocLess(doc2) + "%%%";
                        cnt++;
//                        System.out.println(name1 + " same " + name2);
                    }
                    if (doc1.get("sub").contains(id2)){
                        res = res + "sub" + "=-=" + transDocLess(doc1) + "=-=" + transDocLess(doc2) + "%%%";
                        cnt++;
//                        System.out.println(name1 + " sub " + name2);
                    }
                    if (doc1.get("instance").contains(id2)){
                        res = res + "instanceof" + "=-=" + transDocLess(doc1) + "=-=" + transDocLess(doc2) + "%%%";
                        cnt++;
//                        System.out.println(name1 + " instanceof " + name2);
                    }
                }
                else if (Type1.equals("instance")) {
                    if (doc1.get("related").contains(id2)){
                        res = res + "related" + "=-=" + transDocLess(doc1) + "=-=" + transDocLess(doc2) + "%%%";
                        cnt++;
//                        System.out.println(name1 + " related " + name2);
                    }
                    if (doc1.get("same").contains(id2)){
                        res = res + "same" + "=-=" + transDocLess(doc1) + "=-=" + transDocLess(doc2) + "%%%";
                        cnt++;
//                        System.out.println(name1 + " same " + name2);
                    }
                }
                else {
                    if (Type2.equals("instance") && doc2.get("pro").contains(id1)){
                        res = res + "property" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name2 + " property " + name1);
                    }
                }
                if (Type2.equals("concept")){
                    if (doc2.get("related").contains(id1)){
                        res = res + "related" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name2 + " related " + name1);
                    }
                    if (doc2.get("same").contains(id1)){
                        res = res + "same" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name2 + "same " + name1);
                    }
                    if (doc2.get("sub").contains(id1)){
                        res = res + "subclass" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name2 + " sub " + name1);
                    }
                    if (doc2.get("instance").contains(id1)){
                        res = res + "instanceof" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name2 + " instanceof " + name1);
                    }
                }
                else if (Type2.equals("instance")) {
                    if (doc2.get("related").contains(id1)){
                        res = res + "related" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name2 + " related " + name1);
                    }
                    if (doc2.get("same").contains(id1)){
                        res = res + "same" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name2 + "same " + name1);
                    }
                }
                else {
                    if (Type1.equals("instance") && doc1.get("pro").contains(id2)){
                        res = res + "property" + "=-=" + transDocLess(doc2) + "=-=" + transDocLess(doc1) + "%%%";
                        cnt++;
//                        System.out.println(name1 + " pro " + name2);
                    }
                }
            }
        }

        res = cnt + "%%%" + res;
        System.out.println(res);
        return res;
    }
}
