package me.clonalejandro.npcAPI.npc;

import me.clonalejandro.npcAPI.utils.Manager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by alejandrorioscalera
 * On 10/9/17
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©npcAPI 2017 / 2018
 */

public class EntityNpc extends CraftNpc implements Entity {


    /** SMALL CONSTRUCTORS **/

    private int entityID;
    private Location location;
    private String name;
    private Player player;


    /** REST **/

    public void spawn() {
        try {
            final Class packet = Packet();

            configure(packet);
            Manager.sendPacket(player, packet);

        } catch (Exception ex){

        }
    }


    public void destroy() {
    }


    /** OTHERS **/

    private void configure(Class packet){

        World world = location.getWorld();

        final double x = location.getX(),
                     y = location.getY(),
                     z = location.getZ();

        final float yaw = location.getYaw(),
                    pitch = location.getPitch();

        setVal(packet, "a", entityID);
        setVal(packet, "b", entityID);//get profile ID
        setVal(packet, "c", getX(x));
        setVal(packet, "d", getY(y));
        setVal(packet, "e", getZ(z));
        setVal(packet, "f", getYaw(yaw));
        setVal(packet, "g", getPitch(pitch));
        setVal(packet, "i", getWatcher());
    }


}
