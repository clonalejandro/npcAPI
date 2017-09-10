package me.clonalejandro.npcAPI.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.clonalejandro.ReflectionAPI.ReflectionAPI;
import me.clonalejandro.npcAPI.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

import java.util.Random;
import java.util.UUID;

/**
 * Created by alejandrorioscalera
 * On 9/9/17
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

public class Manager extends ReflectionAPI {


    /** SMALL CONSTRUCTORS **/

    private static Main instance = Main.instance;

    public final static String SPACE = " ";
    public final static String NAME = "npcAPI";


    /** REST **/

    /**
     * @return
     */
    public static PluginManager getPM(){
        return instance.getServer().getPluginManager();
    }


    /**
     * @return
     */
    public static FileConfiguration getConfig(){
        return instance.getConfig();
    }


    /**
     * @param str
     * @return
     */
    public static String translator(String str){
        return ChatColor.translateAlternateColorCodes('&', str);
    }


    /**
     * @return
     */
    public static Main getInstance(){
        return instance;
    }


    /**
     * @param name
     * @return
     */
    public static GameProfile getGameProfile(String name){
        final Users user = name != null ? Users.getUUID(name) : null;
        final Skins skin = user != null ? Skins.getSkin(user.getUuid()) : null;
        final String pname = getRandomString();

        if (skin != null){
            GameProfile profile = new GameProfile(UUID.randomUUID(), pname);
            profile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
            return profile;
        } else {
            try { return new GameProfile(UUID.randomUUID(), pname); }
            catch (NullPointerException ex){
                ex.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("§4§lNPC-API> §fError in profiles");
                return null;
            }
        }
    }


    /** OTHERS **/

    /**
     * @return
     */
    private static String getRandomString(){
        final StringBuilder stringBuilder = new StringBuilder();

        final long ms = new java.util.GregorianCalendar().getTimeInMillis();
        final Random rand = new Random(ms);
        final int length = 8;

        int i = 0;

        while ( i < length){
            char c = (char) rand.nextInt(255);
            if ((c >= '0' && c <='9') || (c >='A' && c <='Z')){
                stringBuilder.append(c);
                i++;
            }
        }
        return stringBuilder.toString();
    }


}