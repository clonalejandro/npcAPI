package me.clonalejandro.npcAPI.npc;

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

public interface Entity {


    /** TAGS **/

    /**
     * This tag is for a only Npc Events methods
     */
    public @interface Event{}


    /**
     * This tag is for a only Npc methods
     */
    public @interface NPC{}


    /** METHODS **/

    /**
     * This method spawn an Npc
     * @param player
     * @param rotation
     */
    @NPC
    void spawn(Player player, boolean rotation);


    /**
     * This method destroy an Npc
     * @param player
     */
    @NPC
    void destroy(Player player);


}