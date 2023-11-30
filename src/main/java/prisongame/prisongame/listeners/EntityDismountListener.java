package prisongame.prisongame.listeners;

import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;
import prisongame.prisongame.PrisonGame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityDismountListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDismountHandcuffs(EntityDismountEvent event) {
        Entity vehicle = event.getDismounted();
        Entity passenger = event.getEntity();

        System.out.println("Handcuff dismount! " + vehicle.getName() + " rode by " + passenger.getName());

        if (!(passenger instanceof Player player && vehicle instanceof Player))
            return;

        System.out.println("we're all players.");

        if (!player.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE))
            return;

        System.out.println("passenger has dolphins grace");

        if (isDisconnected(player)) {
            System.out.println("disconnected! removing & dismounting");
            player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
            vehicle.removePassenger(player);
            return;
        }

        new Exception().printStackTrace();

        System.out.println("not disconnected, cancellinga");

        event.setCancelled(true);
    }

    private boolean isDisconnected(Player player) {
        // player.getHandle().connection.isDisconnected()

        var craftPlayer = (CraftPlayer) player;
        return craftPlayer.getHandle().connection.processedDisconnect;
    }
}
