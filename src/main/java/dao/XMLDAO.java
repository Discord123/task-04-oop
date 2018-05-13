package dao;

import xml.*;

import java.io.IOException;
import java.net.URL;

public interface XMLDAO {
    NodeList getChildList(Node node, URL filePath) throws IOException;
    Document firstNodeCreator(URL filePath) throws IOException;
}
