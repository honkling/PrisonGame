package prisongame.prisongame.lib.gangs;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import prisongame.prisongame.lib.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Gangs {
    public static Gang get(String name) throws SQLException {
        var rs = SQL.query("""
                SELECT * FROM gangs WHERE (lower(name)) = ?;
                """, name.toLowerCase());

        if (!rs.next())
            return null;

        return getGangFromResultSet(rs);
    }

    public static Gang get(OfflinePlayer player) throws SQLException {
        var rs = SQL.query("""
                SELECT * FROM gangs WHERE members LIKE '%?%';
                """, player.getUniqueId());

        if (!rs.next())
            return null;

        return getGangFromResultSet(rs);
    }

    public static boolean exists(String name) throws SQLException {
        return get(name) != null;
    }

    public static Gang create(Player owner, String name) throws SQLException {
        var gang = new Gang(name, owner, new ArrayList<>(), 0.0);
        replace(gang);
        return gang;
    }

    public static void replace(Gang gang) throws SQLException {
        var uuids = gang.members.stream().map((p) -> p.getUniqueId().toString()).toArray(String[]::new);
        var members = String.join(" ", uuids);

        SQL.execute("""
                INSERT OR REPLACE INTO gangs(name, owner, members, bank)
                VALUES (?, ?, ?, ?);
                """,
                gang.name,
                gang.owner.getUniqueId().toString(),
                members,
                gang.bank);
    }

    private static Gang getGangFromResultSet(ResultSet rs) throws SQLException {
        var members = Arrays
                .stream(rs.getString("members").split(","))
                .map(Bukkit::getOfflinePlayer)
                .toList();

        return new Gang(
                rs.getString("name"),
                Bukkit.getOfflinePlayer(rs.getString("owner")),
                members,
                rs.getDouble("bank")
        );
    }
}
