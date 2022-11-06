package prisongame.prisongame;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.logging.Level;

public class Prison {
    public Location runpoint1;
    public Location runpoint2;

    public Location nursebed;
    public Location nursebedOutTP;
    public Location wardenspawn;
    public Location spwn;
    public Location bm;
    public Location bmout;
    public Location solit;
    public Location bert;
    public String name;
    public Location cafedoor1;
    public Location cafedoor2;


    public Prison(String nm, Location rp1, Location rp2, Location nb, Location nbot, Location ws, Location cls, Location bme, Location bmoute, Location sol, Location br, Location cfd1, Location cfd2) {
        name = nm;
        runpoint1 = rp1;
        runpoint2 = rp2;
        nursebed = nb;
        nursebedOutTP = nbot;
        solit = sol;
        bert = br;
        wardenspawn = ws;
        spwn = cls;
        bm = bme;
        bmout = bmoute;
        cafedoor1 = cfd1;
        cafedoor2 = cfd2;
        Bukkit.getLogger().log(Level.INFO, "activated prison " + nm + " world: " + cls.getWorld());
        for (Integer x = 1; x <= 3; x++) {
            Bukkit.getWorld("world").getBlockAt(x, -58, -1008).setType(Material.MUD_BRICKS);
        }
        for (Integer x = this.getCafedoor1().getBlockX(); x <= this.getCafedoor2().getBlockX(); x++) {
            for (Integer y = this.getCafedoor1().getBlockY(); y <= this.getCafedoor2().getBlockY(); y++) {
                for (Integer z = this.getCafedoor1().getBlockZ(); z <= this.getCafedoor2().getBlockZ(); z++) {
                    Bukkit.getWorld("world").getBlockAt(x, y, z).setType(Material.MUD_BRICKS);
                }
            }
        }
    }

    public Location getCafedoor1() {
        return cafedoor1;
    }

    public Location getCafedoor2() {
        return cafedoor2;
    }


    public Location getBm() {
        return bm;
    }

    public Location getBmout() {
        return bmout;
    }

    public Location getBert() {
        return bert;
    }

    public Location getNursebed() {
        return nursebed;
    }

    public Location getNursebedOutTP() {
        return nursebedOutTP;
    }

    public Location getRunpoint1() {
        return runpoint1;
    }

    public Location getRunpoint2() {
        return runpoint2;
    }

    public Location getSolit() {
        return solit;
    }

    public Location getSpwn() {
        return spwn;
    }

    public Location getWardenspawn() {
        return wardenspawn;

    }
}
