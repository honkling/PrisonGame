package prisongame.prisongame.lib;

import java.util.Random;

public class UwUtils {

    public static String uwuify(String text) {

        text = text.replaceAll("\\.", "~ ");
        text = text.replaceAll(",", "~ ");
        text = text.replaceAll("-", "~ ");
        text = text.replaceAll("\\?", "~ ");
        text = text.replaceAll("hurt", "hUWUrt");
        text = text.replaceAll("kill", "hwuwrt");
        text = text.replaceAll("you", "you<3");
        text = text.replaceAll("r", "w");
        text = text.replaceAll("l", "w");
        text = text.replaceAll("uwu", "UWU");
        text = text.replaceAll("owo", "OWO");
        text = text.replaceAll(";-;", "(-_-)");
        text = text.replaceAll("-_-", "(-_-)");
        text = text.replaceAll(":o", "※(^o^)/※");
        text = text.replaceAll(":0", "※(^o^)/※");
        text = text.replaceAll(":\\)", "(｡◕‿‿◕｡)");
        text = text.replaceAll(":>", "(｡◕‿‿◕｡)");
        text = text.replaceAll(":\\(", "(︶︹︶)");
        text = text.replaceAll(":<", "(︶︹︶)");
        text = text.replaceAll(":3", "(・3・)");
        text = text.replaceAll(":D", "(ﾉ◕ヮ◕)ﾉ*:・ﾟ✧");
        text = text.replaceAll("\\._\\.", "(っ´ω`c)");
        text = text.replaceAll("fuck", "fwick");
        text = text.replaceAll("shit", "*poops*");
        text = text.replaceAll("wtf", "whawt the fwick");
        text = text.replaceAll("wth", "whawt the hecc");


        return text + getRandomUwUSuffix();
    }

    static String getRandomUwUSuffix() {
        String[] uwuSuffixes = new String[]{
                    "~ uwu *nuzzles you*",
                    "~ owo whats this",
                    "~ *kisses you*",
                    "~ *blushes*",
                    "~ *hehe*",
                    "~ meow",
                    "~ owo",
                    "~ uwu",
                    " ;3",
                    "~ *boops your nose*",
                    "~ *snuggles with you*",
                    "~ *giggles*",
                    "~ *hugs you*",
        };

        return uwuSuffixes[new Random().nextInt(0,uwuSuffixes.length-1)];
    }
}
