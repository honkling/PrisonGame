package prisongame.prisongame;

public class FilteredWords {
    static String[] filter = {
            "nigger",
            "niger",
            "nigga",
            "faggot",
            "fagot",
            "retard",
            "cunt",
            "ThisIsBlockedByTheChatFilter"
    };

    static String filtermsg(String msg) {
        for (String i : filter) {
            msg = msg.replaceAll(i, "I LOVE AMONG USS!!!!!!!!!");
        }
        return msg;
    }
}
