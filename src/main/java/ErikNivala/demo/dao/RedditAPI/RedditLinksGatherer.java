package ErikNivala.demo.dao.RedditAPI;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.json.simple.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class RedditLinksGatherer {

    private RedditAPITokens tokens;
    private RedditAuthentication authentication = new RedditAuthentication();

    private boolean getTokens() {
        //check token validity
        tokens = authentication.getTokens();
        if (tokens.getAccess_Token() == null || tokens.getAccess_Token().isEmpty()) {
            System.out.println("Access token is invalid. Cannot continue with request!");
            return false;
        }
        return true;
    }

    public ArrayList<String> gatherLinks(String subreddit, int amount, String[] titleFilters) {

        //check input validity
        if (subreddit == null || subreddit.isEmpty()) {
            System.out.println("Passed subreddit was invalid!");
            return null;
        }

        //check token validity
        if (getTokens() == false) {
            System.out.println("There was an error getting needed tokens for the image gathering API call!");
            return null;
        }

        if (amount == 0 || amount < 0)
            amount = 25;

        String url = CONSTANTS.oAuthURL + "/r/" + subreddit + "/hot?limit=" + amount;

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

            Gson gson = new Gson();
            JSONObject ob = gson.fromJson(response.body(), JSONObject.class);

            //cast response to linked tree list and get the children and cast them to an array list!
            ArrayList<Object> posts = (ArrayList<Object>) ((LinkedTreeMap<Object, Object>) ob.get("data")).get("children");

            ArrayList<String> ImageLinks = new ArrayList<String>();

            if(posts.size() > 0)
            {
                //Get data for every single post
                for (int i  = 0; i < posts.size(); i++) {
                    // filter out name and URL
                    LinkedTreeMap<String, String> SinglePost = (LinkedTreeMap<String, String>) ((LinkedTreeMap<Object, Object>) posts.get(i)).get("data");
                    String name = SinglePost.get("name");
                    String link = SinglePost.get("url");

                    //see if it is a T3 post and if url contains an image
                   if(name.contains("t3")) {
                       if (link.contains(".jpg") || link.contains(".png")) {

                           //add to list
                           ImageLinks.add(link);
                       }
                   }
                }
            }

            if(ImageLinks.size() > 0) return ImageLinks;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
