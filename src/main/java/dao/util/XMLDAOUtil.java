package dao.util;

import xml.NodeType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDAOUtil {
    private static final Pattern PATTERN_INF = Pattern.compile("^<\\?.*\\?>$");
    private static final Pattern PATTERN_EMPTY = Pattern.compile("\\s+<.+/>");
    private static final Pattern PATTERN_START = Pattern.compile("\\s*<.+>\\s*");
    private static final Pattern PATTERN_FILL = Pattern.compile("\\s*<.+>.*</.+>");

    public static NodeType checkNodeType(String s) {

        Matcher matcherInf = PATTERN_INF.matcher(s);
        Matcher matcherEmpty = PATTERN_EMPTY.matcher(s);
        Matcher matcherStart = PATTERN_START.matcher(s);
        Matcher matcherFill = PATTERN_FILL.matcher(s);

        if (matcherInf.lookingAt()) {
            return NodeType.INFORMATION_NODE;
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

        return NodeType.START_NODE;
    }
}