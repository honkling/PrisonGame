package prisongame.prisongame;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilteredWords {
    static String[] filter = {
            "niga",
            "niger",
            "niba",
            "niber",
            "noga",
            "noger",
            "retard",
            "kys",
            "fag",
            "vagina",
            "pusy",
            "dick",
            "cock",
            "penis",
            "sex"
    };

    private static String replaceConsecutiveDuplicates(String msg) {
        final Pattern pattern = Pattern.compile("(.)\\1*", Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(msg);

        StringBuilder sanitized = new StringBuilder();

        while (matcher.find())
            sanitized.append(matcher.group(1));

        return sanitized.toString();
    }

    public static String filtermsg(String msg) {
        String sanitized = replaceConsecutiveDuplicates(msg
                .replaceAll("\\s+", ""));

        for (String i : filter) {
            if (sanitized.toLowerCase().contains(i))
                return "I FUCKING LOVE AMONG US!!! YESS!!! AMONGER!! SUSS!!! SUSSY!!! SUSSY BAKA!! SUSS!! WALTUH!! KINDA SUS WALTUH!!";
        }

        return msg;
    }


}
