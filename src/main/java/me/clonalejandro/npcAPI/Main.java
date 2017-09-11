package me.clonalejandro.npcAPI;

import me.clonalejandro.npcAPI.utils.Manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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

public class Main extends JavaPlugin {


    /** SMALL CONSTRUCTORS **/

    public static Main instance;


    /** REST **/

    @Override
    public void onLoad(){
        Bukkit.getConsoleSender().sendMessage(Manager.translator("&b&l" + Manager.NAME + Manager.SPACE + "&fPlugin loaded"));
    }


    @Override
    public void onEnable(){
        try {
            instance = this;

            Config();
            Events();
            Commands();

            Bukkit.getConsoleSender().sendMessage(Manager.translator("&a&l" + Manager.NAME + Manager.SPACE + "&fPlugin enabled"));
        } catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(Manager.translator("&c&l" + Manager.NAME + Manager.SPACE + "&fkilling process plugin with errors"));
            onDisable();
        }
    }


    @Override
    public void onDisable(){
        try {
            Manager.getPM().disablePlugin(instance);
            Bukkit.getConsoleSender().sendMessage(Manager.translator("&c&l" + Manager.NAME + Manager.SPACE + "&fPlugin disabled"));
        } catch (Exception ex){
            ex.printStackTrace();
            Manager.getPM().disablePlugin(this);
            Bukkit.getConsoleSender().sendMessage(Manager.translator("&4&l" + Manager.NAME + Manager.SPACE + "&fPlugin disabled with &4errors"));
        }
        instance = null;
    }


    /** OTHERS **/

    private void Events(){

    }


    private void Commands(){

    }


    private void Config(){
        saveDefaultConfig();
    }


}