package parse;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private HashMap<String, Concept> conceptMap;
    private HashMap<String, Instance> instanceMap;
    private HashMap<String, Property> propertyMap;
    private static Parser parser = new Parser();
    private String path;
//    Logger logger = LoggerFactory.getLogger(Parser.class);

    private Parser() {
        conceptMap = new HashMap<>();
        instanceMap = new HashMap<>();
        propertyMap = new HashMap<>();
        path = "C:\\Users\\shertheus\\Downloads\\ttl";
    }

    public static Parser getParser() {
        return parser;
    }

    public void addConcept(String s, Concept c) {
        conceptMap.put(s, c);
    }

    public void addInstance(String s, Instance i) {
        instanceMap.put(s, i);
    }

    public void addProperty(String s, Property p) {
        propertyMap.put(s, p);
    }

    public BufferedReader getBr(String p) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path + p), StandardCharsets.UTF_8));
    }

    public void jumpLines(BufferedReader br, int num) throws IOException {
        for (int i = 0; i < num; i++){
            br.readLine();
        }
    }

    public String getString(String pattern, String line){
        Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = r.matcher(line);
        m.find();
//        if (!m.find()){
//            System.out.println("alsls");
//        }
        return m.group();
    }

    public String spiltString(int flag, String s){
        String a;
        String b;
        if (flag == 1){
            a = "<";
            b = ">";
        }
        else {
            a = "\"";
            b = "\"";
        }
        return s.split(a)[1].split(b)[0];
    }

    public void readConcept() throws IOException {
        BufferedReader br = getBr("\\xlore.concept.list.ttl");
        String lineTxt = null;
        jumpLines(br, 5);
        while ((lineTxt = br.readLine()) != null) {
            if (lineTxt.contains(" owl:")){
                continue;
            }
            String id = getString("[<](.*?)[>]", lineTxt);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            id = spiltString(1, id);
            name = spiltString(0, name);
            conceptMap.put(id, new Concept(id, name));
        }
        br.close();

        br = getBr("\\xlore.concept.related.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id1).addRelated(id2);
        }
        br.close();

        br = getBr("\\xlore.concept.sameAs.ttl");
        jumpLines(br, 5);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id1).addSame(id2);
        }
        br.close();

        br = getBr("\\xlore.concept.url.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[<](.*?)[>]", lineTxt);
            lineTxt = lineTxt.replaceFirst("[<](.*?)[>]", "");
            id1 = spiltString(1, id1);
            String url = getString("[\"](.*?)[\"]", lineTxt);
            url = spiltString(0, url);
            conceptMap.get(id1).setUrl(url);
        }
    }

    public void readInstance() throws IOException {
        BufferedReader br = getBr("\\xlore.instance.list.ttl");
        String lineTxt = null;
        jumpLines(br, 19);
        while ((lineTxt = br.readLine()) != null) {
            if (lineTxt.contains(" owl:")){
                continue;
            }
            String id = getString("[<](.*?)[>]", lineTxt);
            id = spiltString(1, id);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            name = spiltString(0, name);
            if (lineTxt.contains("property:supplement")) {
                instanceMap.get(id).setSup_alias(name, 1);
            }
            else if (lineTxt.contains("property:alias")) {
                instanceMap.get(id).setSup_alias(name, 0);
            }
            else {
                instanceMap.put(id, new Instance(id, name));
            }
        }

        br = getBr("\\xlore.instance.related.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            instanceMap.get(id1).addRelated(id2);
        }

        br = getBr("\\xlore.instance.url.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id = getString("[<](.*?)[>]", lineTxt);
            lineTxt = lineTxt.replaceFirst("[<](.*?)[>]", "");
            id = spiltString(1, id);
            String url = getString("[\"](.*?)[\"]", lineTxt);
            url = spiltString(0, url);
            instanceMap.get(id).setUrl(url);
        }

        br = getBr("\\xlore.instance.text.ttl");
        jumpLines(br, 3);
        while ((lineTxt = br.readLine()) != null) {
            if (lineTxt.contains(" owl:")){
                continue;
            }
            String id = getString("[<](.*?)[>]", lineTxt);
            id = spiltString(1, id);
            String comment = getString("[\"](.*?)[\"]", lineTxt);
            comment = spiltString(0, comment);
            instanceMap.get(id).setComment(comment);
        }
    }

    public void readProperty() throws IOException {
        BufferedReader br = getBr("\\xlore.property.list.ttl");
        String lineTxt = null;
        jumpLines(br, 13);
        while ((lineTxt = br.readLine()) != null) {
            if (lineTxt.contains(" owl:")){
                continue;
            }
            String id = getString("[<](.*?)[>]", lineTxt);
            id = spiltString(1, id);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            name = spiltString(0, name);
            propertyMap.put(id, new Property(id, name));
        }
    }

    public void readOthers() throws IOException {
        BufferedReader br = getBr("\\xlore.infobox.ttl");
        String lineTxt = null;
        jumpLines(br, 4);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            name = spiltString(0, name);
            instanceMap.get(id1).addProperty(id2 + "|-" + name);
        }

        br = getBr("\\xlore.subclassOf.ttl");
        jumpLines(br, 4);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id2).addSub(id1);
        }

        br = getBr("\\xlore.instanceOf.ttl");
        jumpLines(br, 4);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id2).addInstance(id1);
        }
    }
}
