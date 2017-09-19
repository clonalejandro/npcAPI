package me.clonalejandro.npcAPI.npc;

import com.mojang.authlib.GameProfile;
import me.clonalejandro.npcAPI.utils.Manager;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    private final int entityID;
    private final Location location;
    private final boolean hideTab;
    private final boolean rotation;
    private final GameProfile gameProfile;

    private static Plugin plugin;
    private static boolean started = false;

    public static List<EntityNpc> npcs = new ArrayList<>();

    private List<Player> rendered = new ArrayList<>();
    private String nameInTab;

    /**
     * This method is a constructor class
     * @param name
     * @param location
     * @param rotation
     * @param hideTab
     * @param nameInTab
     */
    public EntityNpc(String name, Location location, boolean rotation, boolean hideTab, String nameInTab){
        this.location = location;
        this.rotation = rotation;
        this.hideTab = hideTab;

        if (nameInTab == null || nameInTab.equalsIgnoreCase("null") || nameInTab.equalsIgnoreCase(" "))
            this.nameInTab = name;

        entityID = getID();
        gameProfile = Manager.getGameProfile(name);

        if(!npcs.contains(this))
            npcs.add(this);
    }


    /** REST **/

    /**
     * This method test if player rendered to an npc
     * @param plugin
     */
    public static void Task(Plugin plugin){
        if (!started){
            started = true;
            EntityNpc.plugin = plugin;
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    for (EntityNpc npc : npcs) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (npc.location.getWorld().equals(p.getWorld())) {
                                if (npc.location.distance(p.getLocation()) > 60 && npc.rendered.contains(p))
                                    npc.destroy(p);
                                else if (npc.location.distance(p.getLocation()) < 60 && !npc.rendered.contains(p))
                                    npc.spawn(p, npc.rotation);
                            } else npc.destroy(p);
                        }
                    }
                }
            }, 0L, 30L);
        }
    }


    /**
     * This method spawn an npc
     * @param player
     * @param rotation
     */
    @NPC
    public void spawn(final Player player, boolean rotation) {
        try {
            Object packet = Packet();

            configure(packet);
            Manager.sendPacket(player, packet);

            //clearAI(player);

            if (!hideTab) addTab(nameInTab);

            if (rotation){
                final float yaw = location.getYaw();

                Class<?> headPacket = headRotationPacket();
                setVal(headPacket, "a", entityID);
                setVal(headPacket, "b", getYaw(yaw));

                Manager.sendPacket(player, headPacket);
            }

            Bukkit.getScheduler().runTaskLater(EntityNpc.plugin, new Runnable(){
                @Override
                public void run() {
                    removeTabList(player);
                }
            },26L);

            rendered.add(player);

            //TODO: Event
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * This method destroy a npc
     * @param player
     */
    @NPC
    public void destroy(Player player) {
        try {
            Class<?> packet = packetDestroy();

            removeTabList(player);
            Manager.sendPacket(player, packet);

            Class<?> scoreboard = Manager.getNMSClass("PacketPlayOutScoreboardTeam");
            scoreboard.newInstance();

            Field a = scoreboard.getDeclaredField("a");
            Field h = scoreboard.getDeclaredField("h");

            a.setAccessible(true);
            a.set(scoreboard, gameProfile.getName());
            a.setAccessible(false);

            h.setAccessible(true);
            h.set(scoreboard, 1);
            h.setAccessible(false);

            Manager.sendPacket(player, scoreboard);
            rendered.remove(player);

            //TODO: Event
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }


    /**
     * This method return gameProfile to npc
     * @return
     */
    public GameProfile getGameProfile() {
        return gameProfile;
    }


    /**
     * This method return if npc is in RotationMode
     * @return
     */
    public boolean isRotation() {
        return rotation;
    }


    /**
     * This method return if npc is Hidden in tab
     * @return
     */
    public boolean isHideTab() {
        return hideTab;
    }


    /**
     * This method return to a Npcs List
     * @return
     */
    public static List<EntityNpc> getNpcs() {
        return npcs;
    }


    /**
     * This method return to a render List players
     * @return
     */
    public List<Player> getRendered() {
        return rendered;
    }


    /**
     * This method return to a npc Location
     * @return
     */
    public Location getLocation(){
        return location;
    }


    /**
     * This method return a npc name in TabList
     * @return
     */
    public String getNameInTab(){
        return nameInTab;
    }


    /** OTHERS **/

    /**
     * This method configure a main packet
     * @param packet
     */
    @NPC
    private void configure(Object packet){
        final double x = location.getX(),
                y = location.getY(),
                z = location.getZ();

        final float yaw = location.getYaw(),
                pitch = location.getPitch();

        final DataWatcher dataWatcher = new DataWatcher(null);
        dataWatcher.a(10, (byte) 127);

        setVal(packet, "a", entityID);
        setVal(packet, "b", gameProfile.getId());//get profile ID
        setVal(packet, "c", getX(x));
        setVal(packet, "d", getY(y));
        setVal(packet, "e", getZ(z));
        setVal(packet, "f", getYaw(yaw));
        setVal(packet, "g", getPitch(pitch));
        setVal(packet, "i", dataWatcher);
    }


    /**
     * This method add to Tab an npc
     * @param name
     * @throws RuntimeException
     */
    private void addTab(String name) throws RuntimeException{
        if (nameInTab != null) {
            nameInTab = Manager.translator(name);

            final Object packet = packetTab();

            if (packet == null) throw new RuntimeException();

            final Object data = configurePacket(packet.getClass());
            final Class<?> element = Manager.getNMSClass("PacketPlayOutPlayerInfo");
            final Class[] elements = element.getClasses();

            Object[] obj = null;
            Object Enum = null;

            for (Class<?> clazz : elements)
                if (clazz.getSimpleName().equalsIgnoreCase("EnumPlayerInfoAction"))
                    obj = clazz.getEnumConstants();

            if (obj == null) throw new RuntimeException();

            for (Object object : obj)
                if (object.toString().equalsIgnoreCase("ADD_PLAYER"))
                    Enum = object;

            //TODO: replace Class<?> per Object
            @SuppressWarnings("unchecked")
            List<Class<?>> players = (List<Class<?>>) getVal(packet, "b");
            players.add((Class <?>) data);

            setVal(packet, "a", Enum);
            setVal(packet, "b", players);
        }
    }


    /**
     * This method return to a packetTab
     * @return
     */
    private Object packetTab() {
        try {
            return Manager.getNMSClass("PacketPlayOutPlayerInfo").newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * This method configure and return Data packet for a packetTab
     * @param packet
     * @return
     */
    private Object configurePacket(Class<?> packet){
        try {
            final Class IChatBaseComponent = Manager.getNMSClass("IChatBaseComponent");
            Class enumSET;


            if (!getVersion().equals("1.8"))
                enumSET = Manager.getNMSClass("EnumGamemode");
            else enumSET = Manager.getNMSClass("WorldSettings").getDeclaredClasses()[0];

            final Enum enumRES = Enum.valueOf(enumSET, "NOT_SET");

            final Object chatBaseComponent = ChatBaseComponent(ComponentSerializer.toString(
                                                new TextComponent(
                                                        Manager.translator(nameInTab)
                                                )
                                            ));

            return packet.getDeclaredClasses()[1].
                    getDeclaredConstructor(GameProfile.class, int.class, enumSET, IChatBaseComponent).
                    newInstance(gameProfile, 1, enumRES, chatBaseComponent);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * @param json
     * @return
     * @throws Exception
     */
    private Object ChatBaseComponent(String json) throws Exception{
        Class<?> IChatBaseComponentClass = Manager.getNMSClass("IChatBaseComponent");
        Class<?> ChatSerializerClass = IChatBaseComponentClass.getClasses()[0];

        return ChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, json);
    }


    /**
     * This method return an HeadRotation packet
     * @return
     */
    private Class<?> headRotationPacket(){
        try { return Manager.getNMSClass("PacketPlayOutEntityHeadRotation"); }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * This method cancel AI when player damage a npc
     * @param player
     */
    private void clearAI(Player player){
        try {
            Class<?> packet = Manager.getNMSClass("PacketPlayOutScoreboardTeam");
            packet.newInstance();

            setVal(packet,"a", gameProfile.getName());
            setVal(packet, "b", gameProfile.getName());
            setVal(packet, "e", "never");
            setVal(packet, "h", 0);
            setVal(packet, "i", 1);

            Field g = packet.getDeclaredField("g");
            g.setAccessible(true);

            ((Collection) g.get(packet)).add(gameProfile.getName());

            Manager.sendPacket(player, packet);
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }


    /**
     * This method return a PacketDestroy
     * @return
     */
    private Class<?> packetDestroy(){
        try {
            Class<?> packet = Manager.getNMSClass("PacketPlayOutEntityDestroy");
            packet.getConstructor(int[].class).newInstance((Object) new int[] {entityID});
            return packet;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * This method remove a npc From Tab List
     * @param player
     * @throws RuntimeException
     */
    private void removeTabList(Player player) throws RuntimeException{
        try {
            final Object packet = packetTab();

            if (packet == null) throw new RuntimeException();

            final Object Data = configurePacket(packet.getClass());

            @SuppressWarnings("unchecked")
            List<Class<?>> players = (List<Class<?>>) getVal(packet, "b");
            players.add((Class<?>) Data);

            Class[] Enum = Manager.getNMSClass("PacketPlayOutPlayerInfo").getClasses();
            Class<?> rem = null;

            for (Class<?> clazz : Enum)
                if (clazz.getName().equalsIgnoreCase("EnumPlayerInfoAction"))
                    rem = clazz;

            if (rem == null) throw new RuntimeException();

            Object[] enums = rem.getEnumConstants();
            Object remove = null;

            for (Object object : enums)
                if (object.toString().equalsIgnoreCase("REMOVE_PLAYER"))
                    remove = object;

            setVal(packet, "a",remove);
            setVal(packet, "b", players);

            Manager.sendPacket(player, packet);
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }


}
