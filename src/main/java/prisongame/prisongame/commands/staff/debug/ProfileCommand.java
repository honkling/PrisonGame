package prisongame.prisongame.commands.staff.debug;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import prisongame.prisongame.Prison;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Profile;
import prisongame.prisongame.lib.ProfileKt;
import prisongame.prisongame.lib.Role;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ProfileCommand implements CommandExecutor {
    public record Property(String name, Method getter, @Nullable Method setter) {}

    private enum Action {
        GET,
        SET
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var player = getPlayer(args);
        var action = getAction(args);
        var property = getProperty(args);

        if (player == null) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>Please provide a player."));
            return true;
        }

        if (action == null) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>Please provide an action. (get/set)"));
            return true;
        }

        if (property == null) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>Please provide a property."));
            return true;
        }

        var profile = ProfileKt.getProfile(player);

        try {
            switch (action) {
                case GET -> {
                    var value = property.getter.invoke(profile);
                    sender.sendMessage(PrisonGame.mm.deserialize(
                            "<gray>Property <property> has value <value>.",
                            Placeholder.component("property", Component
                                    .text(property.name)
                                    .color(NamedTextColor.WHITE)),
                            Placeholder.component("value", Component
                                    .text(value.toString())
                                    .color(NamedTextColor.WHITE))
                    ));
                }
                case SET -> {
                    if (property.setter == null) {
                        sender.sendMessage(PrisonGame.mm.deserialize("<red>This property doesn't have a setter!"));
                        return true;
                    }

                    var value = getValue(property.setter, args);

                    if (value == null) {
                        sender.sendMessage(PrisonGame.mm.deserialize("<red>Please provide a value."));
                        return true;
                    }

                    property.setter.invoke(profile, value);
                    sender.sendMessage(PrisonGame.mm.deserialize(
                            "<gray>Set value of property <property> to <value>.",
                            Placeholder.component("property", Component
                                    .text(property.name)
                                    .color(NamedTextColor.WHITE)),
                            Placeholder.component("value", Component
                                    .text(value.toString())
                                    .color(NamedTextColor.WHITE))
                    ));
                }
            }
        } catch (ReflectiveOperationException exception) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>An error occurred."));
            exception.printStackTrace();
        }

        return true;
    }

    private @Nullable Object getValue(Method setter, String[] args) {
        if (args.length < 4)
            return null;

        try {
            System.out.println(setter.getParameterTypes()[0].getSimpleName());
            return switch (setter.getParameterTypes()[0].getSimpleName()) {
                case "Integer" -> Integer.parseInt(args[3]);
                case "Double" -> Double.parseDouble(args[3]);
                case "Boolean" -> Boolean.parseBoolean(args[3]);
                case "String" -> String.join(" ", Arrays.stream(args).toList().subList(3, args.length).toArray(String[]::new));
                case "Role" -> Role.valueOf(args[3].toUpperCase());
                case "Player" -> Bukkit.getPlayer(args[3]);
                case "OfflinePlayer" -> Bukkit.getOfflinePlayer(args[3]);
                default -> null;
            };
        } catch (Exception exception) {
            PrisonGame.instance.getLogger().info(exception.getMessage());
            return null;
        }
    }

    private @Nullable Property getProperty(String[] args) {
        if (args.length < 3)
            return null;

        var properties = getProperties();

        for (var property : properties) {
            if (property.name.equalsIgnoreCase(args[2]))
                return property;
        }

        return null;
    }

    private @Nullable Action getAction(String[] args) {
        if (args.length < 2)
            return null;

        try {
            return Action.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private @Nullable Player getPlayer(String[] args) {
        if (args.length < 1)
            return null;

        return Bukkit.getPlayer(args[0]);
    }

    public List<Property> getProperties() {
        var getters = Arrays
                .stream(Profile.class.getMethods())
                .filter((m) -> m.getName().startsWith("get"))
                .toArray(Method[]::new);

        var setters = Arrays
                .stream(getters)
                .map((m) -> {
                    try {
                        var name = m.getName().replace("get", "set");
                        return Profile.class.getMethod(name, m.getReturnType());
                    } catch (NoSuchMethodException exception) {
                        return null;
                    }
                })
                .toArray(Method[]::new);

        var properties = new ArrayList<Property>();

        for (int i = 0; i < getters.length; i++) {
            var getter = getters[i];
            var setter = setters[i];
            var name = pascalToUpperSnake(getter.getName()
                    .replace("get", ""));

            properties.add(new Property(name, getter, setter));
        }

        return properties;
    }

    private String pascalToUpperSnake(String pascal) {
        var regex = "(^[a-z]+|[A-Z][a-z]+)";
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(pascal);
        var builder = new StringBuilder();

        while (matcher.find()) {
            var match = matcher.group(0);

            if (!builder.isEmpty())
                builder.append("_");

            builder.append(match.toUpperCase());
        }

        return builder.toString();
    }
}
