package me.clonalejandro.npcAPI.utils;

import com.google.gson.JsonArray;
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

public class Users {


    /** SMALL CONSTRUCTORS **/

    private final String name;
    private final String uuid;

    public Users(String name, String uuid){
        this.name = name;
        this.uuid = uuid;
    }


    /** REST **/

    /**
     * @return
     */
    public String getName(){
        return name;
    }


    /**
     * @return
     */
    public String getUuid() {
        return uuid;
    }


    /**
     * @param name
     * @return
     */
    public static Users getUUID(String name){
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

            final JsonObject Reqname = object.getAsJsonObject("name");
            final JsonObject Reqid = object.getAsJsonObject("id");

            final String aname = Reqname.getAsString();
            final String id = Reqid.getAsString();

            return new Users(aname, id);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /** OTHERS **/

    /**
     * @param name
     * @return
     * @throws IOException
     */
    private static URL getUrl(String name) throws IOException{
        return new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
    }


}
