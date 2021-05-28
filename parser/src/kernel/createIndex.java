package kernel;



import org.apache.lucene.analysis.Analyzer;
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
    public void addConcept(String id, String name, String url, String related, String same, String sub, String instance) {
//        Document document = new Document();
        System.out.println(related);
    }
}
