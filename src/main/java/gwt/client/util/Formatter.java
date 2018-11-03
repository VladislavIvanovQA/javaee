package gwt.client.util;

public class Formatter {

    public static String substutute(String text, Object... params){
        for (int i = 0; i < params.length; i++) {
            Object p = params[i];
            if (p == null){
                p = "";
            }
            text = text.replaceAll("\\{" + i + "}", safeRegexReplacement(p.toString()));
        }
        return text;
    }

    public static String safeRegexReplacement(String replacement){
        if (replacement == null){
            return replacement;
        }
        return replacement.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
    }
}
