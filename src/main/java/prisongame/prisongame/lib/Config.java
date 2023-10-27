package prisongame.prisongame.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;
import prisongame.prisongame.Prison;
import prisongame.prisongame.PrisonGame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private static final PrisonGame INSTANCE = PrisonGame.getPlugin(PrisonGame.class);
    private static final File DATA_FOLDER = INSTANCE.getDataFolder();
    private static final File CONFIG = new File(DATA_FOLDER.getAbsolutePath() + "/config.toml");
    private static final File PRISONS = new File(DATA_FOLDER.getAbsolutePath() + "/prisons.toml");

    public static Prison defaultPrison;
    public static final Map<String, Prison> prisons = new HashMap<>();

    public static class General {
        public static String discordInvite;
        public static final List<String> rules = new ArrayList<>();
    }

    static {
        reload();
    }

    public static void reload() {
        final var world = Bukkit.getWorld("world");
        final var placeholderLocation = new Location(world, 0D, 0D, 0D, 0F, 0F);

        try {
            var config = parseFile(CONFIG);
            var prisons = parseFile(PRISONS);

            Config.prisons.clear();
            General.rules.clear();

            for (var entry : prisons.getTable("prisons").entrySet()) {
                var key = entry.getKey();
                var prison = (TomlTable) entry.getValue();

                var id = prison.getString("selector-item");
                var material = id.isEmpty() ? Material.AIR : Material.matchMaterial(id);

                Config.prisons.put(key, new Prison(
                        prison.getBoolean("show-in-selector"),
                        material,
                        prison.getString("display-name"),
                        prison.getLong("priority").intValue(),
                        prison.getString("name"),
                        placeholderLocation,
                        parseLocation(prisons, key, "track-1"),
                        parseLocation(prisons, key, "track-2"),
                        parseLocation(prisons, key, "nurse-1"),
                        parseLocation(prisons, key, "nurse-2"),
                        parseLocation(prisons, key, "warden"),
                        parseLocation(prisons, key, "prisoner"),
                        parseLocation(prisons, key, "bm-in"),
                        parseLocation(prisons, key, "bm-out"),
                        parseLocation(prisons, key, "solitary"),
                        parseLocation(prisons, key, "bertrude"),
                        placeholderLocation,
                        placeholderLocation
                ));
            }

            defaultPrison = Config.prisons.get(prisons.getString("default-prison"));

            General.discordInvite = config.getString("general.discord-invite");

            var rules = config.getArray("general.rules");

            for (int i = 0; i < rules.size(); i++)
                General.rules.add(rules.getString(i));
        } catch (IOException exception) {
            INSTANCE.getLogger().severe("An error occurred parsing the config.");
            INSTANCE.getLogger().severe(exception.getMessage());
            Bukkit.shutdown();
        }
    }

    private static Location parseLocation(TomlParseResult result, String prison, String key) {
        var location = result.getTable("prisons." + prison).getTable(key);

        if (location == null)
            throw new IllegalArgumentException("Invalid prison " + prison + ", missing " + key);

        var requiredKeys = new ArrayList<String>();
        requiredKeys.add("world");
        requiredKeys.add("x");
        requiredKeys.add("y");
        requiredKeys.add("z");

        if (!location.keySet().containsAll(requiredKeys))
            throw new IllegalArgumentException("Invalid keys for prison " + prison + " and key " + key);

        Double yaw = location.getDouble("yaw");
        Double pitch = location.getDouble("pitch");

        if (yaw == null)
            yaw = 0.0;

        if (pitch == null)
            pitch = 0.0;

        return new Location(
                Bukkit.getWorld(location.getString("world")),
                location.getDouble("x"),
                location.getDouble("y"),
                location.getDouble("z"),
                yaw.floatValue(),
                pitch.floatValue()
        );
    }

    private static TomlParseResult parseFile(File file) throws IOException {
        if (!file.exists()) {
            DATA_FOLDER.mkdir();
            INSTANCE.saveResource(file.getName(), true);
        }

        return Toml.parse(file.toPath());
    }

    // Workaround because clinit doesn't run when accessing children classes
    public static void register() {}
}
