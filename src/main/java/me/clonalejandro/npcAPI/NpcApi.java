package me.clonalejandro.npcAPI;

import me.clonalejandro.npcAPI.npc.Entity;
import me.clonalejandro.npcAPI.npc.EntityNpc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

/**
 * Created by alejandrorioscalera
 * On 11/9/17
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

public class NpcApi {


    /**
     * This method create an Npc
     * @param name
     * @param location
     * @param plugin
     */
    @Entity.NPC
    public static void createNpc(String name, Location location, Plugin plugin){
        new EntityNpc(name, location, false, false, null);
        EntityNpc.Task(plugin);
    }


    /**
     * This method create an Npc
     * @param name
     * @param location
     * @param rotation
     * @param plugin
     */
    @Entity.NPC
    public static void createNpc(String name, Location location, boolean rotation, Plugin plugin){
        new EntityNpc(name, location, rotation, false, null);
        EntityNpc.Task(plugin);
    }


    /**
     * This method create an Npc
     * @param name
     * @param location
     * @param rotation
     * @param hideInTab
     * @param nameInTab
     * @param plugin
     */
    @Entity.NPC
    public static void createNpc(String name, Location location, boolean rotation, boolean hideInTab, String nameInTab, Plugin plugin){
        new EntityNpc(name, location, rotation, hideInTab, nameInTab);
        EntityNpc.Task(plugin);
    }


    /**
     * This method create an Npc
     * @param name
     * @param location
     * @param hideInTab
     * @param nameInTab
     * @param plugin
     */
    @Entity.NPC
    public static void createNpc(String name, Location location, boolean hideInTab, String nameInTab, Plugin plugin){
        new EntityNpc(name, location, false, hideInTab, nameInTab);
        EntityNpc.Task(plugin);
    }


    /**
     * This method remove Npc
     * @param npc
     */
    @Entity.NPC
    public static void removeNpc(EntityNpc npc){
        for (Player player : Bukkit.getOnlinePlayers())
            npc.destroy(player);
    }


    /**
     * This method remove all Npcs
     */
    @Entity.NPC
    public static void removeNpcs(){
        for (EntityNpc npc : EntityNpc.npcs)
            for (Player player : Bukkit.getOnlinePlayers())
                npc.destroy(player);
    }


    /**
     * This method remove all Npcs
     * @param npcList
     */
    @Entity.NPC
    public static void removeNpcs(List<EntityNpc> npcList){
        for (EntityNpc npc : npcList)
            for (Player player : Bukkit.getOnlinePlayers())
                npc.destroy(player);
    }


    /**
     * This method remove all Npcs
     * @param npcList
     */
    @Entity.NPC
    public static void removeNpcs(EntityNpc[] npcList){
        for (EntityNpc npc : npcList)
            for (Player player : Bukkit.getOnlinePlayers())
                npc.destroy(player);
    }


    /**
     * This method remove all Npcs
     * @param npcList
     */
    @Entity.NPC
    public static void removeNpcs(Collection<EntityNpc> npcList){
        for (EntityNpc npc : npcList)
            for (Player player : Bukkit.getOnlinePlayers())
                npc.destroy(player);
    }


}
