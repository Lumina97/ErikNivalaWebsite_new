package ErikNivala.demo.dao.RedditAPI;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class RedditAuthentication {

    private RedditAPITokens APITokens = new RedditAPITokens();
    private final String tokenFilePath = "E:\\Dev\\Java\\demo\\src\\main\\resources\\TokenFile";

    public RedditAPITokens getTokens() {
        if(APITokens.getAccess_Token() == null || APITokens.getAccess_Token().isEmpty())
            getAccessToken();


        return APITokens;
    }


    public String getAccessToken() {
        if (APITokens.getAccess_Token() == null || APITokens.getAccess_Token().isEmpty()) {
           if( refreshToken()){
               return APITokens.getAccess_Token();
           }
           return null;
        }

        return APITokens.getAccess_Token();
    }

    private boolean refreshToken() {
        //try to read token file
        if (readTokenFile() == false)
            return false;

        //try to refresh token
        if (refreshAccessToken() == false)
            return false;

        //write new token data to file
        writeTokenFile();
        return true;
    }

    private boolean refreshAccessToken() {
        try {

            //base64 encoded username(clientID) and password (secret key)
            String userPassword = "ZHmLbNUoaVSVdw" + ":" + "W0bNZeU8oYaUxCzqcWuf89JLMnqMVg";
            String Encoded = Base64.getEncoder().encodeToString(userPassword.getBytes());

            //request body
            String sBody = "grant_type=refresh_token&refresh_token=" + APITokens.getRefresh_token();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://www.reddit.com/api/v1/access_token"))
                    .header("Authorization", "Basic " + Encoded)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("User-Agent", "Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1 Mobile/15E148 Safari/604.1")
                    .POST(HttpRequest.BodyPublishers.ofString(sBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JSONObject ob = gson.fromJson(response.body(), JSONObject.class);

            String error = (String) ob.get("error");
            if (error != null) {
                throw new RuntimeException(error);
            }

            APITokens.setAccess_Token((String) ob.get("access_token"));
            APITokens.setRefresh_token((String) ob.get("refresh_token"));
            APITokens.setLastTokenRefreshTime(new Date());

            return true;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("There was an error trying to refresh the redditAPI access token!");
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean readTokenFile() {
        try {
            Object tokens = new JSONParser().parse(Files.readString(Paths.get(tokenFilePath)));
            JSONObject ob = (JSONObject) tokens;

            APITokens.setRefresh_token((String) ob.get("refresh_token"));
            APITokens.setAccess_Token((String) ob.get("access_token"));
            System.out.println("Read Token File. \nAccess: " + APITokens.getAccess_Token() + "\nrefresh: " + APITokens.getRefresh_token());
            return true;

        } catch (IOException e) {
            System.out.println("Error reading token file! - RedditAuthentication.java");
            System.out.println("Message: \n " + e.getMessage());
            return false;
        } catch (ParseException e) {
            System.out.println("Error parsing token file to JSON ! - RedditAuthentication.java");
            System.out.println("Message: \n " + e.getMessage());
            return false;
        }
    }

    private boolean writeTokenFile() {
        Gson gson = new Gson();
        String tokenString = gson.toJson(APITokens);
        try {
            Files.writeString(Paths.get(tokenFilePath), tokenString);
        } catch (IOException e) {
            System.out.println("There was an error writing tokens to file!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
