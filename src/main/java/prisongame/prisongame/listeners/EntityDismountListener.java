package prisongame.prisongame.listeners;

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

        System.out.println("not disconnected, cancelling");

        event.setCancelled(true);
    }

    private boolean isDisconnected(Player player) {
        // player.getHandle().connection.isDisconnected()

        try {
            Method getHandle = player.getClass().getDeclaredMethod("getHandle");
            Object handle = getHandle.invoke(player);
            Field connectionField = handle.getClass().getDeclaredField("b");
            Object connection = connectionField.get(handle);
            Field disconnectField = connection.getClass().getField("processedDisconnect");
            return (boolean) disconnectField.get(connection);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return false;
        }
    }
}
