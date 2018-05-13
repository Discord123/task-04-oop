package dao;

import xml.*;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface XMLDAO {
    NodeList getChildList(Node node, RandomAccessFile randomAccessFile) throws IOException;
    Document firstNodeCreator(RandomAccessFile randomAccessFile) throws IOException;
}
