package me.clonalejandro.npcAPI.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandrorioscalera
 * On 18/9/17
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

public class JsonFixer {


    /** SMALL CONSTRUCTORS **/

    private final boolean isBreaked;

    private String json;
    private String lastJson;

    private static List<String> array = new ArrayList<>();

    public JsonFixer(String json, boolean isBreaked){
        this.json = json;
        this.isBreaked = isBreaked;

        setJson();
        setArray();
    }


    /** REST **/

    /**
     * This method return a Json Message fixed
     * @return
     */
    public String getJson() {
        return json;
    }


    /**
     * This method return to an ArrayElements
     * @return
     */
    public List<String> getArray() {
        return array;
    }


    /** OTHERS **/

    /**
     * This method set Json and make the Main action
     */
    private void setJson(){
        lastJson = json;

        if (isBreaked) {
            json = json.replace("text=", "\"text\":").
                    replace("siblings=", "\"siblings\":").
                    replace("style=Style{", "\"style\":{").
                    replace("hasParent=", "\"hasParent\":").
                    replace("color=", "\"color\":").
                    replace("bold=", "\"bold\":").
                    replace("italic=", "\"italic\":").
                    replace("underlined=", "\"underlined\":").
                    replace("obfuscated=", "\"obfuscated\":").
                    replace("clickEvent=", "\"clickEvent\":").
                    replace("hoverEvent=", "\"hoverEvent\":").
                    replace("insertion=", "\"insertion\":");

            final JsonParser parser = new JsonParser();
            final JsonElement element = parser.parse(json);
            final JsonObject object = element.getAsJsonObject();

            final String text = object.get("text").getAsString();

            json = "{\"text\":\"" + Manager.translator(text) + "\"}";
        }
        else json = "{\"text\":\"" + Manager.translator(json) + "\"}";
    }


    /**
     * This method set an Array and make the Secondary action
     */
    private void setArray(){
        if (isBreaked){
            String gson = lastJson.replace("text=", "\"text\":").
                    replace("siblings=", "\"siblings\":").
                    replace("style=Style{", "\"style\":{").
                    replace("hasParent=", "\"hasParent\":").
                    replace("color=", "\"color\":").
                    replace("bold=", "\"bold\":").
                    replace("italic=", "\"italic\":").
                    replace("underlined=", "\"underlined\":").
                    replace("obfuscated=", "\"obfuscated\":").
                    replace("clickEvent=", "\"clickEvent\":").
                    replace("hoverEvent=", "\"hoverEvent\":").
                    replace("insertion=", "\"insertion\":");

            final JsonParser parser = new JsonParser();
            final JsonElement element = parser.parse(gson);
            final JsonObject object = element.getAsJsonObject();
            final JsonObject preArray = object.get("style").getAsJsonObject();
            final Object[] objects = elements(preArray);

            int count = 0;

            final String text = object.get("text").getAsString();

            array.add(text);
            array.add(objects[count++].toString());
            array.add(objects[count++].toString());
            array.add(objects[count++].toString());
            array.add(objects[count].toString());
        }
        else array.add(new JsonParser().parse(json).getAsJsonObject().get("text").getAsString());
    }


    /**
     * This method get an Elements from json
     * @param jsonObject
     * @return
     */
    private Object[] elements(JsonObject jsonObject){
        final List<Object> objectList = new ArrayList<>();

        try { objectList.add(jsonObject.get("color").getAsString()); }
        catch (Exception ignored){ objectList.add(jsonObject.get("color").getAsJsonNull().toString()); }

        try { objectList.add(jsonObject.get("bold").getAsString()); }
        catch (Exception ignored) { objectList.add(jsonObject.get("bold").getAsJsonNull().toString()); }

        try { objectList.add(jsonObject.get("italic").getAsString()); }
        catch (Exception ignored){ objectList.add(jsonObject.get("italic").getAsJsonNull().toString()); }

        try { objectList.add(jsonObject.get("underlined").getAsString()); }
        catch (Exception ignored){ objectList.add(jsonObject.get("underlined").getAsJsonNull().toString()); }

        return objectList.toArray();
    }


}