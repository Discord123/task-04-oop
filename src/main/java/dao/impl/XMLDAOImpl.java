package dao.impl;

import dao.XMLDAO;
import dao.util.XMLDAOUtil;
import xml.Document;
import xml.Node;
import xml.NodeList;
import xml.NodeType;

import java.io.IOException;
import java.io.RandomAccessFile;

public class XMLDAOImpl implements XMLDAO {

    @Override
    public NodeList getChildList(Node node, RandomAccessFile randomAccessFile) throws IOException {
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

    @Override
    public Document firstNodeCreator(RandomAccessFile randomAccessFile) throws IOException {

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

        return new Document(information, node);
    }

    private Node recursiveNodeCreate(long nodeStartPosition, long nodeEndPosition,
                                    RandomAccessFile randomAccessFile) throws IOException {
        if (nodeStartPosition >= nodeEndPosition) {
            return null;
        }

        Node childNode = new Node();
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

        nodeType = XMLDAOUtil.checkNodeType(startTag);

        if (nodeType == NodeType.START_NODE) {
            textContentBuilder.append(startTag.substring(startTag.indexOf(">")+1).trim());
            startTag = startTag.substring(0,startTag.indexOf(">")+1);
        }

        startTag = startTag.substring(startTag.indexOf("<"));
        attribute = startTag.replace(name, "").replace("<", "").replace(">", "");
        endTag = startTag.replace("<", "</").split(" ")[0];
        name = endTag.split(">")[0].replace("</", "");
        if (!endTag.contains(">")) {
            endTag = endTag + ">";
        }
        endTag = endTag.substring(0,endTag.indexOf(">")+1);

        if (nodeType == NodeType.START_NODE) {
            String s;
            while ((s = randomAccessFile.readLine()) != null) {
                if (randomAccessFile.getFilePointer() < nodeEndPosition) {
                    if (XMLDAOUtil.checkNodeType(s) == NodeType.TEXT_NODE &&
                            XMLDAOUtil.checkNodeType(nextTagLine) == NodeType.TEXT_NODE) {
                        textContentBuilder.append(s.trim());
                    }
                    if (s.replaceAll(" ", "").contains(endTag)) {
                        textContentBuilder.append(" " + (s.replace(endTag,"").trim()));
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
            attribute = "";
        }

        startTag = startTag.substring(0, startTag.indexOf(">")+1);
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
