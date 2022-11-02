package prisongame.prisongame;


import org.bukkit.Bukkit;
import org.bukkit.Location;

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

    public Prison(String nm, Location rp1, Location rp2, Location nb, Location nbot, Location ws, Location cls, Location bme, Location bmoute, Location sol, Location br) {
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
        Bukkit.getLogger().log(Level.INFO, "activated prison " + nm);
    }

}
