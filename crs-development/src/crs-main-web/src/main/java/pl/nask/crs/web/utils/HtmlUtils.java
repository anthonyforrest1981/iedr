package pl.nask.crs.web.utils;

import java.util.*;

import org.apache.commons.lang.StringUtils;

public class HtmlUtils {

    private static final Map<String, String> EMPTY_ATTRIBUTES = new HashMap<>();

    public static String displayListAsTable(Collection<String> list, int columns, String cssClass) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        Map<String, String> attributes = new HashMap<>();
        if (cssClass != null) {
            attributes.put("class", cssClass);
        }
        List<String> rows = new ArrayList<>();
        List<String> cells = new ArrayList<>();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            cells.add(generateCell(it.next(), EMPTY_ATTRIBUTES));
            if (cells.size() == columns || !it.hasNext()) {
                rows.add(generateRow(cells, EMPTY_ATTRIBUTES));
                cells.clear();
            }
        }
        return generateTable(rows, attributes);
    }

    private static String wrapInTag(String tag, String value, Map<String, String> attributes) {
        String attributesString = attributesToString(attributes);
        return String.format("<%s%s>%s</%s>", tag, attributesString, value, tag);
    }

    private static String attributesToString(Map<String, String> attributes) {
        String result = "";
        for (Map.Entry<String, String> attributeEntry : attributes.entrySet()) {
            result += String.format(" %s=\"%s\"", attributeEntry.getKey(), attributeEntry.getValue());
        }
        return result;
    }

    private static String generateCell(String value, Map<String, String> attributes) {
        return wrapInTag("td", value, attributes);
    }

    private static String generateRow(List<String> cells, Map<String, String> attributes) {
        return wrapInTag("tr", StringUtils.join(cells, ""), attributes);
    }

    private static String generateTable(List<String> rows, Map<String, String> attributes) {
        return wrapInTag("table", StringUtils.join(rows, ""), attributes);
    }

}
