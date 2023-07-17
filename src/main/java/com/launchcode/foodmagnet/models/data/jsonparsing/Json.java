package com.launchcode.foodmagnet.models.data.jsonparsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }

    public static JsonNode parse(String src) throws JsonProcessingException {

        return objectMapper.readTree(src);
    }

    public static <A> A fromJson(JsonNode node, Class<A> aclass) throws JsonProcessingException {

        return objectMapper.treeToValue(node, aclass);
    }

}
