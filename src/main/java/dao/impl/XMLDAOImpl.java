package dao.impl;

import dao.XMLDAO;
import dao.util.XMLDAOUtil;
import xml.Document;
import xml.Node;
import xml.NodeList;
import xml.NodeType;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

public class XMLDAOImpl implements XMLDAO {

    private static final String LEFT_BRACKET =  "<";
    private static final String RIGHT_BRACKET =  ">";
    private static final String EMPTY_PLACE = "";
    private static final String INTERVAL = " ";
    private static final String LEFT_BRACKET_WITH_SLASH = "</";

    private RandomAccessFile randomAccessFile;

    @Override
    public NodeList getChildList(Node node, URL filePath) throws IOException {

        randomAccessFile = new RandomAccessFile(filePath.getFile(),"r");
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
        randomAccessFile.close();

        return nodeList;
    }

    @Override
    public Document firstNodeCreator(URL filePath) throws IOException {

        randomAccessFile = new RandomAccessFile(filePath.getFile(),"r");
        Node node = new Node();
        String name;
        String startTag;
        String endTag = "";
        String information = "";
        long startPosition;
        long endPosition = 0;

        String firstLine = randomAccessFile.readLine();

        if (XMLDAOUtil.checkNodeType(firstLine) == NodeType.INFORMATION_NODE) {
            information = firstLine;
            firstLine = randomAccessFile.readLine();
        }

        startPosition = randomAccessFile.getFilePointer();
        name = firstLine.replaceAll(LEFT_BRACKET, EMPTY_PLACE).replaceAll(RIGHT_BRACKET, EMPTY_PLACE);
        startTag = firstLine;

        String closeTagName = firstLine.replaceAll(LEFT_BRACKET, LEFT_BRACKET_WITH_SLASH);

        String s;
        while ((s = randomAccessFile.readLine()) != null) {
            if (s.replaceAll(INTERVAL, EMPTY_PLACE).equals(closeTagName.replaceAll(INTERVAL, EMPTY_PLACE))) {
                endPosition = randomAccessFile.getFilePointer();
                endTag = s;
            }
        }

        node.setName(name);
        node.setStartTag(startTag);
        node.setEndTag(endTag);
        node.setStartPosition(startPosition);
        node.setEndPosition(endPosition);

        return new Document(information, node);
    }

    private Node recursiveNodeCreate(long nodeStartPosition, long nodeEndPosition,
                                    RandomAccessFile randomAccessFile) throws IOException {
        if (nodeStartPosition >= nodeEndPosition) {
            return null;
        }

        Node childNode = new Node();
        String name;
        String startTag;
        String endTag;
        String attribute;
        NodeType nodeType;
        String textContent;
        long startPosition;
        long endPosition = 0;
        long nextLinePosition;
        String nextTagLine;

        randomAccessFile.seek(nodeStartPosition);

        startTag = randomAccessFile.readLine();
        startPosition = randomAccessFile.getFilePointer();
        nextTagLine = randomAccessFile.readLine();

        randomAccessFile.seek(startPosition);

        StringBuilder textContentBuilder = new StringBuilder(EMPTY_PLACE);

        nodeType = XMLDAOUtil.checkNodeType(startTag);

        if (nodeType == NodeType.START_NODE) {
            textContentBuilder.append(startTag.substring(startTag.indexOf(RIGHT_BRACKET)+1).trim());
            startTag = startTag.substring(0,startTag.indexOf(RIGHT_BRACKET)+1);
        }

        startTag = startTag.substring(startTag.indexOf(LEFT_BRACKET));
        endTag = startTag.replace(LEFT_BRACKET, LEFT_BRACKET_WITH_SLASH).split(EMPTY_PLACE)[0];
        name = endTag.split(RIGHT_BRACKET)[0].replace(LEFT_BRACKET_WITH_SLASH, EMPTY_PLACE);
        attribute = startTag.replace(name, EMPTY_PLACE).replace(LEFT_BRACKET, EMPTY_PLACE).replace(RIGHT_BRACKET, EMPTY_PLACE);


        if (!endTag.contains(RIGHT_BRACKET)) {
            endTag = endTag + RIGHT_BRACKET;
        }
        endTag = endTag.substring(0,endTag.indexOf(RIGHT_BRACKET)+1);

        if (nodeType == NodeType.START_NODE) {
            String s;
            while ((s = randomAccessFile.readLine()) != null) {
                if (randomAccessFile.getFilePointer() < nodeEndPosition) {
                    if (XMLDAOUtil.checkNodeType(s) == NodeType.TEXT_NODE &&
                            XMLDAOUtil.checkNodeType(nextTagLine) == NodeType.TEXT_NODE) {
                        textContentBuilder.append(s.trim());
                    }
                    if (s.replaceAll(INTERVAL, EMPTY_PLACE).contains(endTag)) {
                        textContentBuilder.append(INTERVAL + (s.replace(endTag,EMPTY_PLACE).trim()));
                        endPosition = randomAccessFile.getFilePointer();
                        break;
                    }
                }
            }
        }

        textContent = textContentBuilder.toString();

        if (nodeType == NodeType.FILL_NODE) {
            endPosition = startPosition;
            textContent = startTag.substring(startTag.indexOf(RIGHT_BRACKET) + 1).split(LEFT_BRACKET_WITH_SLASH)[0];
            attribute = EMPTY_PLACE;
        }

        startTag = startTag.substring(0, startTag.indexOf(RIGHT_BRACKET)+1);
        randomAccessFile.readLine();
        nextLinePosition = randomAccessFile.getFilePointer();

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
}
