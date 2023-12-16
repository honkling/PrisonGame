package prisongame.prisongame;

import net.dv8tion.jda.api.EmbedBuilder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import prisongame.prisongame.discord.DiscordKt;

import java.awt.*;
import java.time.Instant;
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
            "fagot",
            "realybadword" // used for testing filter
            //"fag", this one's disabled as it disables things such as "of agmass"
    };

    private static String replaceConsecutiveDuplicates(String msg) {
        final Pattern pattern = Pattern.compile("(.)\\1*", Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(msg);

        StringBuilder sanitized = new StringBuilder();

        while (matcher.find())
            sanitized.append(matcher.group(1));

        return sanitized.toString();
    }

    public static String filtermsg(Player player, String msg) {
        String sanitized = replaceConsecutiveDuplicates(msg
                .replaceAll("\\s+", ""));

        for (String i : filter) {
            if (sanitized.toLowerCase().contains(i)) {
                alert(player, msg, i, "team chat");
                return "I FUCKING LOVE AMONG US!!! YESS!!! AMONGER!! SUSS!!! SUSSY!!! SUSSY BAKA!! SUSS!! WALTUH!! KINDA SUS WALTUH!!";
            }
        }

        return msg;
    }

    public static void alert(Player violator, String message, String word, String context) {
        for (var player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("pbb.staff"))
                continue;

            sendAlert(Audience.audience(player), violator, message, word, context);
        }

        sendAlert(Audience.audience(Bukkit.getConsoleSender()), violator, message, word, context);

        var now = Instant.now();
        var embed = new EmbedBuilder();
        embed.addField("Time", String.format(
                "<t:%s> (<t:%s:R>)",
                now.getEpochSecond(),
                now.getEpochSecond()
        ), true);
        embed.setDescription(String.format(
                "**%s** was filtered due to using the word **%s** (context: **%s**). Their message is below.\n**%s**",
                violator.getName(),
                word,
                context,
                message
        ));
        embed.setColor(new Color(0xFF5555));
        DiscordKt.filter.sendMessageEmbeds(embed.build()).queue();
    }

    private static void sendAlert(Audience audience, Player violator, String message, String word, String context) {
        audience.sendMessage(PrisonGame.mm.deserialize(
                "<gray><player> was filtered due to using the word '<word>' (context: <context>). Their message is below.\n<message>",
                Placeholder.component("player", violator.name().color(NamedTextColor.WHITE)),
                Placeholder.component("word", Component
                        .text(word)
                        .color(NamedTextColor.WHITE)),
                Placeholder.component("context", Component
                        .text(context)
                        .color(NamedTextColor.WHITE)),
                Placeholder.component("message", Component
                        .text(message)
                        .color(NamedTextColor.WHITE))
        ));
    }

    public static @Nullable String isClean(String msg) {
        String sanitized = replaceConsecutiveDuplicates(msg
                .replaceAll("\\s+", ""));

        for (String i : filter) {
            if (sanitized.toLowerCase().contains(i))
                return i;
        }

        return null;
    }
}
