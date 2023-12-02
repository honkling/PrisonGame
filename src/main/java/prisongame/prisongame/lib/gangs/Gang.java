package prisongame.prisongame.lib.gangs;

import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public class Gang {
    public String name;
    public UUID owner;
    public String ownerName;
    public List<UUID> members;
    public List<UUID> officials;
    public double bank;

    public Gang(
            String name,
            UUID owner,
            String ownerName,
            List<UUID> members,
            List<UUID> officials,
            double bank
    ) {
        this.name = name;
        this.owner = owner;
        this.ownerName = ownerName;
        this.members = members;
        this.officials = officials;
        this.bank = bank;
    }
}
