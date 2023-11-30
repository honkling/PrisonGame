package prisongame.prisongame.lib.gangs;

import org.bukkit.OfflinePlayer;

import java.util.List;

public class Gang {
    public String name;
    public OfflinePlayer owner;
    public List<OfflinePlayer> members;
    public double bank;

    public Gang(
            String name,
            OfflinePlayer owner,
            List<OfflinePlayer> members,
            double bank
    ) {
        this.name = name;
        this.owner = owner;
        this.members = members;
        this.bank = bank;
    }
}
