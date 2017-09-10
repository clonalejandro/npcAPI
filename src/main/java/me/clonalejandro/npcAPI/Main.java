package me.clonalejandro.npcAPI;

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
        Bukkit.getConsoleSender().sendMessage("§b§lnpcAPI §floaded");
    }


    @Override
    public void onEnable(){
        try {
            instance = this;

            Config();
            Events();
            Commands();

            Bukkit.getConsoleSender().sendMessage("§a§lnpcAPI §fenabled");
        } catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§c§lnpcAPI §fkilling process with errors");
            onDisable();
        }
    }


    @Override
    public void onDisable(){

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