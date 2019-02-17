package com.knusbaum.globetrotter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.knusbaum.globetrotter.Json.ErrorResponse;
import com.knusbaum.globetrotter.Json.StringResponse;
import com.knusbaum.globetrotter.Json.StringRequest;
import com.knusbaum.globetrotter.util.Either;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class Globetrotter {

    // Static init and Singleton stuff
    private static Globetrotter gt;
    public static Globetrotter instance() {
        if(gt == null) {
            throw new GlobetrotterUninitializedException();
        }
        return gt;
    }

    public static void initGlobal(String hostport, String requestURI, String translation) {
        if(gt == null) {
            gt = new Globetrotter(hostport, requestURI, translation);
        }
    }

    public static void setGlobalTranslation(String translation) {
        instance().setTranslation(translation);
    }

    public static String getGlobalTranslation() {
        return instance().getTranslation();
    }

    // Actual class
    private String hostport;
    private String requestURI;
    private String translation;

    public Globetrotter(String hostport, String requestURI, String translation) {
        this.hostport = hostport;
        this.requestURI = requestURI;
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Either<StringResponse,ErrorResponse> lookupString(String string)
            throws URISyntaxException, IOException {
        return lookupString(string, this.translation);
    }

    public Either<StringResponse,ErrorResponse> lookupString(String string, String translation)
            throws URISyntaxException, IOException {
        URI uri = new URI(String.format("http://%s%s", hostport, requestURI));

        StringRequest sr = new StringRequest(string, translation);

        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(sr.toJSON().getBytes());
        out.flush();
        InputStream in1 = conn.getInputStream();
        BufferedReader bis = new BufferedReader(new InputStreamReader(in1));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bis.readLine()) != null) {
            sb.append(line);
        }
        String reply = sb.toString();
        try {
            return (Either<StringResponse,ErrorResponse>)Either.Success(StringResponse.fromJson(reply));
        } catch (JsonProcessingException e) {
            return (Either<StringResponse,ErrorResponse>)Either.Failure(ErrorResponse.fromJson(reply));
        }
    }

}
