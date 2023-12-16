package prisongame.prisongame.lib;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.FilteredWords;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.discord.listeners.Messages;
import prisongame.prisongame.keys.Keys;

public class ChatFormat implements ChatRenderer {
    private boolean stopDuplicates = false;

    @Override
    public @NotNull Component render(
            @NotNull Player source,
            @NotNull Component sourceDisplayName,
            @NotNull Component message,
            @NotNull Audience viewer
    ) {
        var plainMessage = PlainTextComponentSerializer.plainText().serialize(message);
        var role = PrisonGame.roles.get(source);
        var isWarden = role == Role.WARDEN;

        for (var player : Bukkit.getOnlinePlayers()) {
            var name = player.getName();

            if (plainMessage.toLowerCase().contains(name.toLowerCase()))
                player.playSound(Sound.sound()
                        .type(Key.key("block.note_block.pling"))
                        .pitch(2f)
                        .build());

            message = message.replaceText((builder) -> builder
                    .match("(?i)" + name)
                    .replacement(Component
                            .text("@" + name)
                            .color(NamedTextColor.GREEN)));
        }

        if (PrisonGame.FEMBOYS && role == Role.NURSE)
            message = sprinkleFemboys(getLegacy(message, true));

        if (PrisonGame.grammar && role != Role.PRISONER && !isWarden)
            message = sprinkleGrammar(getLegacy(message, true));

        if (insertSpaces(isWarden, viewer))
            viewer.playSound(Sound.sound()
                    .type(Key.key("block.note_block.bit"))
                    .build());

        Bukkit.getLogger().info(String.format(
                "%s: %s",
                LegacyComponentSerializer.legacySection().serialize(sourceDisplayName),
                getLegacy(message, false)
        ));

        var delimiter = Component
                .text(": ")
                .color(isWarden ? NamedTextColor.RED : NamedTextColor.GRAY);

        var filter = FilteredWords.isClean(plainMessage);
        var filtered = Component.text("I FUCKING LOVE AMONG US!!! YESS!!! AMONGER!! SUSS!!! SUSSY!!! SUSSY BAKA!! SUSS!! WALTUH!! KINDA SUS WALTUH!!");
        var cleanMessage = filter == null ? message : filtered;
        var render = sourceDisplayName
                .append(delimiter
                        .append(cleanMessage));

        if (!stopDuplicates) {
            stopDuplicates = true;
            return render;
        }

        if (filter != null) {
            if (!Config.dev)
                Messages.INSTANCE.onChat(source, getLegacy(cleanMessage, false));
            FilteredWords.alert(source, getLegacy(message, false), filter, "chat");
            stopDuplicates = false;
        }

        return render;
    }

    private void cancel(AsyncChatEvent event) {
        event.renderer();
    }

    private String getLegacy(Component message, boolean section) {
        var serializer = section
                ? LegacyComponentSerializer.legacySection()
                : LegacyComponentSerializer.legacyAmpersand();

        return serializer.serialize(message);
    }

    private Component sprinkleFemboys(String message) {
        return LegacyComponentSerializer
                .legacySection()
                .deserialize(UwUtils.uwuify(message));
    }

    private Component sprinkleGrammar(String message) {
        message = message.substring(0, 1).toUpperCase() + message.substring(1);
        message = message.replace(" i ", " I ");

        if (!message.matches(".*[.!?;:]$"))
            message += ".";

        return LegacyComponentSerializer.legacySection().deserialize(message);
    }

    private boolean insertSpaces(boolean isWarden, Audience viewer) {
        var uuid = viewer.get(Identity.UUID);

        if (uuid.isEmpty())
            return false;

        var player = Bukkit.getPlayer(uuid.get());

        return isWarden && player != null && !Keys.NO_WARDEN_SPACES.has(player);
    }
}
