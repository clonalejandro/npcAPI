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

class Skins {


    /** SMALL CONSTRUCTORS **/

    private final String value;
    private final String signature;

    private Skins(String value, String signature){
        this.value = value;
        this.signature = signature;
    }


    /** REST **/

    /**
     * This method return to a Value of Request
     * @return
     */
    String getValue(){
        return value;
    }


    /**
     * This method return to a Signature of Request
     * @return
     */
    String getSignature(){
        return signature;
    }


    /**
     * This method return to a Skin data from uuid
     * @param uuid
     * @return
     */
    static Skins getSkin(String uuid){
        try {
            final URL url = getUrl(uuid);
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

            final JsonArray props = object.getAsJsonArray("properties");
            final JsonObject obj = (JsonObject) props.get(0);

            final String value = obj.get("value").getAsString();
            final String signature = obj.get("signature").getAsString();

            return new Skins(value, signature);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /** OTHERS **/

    /**
     * This method return a URL api from MojangAPI
     * @param uuid
     * @return
     * @throws IOException
     */
    private static URL getUrl(String uuid) throws IOException {
        return new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
    }


}