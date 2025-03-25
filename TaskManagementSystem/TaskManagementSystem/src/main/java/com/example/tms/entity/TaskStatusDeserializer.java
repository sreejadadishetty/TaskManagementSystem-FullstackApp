package com.example.tms.entity;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class TaskStatusDeserializer extends JsonDeserializer<TaskStatus> {

    @Override
    public TaskStatus deserialize(JsonParser p, DeserializationContext ctxt) 
        throws IOException {
        
        // Handle both string and object formats
        JsonNode node = p.getCodec().readTree(p);
        
        String value;
        if (node.isObject()) {
            // If client sends {status: {value: "TODO"}}
            value = node.get("value").asText();
        } else {
            value = node.asText();
        }

        value = value.trim().replace(" ", "_").toUpperCase();
        
        try {
            return TaskStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid TaskStatus. Valid values: " + Arrays.toString(TaskStatus.values())
            );
        }
    }
}
