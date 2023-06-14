package org.rij.minecraft.bukkit.ametis;

import org.eclipse.jetty.http.HttpStatus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static spark.Spark.*;

public class SparkWebServer {
    private int port;
    private final AmeWL p;

    public SparkWebServer(int port, AmeWL p){
        this.port = port;
        this.p = p;
    }


    public void start(){
        port(port);
        String APIKey = p.getConfigString("API_Key");

        get("/whitelist", (request, response) -> {
            String Key = request.headers("Key");
            boolean isMatch = compareAPIKeys(APIKey, Key);
            if(!isMatch){
                halt(HttpStatus.UNAUTHORIZED_401);
            }

            String Name = request.headers("Name");
            response.type("text/plain");
            response.status(HttpStatus.OK_200);

            if(!p.isWhitelisted(Name)) {
                p.addToWhitelist(Name);
                response.body("Success");
                return response.body();
            }

            response.body("Already Whitelisted");

            return response.body();
        });
    }

    public void stop(){
        stop();
    }

    public static boolean compareAPIKeys(String storedKey, String providedKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] storedBytes = md.digest(storedKey.getBytes());
            byte[] providedBytes = md.digest(providedKey.getBytes());

            return Arrays.equals(storedBytes, providedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}
