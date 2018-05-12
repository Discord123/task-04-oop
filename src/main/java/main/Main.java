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

        String breakfastMenu = "breakfastMenu.xml";
        String nodes = "nodes.xml";
        String books = "books.xml";
        URL filePath = XMLDaoFactory.class.getClassLoader().getResource(breakfastMenu);

        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath.getFile(), "r");

        Node document = firstNodeCreator(randomAccessFile);

        NodeList nodeList = getChildList(document, randomAccessFile);
        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.item(i);
            System.out.println(node);
            System.out.println("=======");
            if (node.getNodeType() != NodeType.TEXT_NODE || node.getNodeType() != NodeType.FILL_NODE) {
                NodeList nodeList1 = getChildList(node, randomAccessFile);
                for (int j = 0; j < nodeList1.size(); j++) {
                    Node node1 = nodeList1.item(j);
                    System.out.println(node1);
                }
                System.out.println("---------------------------------");
            }
        }

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
            if (childNode.getNextLinePosition() == nodeEndPosition) {
                break;
            }
        }

        return nodeList;
    }

    public static Node recursiveNodeCreate(long nodeStartPosition, long nodeEndPosition,
                                           RandomAccessFile randomAccessFile) throws IOException {

        if (nodeStartPosition >= nodeEndPosition) {
            return null;
        }

        String name = "";
        String startTag = "";
        String endTag = "";
        String attribute = "";
        NodeType nodeType;
        String textContent = "";
        long startPosition = 0;
        long endPosition = 0;
        long nextLinePosition;
        String nextTagLine = "";

        randomAccessFile.seek(nodeStartPosition);

        startTag = randomAccessFile.readLine();
        startPosition = randomAccessFile.getFilePointer();
        nextTagLine = randomAccessFile.readLine();

        randomAccessFile.seek(startPosition);

        StringBuilder textContentBuilder = new StringBuilder("");

        if (checkNodeType(startTag) == NodeType.START_NODE) {
            textContentBuilder.append(startTag.substring(startTag.indexOf(">")+1));
            startTag = startTag.substring(0,startTag.indexOf(">")+1);
        }

        nodeType = checkNodeType(startTag);

        startTag = startTag.substring(startTag.indexOf("<"));

        endTag = startTag.replace("<", "</").split(" ")[0];
        if (!endTag.contains(">")) {
            endTag = endTag + ">";
        }

        if (nodeType == NodeType.START_NODE) {
            String s;
            while ((s = randomAccessFile.readLine()) != null) {
                if (randomAccessFile.getFilePointer() < nodeEndPosition) {
                    if (checkNodeType(s) == NodeType.TEXT_NODE && checkNodeType(nextTagLine) == NodeType.TEXT_NODE) {
                        textContentBuilder.append(s);
                    }
                    if (s.replaceAll(" ", "").contains(endTag)) {
                        textContentBuilder.append(s.replace(endTag,""));
                        endPosition = randomAccessFile.getFilePointer();
                        break;
                    }
                }
            }
        }

        textContent = textContentBuilder.toString();

        if (nodeType == NodeType.FILL_NODE) {
            endPosition = startPosition;
            textContent = startTag.substring(startTag.indexOf(">") + 1).split("</")[0];
        }

        name = endTag.replace("</", "").replace(">", "");
        attribute = startTag.replace(name, "").replace("<", "").replace(">", "");
        randomAccessFile.readLine();
        nextLinePosition = randomAccessFile.getFilePointer();

        Node childNode = new Node();
        childNode.setName(name);
        childNode.setStartTag(startTag);
        childNode.setEndTag(endTag);
        childNode.setAttribute(attribute);
        childNode.setNodeType(nodeType);
        childNode.setTextContent(textContent);
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

        if (checkNodeType(firstLine) == NodeType.INFORMATION_NODE) {
            firstLine = randomAccessFile.readLine();
        }

        startPosition = randomAccessFile.getFilePointer();
        name = firstLine.replaceAll("<", "").replaceAll(">", "");
        startTag = firstLine;

        String closeTagName = firstLine.replaceAll("<", "</");

        String s;
        while ((s = randomAccessFile.readLine()) != null) {
            if (s.replaceAll(" ", "").equals(closeTagName.replaceAll(" ", ""))) {
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

    public static Node nodeBuilder(NodeType nodeType) {


        return null;
    }

    public static NodeType checkNodeType(String s) {

        Pattern patternInf = Pattern.compile("^<\\?.*\\?>$");
        Matcher matcherInf = patternInf.matcher(s);

        Pattern patternEnd = Pattern.compile("\\s*</.+>");
        Matcher matcherEnd = patternEnd.matcher(s);

        Pattern patternEmpty = Pattern.compile("\\s+<.+/>");
        Matcher matcherEmpty = patternEmpty.matcher(s);

        Pattern patternStart = Pattern.compile("\\s*<.+>\\s*");
        Matcher matcherStart = patternStart.matcher(s);

        Pattern patternFill = Pattern.compile("\\s*<.+>.*</.+>");
        Matcher matcherFill = patternFill.matcher(s);

        if (matcherInf.lookingAt()) {
            return NodeType.INFORMATION_NODE;
        }
        if (matcherEnd.lookingAt()) {
            return NodeType.END_NODE;
        }
        if (matcherEmpty.lookingAt()) {
            return NodeType.EMPTY_NODE;
        }
        if (matcherFill.lookingAt()) {
            return NodeType.FILL_NODE;
        }
        if (!s.contains("<") && !s.contains(">")) {
            return NodeType.TEXT_NODE;
        }
        if (matcherStart.lookingAt()) {
            return NodeType.START_NODE;
        }

        return null;
    }
}
