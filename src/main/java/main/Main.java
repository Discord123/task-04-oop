package main;

import xml.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        String breakfastMenu = "breakfastMenu.xml";
        String nodes = "nodes.xml";
        String books = "books.xml";

        DocumentBuilder documentBuilder = DocumentBuilder.getInstance();

        ////////////////////////////////////////////////////////////////////

        Document breakFastMenuDocument = documentBuilder.getDocument(breakfastMenu);
        PrintInfo.printInfo(breakFastMenuDocument);

        NodeList breakFastMenuList = breakFastMenuDocument.getChildNodes();
        PrintInfo.printInfo(breakFastMenuList);

        for (int i = 0; i < breakFastMenuList.size(); i++) {
            Node childNode1 = breakFastMenuList.item(i);
            if (childNode1.getNodeType() != NodeType.TEXT_NODE) {
               NodeList nodeList1 = childNode1.getChildList();
               PrintInfo.printInfo(nodeList1);
            }
        }

        ////////////////////////////////////////////////////////////////////

        Document nodesDocument = documentBuilder.getDocument(nodes);
        PrintInfo.printInfo(nodesDocument);

        NodeList nodesList = nodesDocument.getChildNodes();
        PrintInfo.printInfo(nodesList);

        for (int i = 0; i < nodesList.size(); i++) {
            Node childNode2 = nodesList.item(i);
            if (childNode2.getNodeType() != NodeType.TEXT_NODE) {
                NodeList nodeList2 = childNode2.getChildList();
                PrintInfo.printInfo(nodeList2);
            }
        }

        ////////////////////////////////////////////////////////////////////

        Document booksDocument = documentBuilder.getDocument(books);
        PrintInfo.printInfo(booksDocument);

        NodeList booksList = booksDocument.getChildNodes();
        PrintInfo.printInfo(booksList);

        for (int i = 0; i < booksList.size(); i++) {
            Node childNode3 = booksList.item(i);
            if (childNode3.getNodeType() != NodeType.TEXT_NODE) {
                NodeList nodeList3 = childNode3.getChildList();
                PrintInfo.printInfo(nodeList3);
            }
        }
    }
}
