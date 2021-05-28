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
        for (int i = 0; i < num; i++) {
            br.readLine();
        }
    }

    public String getString(String pattern, String line) {
        Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = r.matcher(line);
        m.find();
//        if (!m.find()){
//            System.out.println("alsls");
//        }
        return m.group();
    }


    public void readConcept() throws IOException {
        BufferedReader br = getBr("\\xlore.concept.list.ttl");
        String lineTxt = null;
        jumpLines(br, 5);
        while ((lineTxt = br.readLine()) != null) {
            if (lineTxt.contains(" owl:")) {
                continue;
            }
            String id = getString("[<](.*?)[>]", lineTxt);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            id = id.substring(1, id.length() - 1);
            name = name.substring(1, name.length() - 1);
            conceptMap.put(id, new Concept(id, name));
        }
        br.close();
        System.out.println("concept.list Done");

        br = getBr("\\xlore.concept.related.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id1).addRelated(id2);
        }
        br.close();
        System.out.println("concept.related Done");

        br = getBr("\\xlore.concept.sameAs.ttl");
        jumpLines(br, 5);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id1).addSame(id2);
        }
        br.close();
        System.out.println("concept.sameAs Done");

        br = getBr("\\xlore.concept.url.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[<](.*?)[>]", lineTxt);
            lineTxt = lineTxt.replaceFirst("[<](.*?)[>]", "");
            id1 = id1.substring(1, id1.length() - 1);
            String url = getString("[\"](.*?)[\"]", lineTxt);
            url = url.substring(1, url.length() - 1);
            conceptMap.get(id1).setUrl(url);
        }
        br.close();
        System.out.println("concept.url Done");

        br = getBr("\\xlore.subclassOf.ttl");
        jumpLines(br, 4);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id2).addSub(id1);
        }
        br.close();
        System.out.println("subclassOf Done");

        br = getBr("\\xlore.instanceOf.ttl");
        jumpLines(br, 4);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            conceptMap.get(id2).addInstance(id1);
        }
        br.close();
        System.out.println("instanceOf Done");
    }

    public void readInstance() throws IOException {
        BufferedReader br = getBr("\\xlore.instance.list.ttl");
        String lineTxt = null;
        jumpLines(br, 19);
        while ((lineTxt = br.readLine()) != null) {
            if (lineTxt.contains(" owl:")) {
                continue;
            }
            String id = getString("[<](.*?)[>]", lineTxt);
            id = id.substring(1, id.length() - 1);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            name = name.substring(1, name.length() - 1);
            if (lineTxt.contains("property:supplement")) {
                instanceMap.get(id).setSup_alias(name, 1);
            } else if (lineTxt.contains("property:alias")) {
                instanceMap.get(id).setSup_alias(name, 0);
            } else {
                instanceMap.put(id, new Instance(id, name));
            }
        }
        br.close();
        System.out.println("instance.list Done");

        br = getBr("\\xlore.instance.related.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            instanceMap.get(id1).addRelated(id2);
        }
        br.close();
        System.out.println("instance.related Done");

        br = getBr("\\xlore.instance.related.ttl");
        jumpLines(br, 5);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            instanceMap.get(id1).addSame(id2);
        }
        br.close();
        System.out.println("instance.related Done");

        br = getBr("\\xlore.instance.url.ttl");
        jumpLines(br, 11);
        while ((lineTxt = br.readLine()) != null) {
            String id = getString("[<](.*?)[>]", lineTxt);
            lineTxt = lineTxt.replaceFirst("[<](.*?)[>]", "");
            id = id.substring(1, id.length() - 1);
            String url = getString("[\"](.*?)[\"]", lineTxt);
            url = url.substring(1, url.length() - 1);
            instanceMap.get(id).setUrl(url);
        }
        br.close();
        System.out.println("instance.url Done");

        br = getBr("\\xlore.infobox.ttl");
        jumpLines(br, 4);
        while ((lineTxt = br.readLine()) != null) {
            String id1 = getString("[a-z]+[\\d]+", lineTxt);
            lineTxt = lineTxt.replaceFirst("[a-z]+[\\d]+", "");
            String id2 = getString("[a-z]+[\\d]+", lineTxt);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            name = name.substring(1, name.length() - 1);
            instanceMap.get(id1).addProperty(id2 + "|-" + name);
        }
        br.close();
        System.out.println("infobox Done");

        br = getBr("\\xlore.instance.text.ttl");
        jumpLines(br, 3);
        while ((lineTxt = br.readLine()) != null) {
            String id = getString("[<](.*?)[>]", lineTxt);
            lineTxt = lineTxt.substring(id.length() + 15, lineTxt.length() - 6);
            id = id.substring(1, id.length() - 1);
            if (lineTxt.length() == 0) {
                continue;
            }
            instanceMap.get(id).setComment(lineTxt);
        }
        br.close();
        System.out.println("instance.text Done");
    }

    public void readProperty() throws IOException {
        BufferedReader br = getBr("\\xlore.property.list.ttl");
        String lineTxt = null;
        jumpLines(br, 13);
        while ((lineTxt = br.readLine()) != null) {
            if (lineTxt.contains(" owl:")) {
                continue;
            }
            String id = getString("[<](.*?)[>]", lineTxt);
            id = id.substring(1, id.length() - 1);
            String name = getString("[\"](.*?)[\"]", lineTxt);
            name = name.substring(1, name.length() - 1);
            propertyMap.put(id, new Property(id, name));
        }
        br.close();
        System.out.println("property.list Done");
    }
}
