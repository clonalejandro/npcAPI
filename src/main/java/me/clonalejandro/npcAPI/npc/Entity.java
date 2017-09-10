package me.clonalejandro.npcAPI.npc;

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

    public @interface Event{}
    public @interface NPC{}

    public void spawn();
    public void destroy();

}