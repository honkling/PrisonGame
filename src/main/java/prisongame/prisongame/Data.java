package prisongame.prisongame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import prisongame.prisongame.Prison;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Data implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    public final OfflinePlayer ward;
    public final String map;
    public final Boolean isreload;
    public final Boolean hasSwat;
    public final HashMap<Player, Location> playerLocationHashMap;


    // Can be used for saving
    public Data(OfflinePlayer ward, String map, Boolean isreload, Boolean hasSwat, HashMap<Player, Location> playerLocationHashMap) {
        this.ward = ward;
        this.map = map;
        this.isreload = isreload;
        this.hasSwat = hasSwat;
        this.playerLocationHashMap = playerLocationHashMap;

    }
    // Can be used for loading
    public Data(Data loadedData) {
        this.ward = loadedData.ward;
        this.map = loadedData.map;
        this.isreload = loadedData.isreload;
        this.hasSwat = loadedData.hasSwat;
        this.playerLocationHashMap = loadedData.playerLocationHashMap;
    }

    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Data loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}