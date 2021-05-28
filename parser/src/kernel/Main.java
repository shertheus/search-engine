package kernel;

//import org.apache.lucene.document.Document;
import py4j.GatewayServer;

import java.io.IOException;

public class Main {
    public createIndex index;

    public Main() throws IOException {
        this.index = new createIndex();
    }

    public boolean getString(String id, String name, String url, String related, String same, String sub, String instance) {
//        getString
        this.index.addConcept(id, name, url, related, same, sub, instance);
        System.out.println(related);
        return true;
    }
    public int randInt(String id, String name, String url, String related, String same, String sub, String instance) {
        System.out.println("related");
        return 1;
    }

    public static void main(String[] args) throws IOException {

        Main app = new Main();
        // py4j服务
        GatewayServer gatewayServer = new GatewayServer(app);
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }
}
