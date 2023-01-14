package prisongame.prisongame;

public class FilteredWords {
    static String[] filter = {
            "nigger",
            "niger",
            "nigga",
            "faggot",
            "fagot",
            "retard"
    };

    public static String filtermsg(String msg) {
        for (String i : filter) {
            if (msg.toLowerCase().contains(i)) {
                msg = "I FUCKING LOVE AMONG US!!! YESS!!! AMONGER!! SUSS!!! SUSSY!!! SUSSY BAKA!! SUSS!! WALTUH!! KINDA SUS WALTUH!!";
            }
        }
        return msg;
    }


}
