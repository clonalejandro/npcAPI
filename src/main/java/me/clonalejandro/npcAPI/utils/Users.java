package me.clonalejandro.npcAPI.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

class Users {


    /** SMALL CONSTRUCTORS **/

    private final String uuid;

    private Users(String uuid){
        this.uuid = uuid;
    }


    /** REST **/

    /**
     * This method return a UUID
     * @return
     */
    String getUuid() {
        return uuid;
    }


    /**
     * This method return to a UUID from Player name
     * @param name
     * @return
     */
    static Users getUUID(String name){
        try {
            final URL url = getUrl(name);
            final HttpURLConnection req = (HttpURLConnection) url.openConnection();

            //START Request
            req.setRequestProperty("Content-Type", "application/json");
            req.connect();

            final InputStream stream = (InputStream) req.getContent();
            final InputStreamReader reader = new InputStreamReader(stream);

            final JsonParser jsonParser = new JsonParser();
            final JsonElement element = jsonParser.parse(reader);
            final JsonObject object = element.getAsJsonObject();

            //END Request
            stream.close();
            reader.close();
            req.disconnect();

            final String id = object.get("id").getAsString();

            return new Users(id);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /** OTHERS **/

    /**
     * This method return to a URL from MojangAPI
     * @param name
     * @return
     * @throws IOException
     */
    private static URL getUrl(String name) throws IOException{
        return new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
    }


}