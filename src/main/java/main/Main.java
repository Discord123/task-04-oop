package main;


import dao.XMLDaoFactory;
import xml.Node;
import xml.NodeType;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        String breakfastMenu= "breakfastMenu.xml";
        String nodes = "nodes.xml";
        URL filePath = XMLDaoFactory.class.getClassLoader().getResource(breakfastMenu);

        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath.getFile(),"r");

//        List<String> list = new ArrayList<>();
//        String s;
//        while ((s = randomAccessFile.readLine()) != null) {
//            list.add(s);
//        }
//
//        for (String ss : list) {
//            System.out.println(ss);
//        }

        long startPosition = 0;
        long endPosition = 0;

        String tagStartName = "<breakfast-menu>";
        String tagEndName = "</breakfast-menu>";

        String s;
        while ((s = randomAccessFile.readLine()) != null) {
            System.out.println(checkNodeType(s));
        }

    }

    public static Node createFirstNode(RandomAccessFile randomAccessFile) throws IOException {
        Node node = new Node();

        String firstLine = randomAccessFile.readLine();
        String secondLine;

        if (checkNodeType(firstLine) == NodeType.ELEMENT_NODE){
            secondLine = randomAccessFile.readLine();
            nodeCreator(secondLine,randomAccessFile);
        } else {
            nodeCreator(firstLine,randomAccessFile);
        }

        return node;
    }

    public static Node nodeCreator(String s,RandomAccessFile randomAccessFile){
        Node node = new Node();



        return node;
    }

    public static Node nodeBuilder(NodeType nodeType){


        return new Node();
    }

    public static NodeType checkNodeType(String s) {

        System.out.println(s);

        Pattern patternAtr = Pattern.compile("\\s*<.+>");
        Matcher matcherAtr = patternAtr.matcher(s);

        Pattern patternEnd = Pattern.compile("\\s*</.+>");
        Matcher matcherEnd = patternEnd.matcher(s);

        Pattern patternEmpty = Pattern.compile("\\s+<.+/>");
        Matcher matcherEmpty = patternEmpty.matcher(s);

        Pattern patternStart = Pattern.compile("\\s*<.+>");
        Matcher matcherStart = patternStart.matcher(s);

        Pattern patternFill = Pattern.compile("\\s*<.+>.*</.+>");
        Matcher matcherFill = patternFill.matcher(s);

        if(s.contains("<?") && s.contains("?>")) {
            return NodeType.ELEMENT_NODE;
        }
        if(matcherAtr.lookingAt() && s.contains("\"")){
            return NodeType.ATTRIBUTE_NODE;
        }
        if(matcherEnd.lookingAt()){
            return NodeType.END_NODE;
        }
        if(matcherEmpty.lookingAt()){
            return NodeType.EMPTY_NODE;
        }
        if(matcherFill.lookingAt()){
            return NodeType.FILL_NODE;
        }
        if(matcherStart.lookingAt()){
            return NodeType.START_NODE;
        }

        return null;
    }
}
