package parse;

import java.io.IOException;

public class testClass {
    public static void main(String[] args) throws IOException {
//        String s = "\"《 [[bd5224288|烈日灼心]]》是由蓝色星空影业、 [[bd1743080|博纳影业]]联合出品的犯罪悬疑片，由 曹保平编剧并执导， [[bd7279496|邓超]]、 [[bd4644080|段奕宏]]、 郭涛、 [[bd5456277|王珞丹]]等主演，于2015年8月27日在中国内地上映   。::;该片改编自女作家 [[bd7879704|须一瓜]]的长篇小说《 [[bd2509809|太阳黑子]]》，讲述了三个身份各异的结拜兄弟共同抚养一个孤女，在巧合之下牵扯出一桩陈年大案的故事。::;2015年6月21日，该片在 [[bd5987789|第18届上海国际电影节]]获得最佳男演员、最佳导演奖   ， 第31届中国电影金鸡奖获得最佳男主角奖   。\"@bd .";
//        String s1 = "<eni40164> rdfs:comment \"'''Pike''' is an interpreted, general-purpose, high-level, cross-platform, dynamic programming language, with a syntax similar to that of [[en2687|C]]. Unlike many other dynamic languages, Pike is both statically and dynamically typed, and requires explicit type definitions. It features a flexible type system that allows the rapid development and flexible code of dynamically typed languages, while still providing some of the benefits of a statically typed language.::;Pike features garbage collection, advanced data types, and first-class anonymous functions, with support for many programming paradigms, including object-oriented, functional and imperative programming. Pike is free software, released under the [[en2216362|GPL]], LGPL and [[en224546|MPL]] licenses.\"@en .";
//        Instance i = new Instance("id", "name");
//        System.out.println(s1);
//        i.setComment(s1);
        long startTime = System.currentTimeMillis();
        Parser parser = Parser.getParser();
//        parser.readConcept();
        parser.readInstance();
//        parser.readProperty();
//        parser.readOthers();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
