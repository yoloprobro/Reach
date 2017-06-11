package me.yonatanx.Reach;

import org.bukkit.Location;

/**
 * Created by Yonatan.
 */

public class ReachUtils {

    public static double calculateDistance(Location location1, Location location2){
        return Math.ceil(Math.sqrt(
                Math.pow(location1.getBlockX() - location2.getBlockX(), 2) +
                        Math.pow(location1.getBlockY() - location2.getBlockY(), 2) +
                        Math.pow(location1.getBlockZ() - location2.getBlockZ(), 2)
        ));
    }
}
