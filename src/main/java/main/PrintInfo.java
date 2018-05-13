package main;

import xml.Document;
import xml.Node;
import xml.NodeList;

public class PrintInfo {

    public static void printInfo(Node node){
        System.out.println(node);
        System.out.println("----------");
    }

    public static void printInfo(Document document) {
        System.out.println(document);
        System.out.println("----------");
    }

    public static void printInfo(NodeList nodeList) {
        for (int i = 0; i < nodeList.size(); i++) {
            System.out.println(nodeList.item(i));
        }
        System.out.println("----------");
    }
}
