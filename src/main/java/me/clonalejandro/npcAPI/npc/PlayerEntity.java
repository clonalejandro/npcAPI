package me.clonalejandro.npcAPI.npc;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R3.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

/**
 * Created by alejandrorioscalera
 * On 17/9/17
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

public class PlayerEntity {


    /** SMALL CONSTRUCTORS **/

    private final String version;
    private final Location location;
    private final GameProfile gameProfile;

    private Player entity;

    PlayerEntity(String version, Location location, GameProfile gameProfile){
        this.version = version;
        this.location = location;
        this.gameProfile = gameProfile;
    }

    DataWatcher dataWatcher18;


    /** REST **/

    /**
     * This method return to an EntityPlayer
     * @return
     */
    public Player getEntity() {
        return entity;
    }


    /** OTHERS **/

    /**
     * This method create an Entity NPC
     */
    private void createEntity(){
        switch (version){
            default:
                break;
            case "1.8":
                頁();
                break;
            case "1.12":
                break;
        }
    }


    /**
     * This method help to create an EntityNPC
     */
    private void 頁(){
        final MinecraftServer server = (MinecraftServer) Bukkit.getServer();
        final WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        final PlayerInteractManager playerInteractManager = new PlayerInteractManager(
                ((CraftWorld) location.getWorld()).getHandle());
        final EntityPlayer entityPlayer = new EntityPlayer(server, world, gameProfile, playerInteractManager);
        final DataWatcher dataWatcher = entityPlayer.getDataWatcher();

        dataWatcher.a(10, (byte) 127);

        dataWatcher18 = dataWatcher;
        entity = (Player) entityPlayer;
    }


}
