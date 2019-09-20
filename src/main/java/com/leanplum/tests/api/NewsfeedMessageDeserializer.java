package com.leanplum.tests.api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class NewsfeedMessageDeserializer implements JsonDeserializer<List> {

    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject messageEntries = json.getAsJsonObject();

        //messageEntries.keySet();
        System.out.println(messageEntries.keySet());
        
        return null;
    }
}
