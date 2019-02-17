package com.knusbaum.globetrotter.Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class StringResponse extends ReadableFromJson {
    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public static StringResponse fromJson(String json) throws JsonProcessingException, IOException {
        ObjectMapper om = new ObjectMapper();
        ObjectReader or = om.reader(StringResponse.class);
        return or.readValue(json);
    }

    public static StringResponse fromJson(Reader r) throws  JsonProcessingException, IOException {
        ObjectMapper om = new ObjectMapper();
        ObjectReader or = om.reader(StringResponse.class);
        return or.readValue(r);
    }

    public static StringResponse fromJson(InputStream is) throws  JsonProcessingException, IOException {
        ObjectMapper om = new ObjectMapper();
        ObjectReader or = om.reader(StringResponse.class);
        return or.readValue(is);
    }
}

