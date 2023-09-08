package com.launchcode.foodmagnet.models.data.apiData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }

    //parses raw json file into a JsonNode
    public static JsonNode parse(String src)  {

        try {
            return objectMapper.readTree(src);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //used to map json restaurant objects into pojos
    public static <A> A fromJson(JsonNode node, Class<A> aclass) {

        try {
            return objectMapper.treeToValue(node, aclass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
