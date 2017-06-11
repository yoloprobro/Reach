package me.yonatanx.Reach;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Yonatan.
 */

public class Reach extends JavaPlugin implements Listener {

    //TODO: other entities support

    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }
    public void onDisable(){}

    private double getReachSize(){
        return getConfig().getDouble("reach");
    }

    private double getDamage(){
        return getConfig().getDouble("damage");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (event.getAction() != Action.LEFT_CLICK_AIR) return;
        if (!event.getPlayer().hasPermission("reach.reach")) return;
        for (Block block : event.getPlayer().getLineOfSight(null, (int)Math.ceil(getReachSize()))) {
            Location location = block.getLocation();
            for (Entity entity : event.getPlayer().getNearbyEntities(getReachSize(), getReachSize(), getReachSize())) {
                if (location.getWorld() != entity.getWorld()) return;
                if (location.getBlockX() != entity.getLocation().getBlockX()) return;
                if (location.getBlockZ() != entity.getLocation().getBlockZ()) return;
                if (location.getBlockY() == entity.getLocation().getY() || location.getBlockY() == entity.getLocation().getBlockY() + 1 || location.getBlockY() == entity.getLocation().getBlockY() - 1) {
                    if (entity instanceof Player)
                        ((Player) entity).damage(getDamage(), event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!(ReachUtils.calculateDistance(event.getDamager().getLocation(), event.getEntity().getLocation()) > getReachSize())) return;

        event.setCancelled(true);
    }
}
