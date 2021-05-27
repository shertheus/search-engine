package parse;

import java.util.ArrayList;
import java.util.List;

public class Concept {
    private final String id, name;
    private  String url;
    private List<String> related, same, instance, sub;
    public Concept (String id, String name) {
        this.id = id;
        this.name = name;
        this.url = "";
        this.related = new ArrayList<>();
        this.same = new ArrayList<>();
        this.instance = new ArrayList<>();
        this.sub = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRelated() {
        return related;
    }

    public List<String> getSame() {
        return same;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getInstance() {
        return instance;
    }

    public List<String> getSub() {
        return sub;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addRelated(String r) {
        related.add(r);
    }

    public void addSame (String s) {
        same.add(s);
    }

    public void addInstance (String i) {
        instance.add(i);
    }

    public void addSub (String s) {
        sub.add(s);
    }
}
