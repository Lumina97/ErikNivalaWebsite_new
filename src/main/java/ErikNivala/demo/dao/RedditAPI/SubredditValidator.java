package ErikNivala.demo.dao.RedditAPI;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SubredditValidator {

    final String oAuthURL = "https://oauth.reddit.com";
    private RedditAPITokens tokens;

    private RedditAuthentication authentication = new RedditAuthentication();

    public boolean validateSubreddit(String subreddit) {

        //check input validity
        if (subreddit == null || subreddit.isEmpty()) {
            System.out.println("Passed subreddit was invalid!");
            return false;
        }

        //check token validity
        tokens = authentication.getTokens();
        if (tokens.getAccess_Token() == null || tokens.getAccess_Token().isEmpty()) {
            System.out.println("Access token is invalid. Cannot continue with request!");
            return false;
        }
        //check if subreddit actually exists
        return checkSubredditValidity(subreddit);
    }


    private boolean checkSubredditValidity(String subreddit) {
        String url = oAuthURL + "/r/" + subreddit + "/hot?limit=1";

        try {
            HttpRequest getRequest = null;
            getRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Authorization", "BEARER " + tokens.getAccess_Token())
                    .header("User-Agent", "Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1 Mobile/15E148 Safari/604.1")
                    .GET()
                    .build();


            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            //302  with invalid subreddit
            //subreddit was not found
            if (response.body().contains("302")) {
                System.out.println("Given subreddit was not a valid subreddit!");
                return false;
            }

            return true;

        } catch (URISyntaxException e) {
            System.out.println("There was an URISyntaxException with the subreddit validation request!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("There was an IOException with the subreddit validation request!");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("There was an InterruptedException with the subreddit validation request!");
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            System.out.println("There was an error with the subreddit validation request!");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
