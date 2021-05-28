package parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instance {
    private final String id, name;
    private  String url, comment;
    private List<String> related, same, property, comment_ins, supplement, alias;
    public Instance (String id, String name) {
        this.id = id;
        this.name = name;
        this.url = "";
        this.comment = "";
        this.related = new ArrayList<>();
        this.same = new ArrayList<>();
        this.property = new ArrayList<>();
        this.comment_ins = new ArrayList<>();
        this.alias = new ArrayList<>();
        this.supplement = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getComment() {
        return comment;
    }

    public void setSup_alias(String s, int flag) {
        if (flag == 1) {
            supplement.add(s);
        }
        else {
            alias.add(s);
        }
    }

    public List<String> getRelated() {
        return related;
    }

    public List<String> getSame() {
        return same;
    }

    public List<String> getProperty() {
        return property;
    }

    public List<String> getComment_ins() {
        return comment_ins;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setComment (String c) {
        String pattern = "\\[\\[(.*?)\\]\\]";
        Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = r.matcher(c);
        while (m.find()){
            String tmp = m.group().split("\\[\\[")[1].split("\\]\\]")[0];
            if (tmp.contains("|")){
                String[] t = tmp.split("\\|");
                if (t[1].contains("$")) {
                    t[1] = t[1].replaceAll("\\$", "CHAR_DOLLAR");
                }
                c = c.replaceFirst("\\[\\[(.*?)\\]\\]", t[1]);
                this.comment_ins.add(t[0]);
            }
            else{
                c = c.replaceFirst("\\[\\[(.*?)\\]\\]", tmp);
                this.comment_ins.add(tmp);
            }
        }
        this.comment = c;
//        System.out.println(c);
//        for (String comment_in : this.comment_ins) {
//            System.out.println(comment_in);
//        }
    }

    public void addRelated (String r) {
        related.add(r);
    }

    public void addSame (String s) {
        same.add(s);
    }

    public void addProperty (String p) {
        property.add(p);
    }
}