package me.clonalejandro.npcAPI.npc;

import me.clonalejandro.npcAPI.utils.Manager;

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

public abstract class CraftNpc {


    /** SMALL CONSTRUCTORS **/

    //none...


    /** REST **/

    /**
     * @param object
     * @param name
     * @param value
     */
    public void setVal(Object object, String name, Object value){
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }


    /**
     * @param object
     * @param name
     * @return
     */
    public Object getVal(Object object, String name){
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
     * @return
     */
    public Class<?> Packet(){
        try {
            Class packet = Manager.getNMSClass("PacketPlayOutNamedEntitySpawn");
            packet.newInstance();
            return packet;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * @param x
     * @return
     */
    public Integer getX(Double x){
        return (Integer) Helper(x * 32.0D);
    }


    /**
     * @param y
     * @return
     */
    public Integer getY(Double y){
        return (Integer) Helper(y * 32.0D);
    }


    /**
     * @param z
     * @return
     */
    public Integer getZ(Double z){
        return (Integer) Helper(z * 32.0D);
    }


    /**
     * @param yaw
     * @return
     */
    public Byte getYaw(float yaw){
        return (byte) ((int) (yaw * 256.0F / 360.0F));
    }


    /**
     * @param pitch
     * @return
     */
    public Byte getPitch(float pitch){
        return (byte) ((int) (pitch * 256.0F / 360.0F));
    }


    /**
     * @return
     */
    public Class<?> getWatcher(){
        try {
            Class<?> clazz = DataWatcher();

            assert clazz != null;

            Method a = clazz.getDeclaredMethod("a", int.class, byte.class);
            a.invoke(null, 10, (byte)127);

            return clazz;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /** OTHERS **/

    /**
     * @return
     */
    private Class<?> MathHelper(){
        return Manager.getNMSClass("MathHelper");
    }


    /**
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


    /**
     * @return
     */
    private Class<?> DataWatcher() {
        try {
            Class<?> watcher =  Manager.getNMSClass("DataWatcher");
            watcher.getConstructor(Entity.class).newInstance((Object) null);
            return watcher;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
