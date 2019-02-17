package com.knusbaum.globetrotter.Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class ErrorResponse extends ReadableFromJson {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static ErrorResponse fromJson(String json) throws JsonProcessingException, IOException {
        ObjectMapper om = new ObjectMapper();
        ObjectReader or = om.reader(ErrorResponse.class);
        return or.readValue(json);
    }

    public static ErrorResponse fromJson(Reader r) throws  JsonProcessingException, IOException {
        ObjectMapper om = new ObjectMapper();
        ObjectReader or = om.reader(ErrorResponse.class);
        return or.readValue(r);
    }

    public static ErrorResponse fromJson(InputStream is) throws  JsonProcessingException, IOException {
        ObjectMapper om = new ObjectMapper();
        ObjectReader or = om.reader(ErrorResponse.class);
        return or.readValue(is);
    }
}
