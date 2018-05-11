package main;


import dao.XMLDaoFactory;
import xml.Document;
import xml.Node;
import xml.NodeList;
import xml.NodeType;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        String breakfastMenu= "breakfastMenu.xml";
        String nodes = "nodes.xml";
        URL filePath = XMLDaoFactory.class.getClassLoader().getResource(breakfastMenu);

        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath.getFile(),"r");

        Node document = firstNodeCreator(randomAccessFile);

        NodeList nodeList = getChildList(document, randomAccessFile);

        for (int i = 0; i < nodeList.size(); i++) {
            System.out.println(nodeList.item(i));
        }


        Node node = nodeList.item(0);
        System.out.println(node);
        NodeList nodeList1 = node.getChildList();
        System.out.println(nodeList1);

    }

    public static NodeList getChildList(Node node, RandomAccessFile randomAccessFile) throws IOException {
        NodeList nodeList = new NodeList();
        long nodeStartPosition = node.getStartPosition();
        long nodeEndPosition = node.getEndPosition();

        Node childNode;
        while (true) {
            childNode = recursiveNodeCreate(nodeStartPosition, nodeEndPosition, randomAccessFile);
            nodeStartPosition = childNode.getEndPosition();
            nodeList.addNode(childNode);
            if (childNode.getNextLinePosition() == nodeEndPosition){
                break;
            }
        }

        return nodeList;
    }

    public static Node recursiveNodeCreate(long nodeStartPosition, long nodeEndPosition,
                                           RandomAccessFile randomAccessFile) throws IOException {

        if(nodeStartPosition >= nodeEndPosition){
            return null;
        }

        String name;
        String startTag;
        String endTag;
        String attribute;
        long startPosition;
        long endPosition = 0;
        long nextLinePosition;

        randomAccessFile.seek(nodeStartPosition);
        startTag = randomAccessFile.readLine();

        startPosition = randomAccessFile.getFilePointer();

        startTag = startTag.substring(startTag.indexOf("<"));

        endTag = startTag.replace("<","</").split(" ")[0]+">";
        name = endTag.replace("</","").replace(">","");
        attribute = startTag.replace(name,"").replace("<","").replace(">","");

        String s;
        while ((s = randomAccessFile.readLine()) != null) {
            if (randomAccessFile.getFilePointer() < nodeEndPosition) {
                if (s.replaceAll(" ","").equals(endTag)){
                    endPosition = randomAccessFile.getFilePointer();
                    break;
                }
            }
        }

        randomAccessFile.readLine();
        nextLinePosition = randomAccessFile.getFilePointer();

        Node childNode = new Node();
        childNode.setName(name);
        childNode.setStartTag(startTag);
        childNode.setEndTag(endTag);
        childNode.setAttribute(attribute);
        childNode.setStartPosition(startPosition);
        childNode.setEndPosition(endPosition);
        childNode.setNextLinePosition(nextLinePosition);

        return childNode;
    }

    public static Node firstNodeCreator(RandomAccessFile randomAccessFile) throws IOException {
        Document document;
        Node node = new Node();
        String name;
        String startTag;
        String endTag = "";
        long startPosition;
        long endPosition = 0;

        String firstLine = randomAccessFile.readLine();

        if (checkNodeType(firstLine) == NodeType.INFORMATION_NODE){
            firstLine = randomAccessFile.readLine();
        }

        startPosition = randomAccessFile.getFilePointer();
        name = firstLine.replaceAll("<","").replaceAll(">","");
        startTag = firstLine;

        String closeTagName = firstLine.replaceAll("<","</");

        String s;
        while ((s = randomAccessFile.readLine()) != null) {
            if(s.replaceAll(" ","").equals(closeTagName.replaceAll(" ",""))){
                endPosition = randomAccessFile.getFilePointer();
                endTag = s;
            }
        }

        node.setName(name);
        node.setStartTag(startTag);
        node.setEndTag(endTag);
        node.setStartPosition(startPosition);
        node.setEndPosition(endPosition);

        return node;
    }

    public static Node nodeBuilder(NodeType nodeType){



        return null;
    }

    public static NodeType checkNodeType(String s) {

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
            return NodeType.INFORMATION_NODE;
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
