package kernel;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class createIndex {
    Analyzer analyzer;
    // 指定索引库的地址
    Directory directory;
    // 创建IndexWriter
    private final IndexWriter writer;
    createIndex() throws IOException {
        analyzer = new IKAnalyzer();
        directory = FSDirectory.open(new File("indexSource").toPath());
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
        cfg.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        writer = new IndexWriter(directory,cfg);
    }
    public void addConcept(String id, String name, String url, String related, String same, String sub, String instance) throws IOException {
        Document doc = new Document();
        Field fid = new StringField("id",id,Field.Store.YES);
        Field ftype = new StringField("type","concept",Field.Store.YES);
        Field furl = new StringField("url",url,Field.Store.YES);
        Field fname = new TextField("name",name,Field.Store.YES);
        Field frelated = new TextField("related",related,Field.Store.YES);
        Field fsame = new TextField("same",same,Field.Store.YES);
        Field fsub = new TextField("sub",sub,Field.Store.YES);
        Field finstance = new TextField("instance",instance,Field.Store.YES);

        doc.add(fid);
        doc.add(ftype);
        doc.add(furl);
        doc.add(fname);
        doc.add(frelated);
        doc.add(fsame);
        doc.add(fsub);
        doc.add(finstance);

        writer.addDocument(doc);

    }
    public void addInstance(String id, String name, String url, String comment, String supplement, String alias,
                            String related, String same, String comment_ins, String pro) throws IOException {
        Document doc = new Document();
        Field fid = new StringField("id",id,Field.Store.YES);
        Field furl = new StringField("url",url,Field.Store.YES);
        Field fname = new TextField("name",name,Field.Store.YES);
        Field frelated = new TextField("related",related,Field.Store.YES);
        Field fsame = new TextField("same",same,Field.Store.YES);
        Field fcomment = new TextField("comment",comment,Field.Store.YES);
        Field fsupplement = new TextField("supplement",supplement,Field.Store.YES);
        Field falias = new TextField("alias",alias,Field.Store.YES);
        Field fcomment_ins = new TextField("comment_ins",comment_ins,Field.Store.YES);
        Field fpro = new TextField("pro",pro,Field.Store.YES);
        Field ftype = new StringField("type","instance",Field.Store.YES);

        doc.add(fid);
        doc.add(furl);
        doc.add(fname);
        doc.add(ftype);
        doc.add(frelated);
        doc.add(fsame);
        doc.add(fcomment);
        doc.add(fsupplement);
        doc.add(falias);
        doc.add(fcomment_ins);
        doc.add(fpro);

        writer.addDocument(doc);

    }
    public void addProperty(String id, String label, String fullname) throws IOException {
//        Document document = new Document();
//        System.out.println(id);
        Document doc = new Document();
        Field fid = new StringField("id",id,Field.Store.YES);
        Field flabel = new TextField("label",label,Field.Store.YES);
        Field ffullname = new TextField("fullname",fullname,Field.Store.YES);
        Field ftype = new StringField("type","property",Field.Store.YES);

        doc.add(fid);
        doc.add(flabel);
        doc.add(ffullname);
        doc.add(ftype);

        writer.addDocument(doc);
    }

    public void writerCommit() {
        try {
            writer.commit();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
