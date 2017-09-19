package me.clonalejandro.npcAPI.npc;

import com.mojang.authlib.GameProfile;
import me.clonalejandro.npcAPI.utils.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

abstract class CraftNpc {


    /** REST **/

    /**
     * This method set Value via reflection
     * @param object
     * @param name
     * @param value
     */
    void setVal(Object object, String name, Object value){
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        }
        catch (Exception ignored){}
    }


    /**
     * This method get Value via reflection
     * @param object
     * @param name
     * @return
     */
    Object getVal(Object object, String name){
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * This method return a Main packet for spawn npc
     * @return
     */
    Object Packet(){
        try { return Manager.getNMSClass("PacketPlayOutNamedEntitySpawn").newInstance(); }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * This method operate a x coord
     * @param x
     * @return
     */
    Integer getX(Double x){
        return (Integer) Helper(x * 32.0D);
    }


    /**
     * This method operate a y coord
     * @param y
     * @return
     */
    Integer getY(Double y){
        return (Integer) Helper(y * 32.0D);
    }


    /**
     * This method operate a z coord
     * @param z
     * @return
     */
    Integer getZ(Double z){
        return (Integer) Helper(z * 32.0D);
    }


    /**
     * This method operate a Yaw coord
     * @param yaw
     * @return
     */
    Byte getYaw(float yaw){
        return (byte) ((int) (yaw * 256.0F / 360.0F));
    }


    /**
     * This method operate a Pitch coord
     * @param pitch
     * @return
     */
    Byte getPitch(float pitch){
        return (byte) ((int) (pitch * 256.0F / 360.0F));
    }


    /**
     * This method return a ID
     * @return
     */
    int getID(){
        return (int) Math.ceil(Math.random() * 1000) + 2000;
    }


    /**
     * This method return to a Server version
     * @return
     */
    String getVersion(){
        String version = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + "entity.CraftPlayer";
        version = version.replace("org.bukkit.craftbukkit.v", "");
        version = version.replace(".entity.CraftPlayer", "");
        version = version.replace("_", ".");
        version = version.replace("R", "");

        if (version.contains("1.8")) version = "1.8";
        else if (version.contains("1.12")) version = "1.12";

        return version;
    }


    /**
     * This method return an PlayerEntity instanced
     * @param location
     * @param gameProfile
     * @return
     */
    PlayerEntity playerEntity(Location location, GameProfile gameProfile){
        return new PlayerEntity(getVersion(), location, gameProfile);
    }


    /** OTHERS **/

    /**
     * This method return a class MathHelper
     * @return
     */
    private Class<?> MathHelper(){
        return Manager.getNMSClass("MathHelper");
    }


    /**
     * This method invoke a Method for MathHelper
     * @param doub
     * @return
     */
    private Object Helper(Double doub){
        try {
            Method floor = MathHelper().getDeclaredMethod("floor", double.class);
            return floor.invoke(null, doub);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}