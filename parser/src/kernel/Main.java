package kernel;

//import org.apache.lucene.document.Document;
import py4j.GatewayServer;

import java.io.IOException;

public class Main {
    public createIndex index;
    public Serach serach;
    public Main() throws IOException {
        this.index = new createIndex();
        this.serach = new Serach();
    }

    public boolean getConceptString(String id, String name, String url, String related, String same, String sub, String instance) throws IOException {
//        getString
        this.index.addConcept(id, name, url, related, same, sub, instance);
        return true;
    }
    public boolean getInstanceString(String id, String name, String url, String comment, String supplement, String alias,
                                     String related, String same, String comment_ins, String pro) throws IOException {
//        getString
        this.index.addInstance(id, name, url, comment, supplement, alias, related, same, comment_ins, pro);
        return true;
    }
    public boolean getPropertyString(String id, String label, String fullname) throws IOException {
//        getString
        this.index.addProperty(id, label, fullname);
        return true;
    }

    public boolean set_commit() {
        this.index.writerCommit();
        return true;
    }

    public String searchByWord(String keyword, String type) throws IOException {
        System.out.println(this.serach.searchByWord(keyword, type));
        return this.serach.searchByWord(keyword, type);
    }

    public String searchById(String id) throws IOException {
        return this.serach.searchByID(id, true);
    }

    public String searchRelation(String name1, String name2, String type1, String type2) throws IOException {
        return this.serach.searchWhatRelation(name1, name2, type1, type2);
    }
    public static void main(String[] args) throws IOException {

        Main app = new Main();
        GatewayServer gatewayServer = new GatewayServer(app);
        gatewayServer.start();
        System.out.println("Gateway Server Started");
//        Serach serach = new Serach();
//
//        System.out.println( serach.searchByID("eni2020540", true));
//        System.out.println(serach.searchByWord("北京", "all"));
//        serach.searchWhatRelation("老爷", "省", "all", "all");
    }

}
