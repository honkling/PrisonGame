package prisongame.prisongame;

public class FilteredWords {
    static String[] filter = {
            "nigger",
            "niger",
            "nig",
            "nigga",
            "niga",
            "faggot",
            "fag",
            "fagot"
    };

    static String filtermsg(String msg) {
        for (String i : filter) {
            msg = msg.replace(i, "I LOVE AMONG USS!!!!!!!!!");
        }
        return msg;
    }
}
