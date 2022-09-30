package ErikNivala.demo.dao.RedditAPI;

public interface RedditDao {

    String downloadImagesFromSubreddit(String subreddit, int amount, String[] titleFilters);

}
