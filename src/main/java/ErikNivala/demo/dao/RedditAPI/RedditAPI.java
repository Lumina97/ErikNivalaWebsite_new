package ErikNivala.demo.dao.RedditAPI;

import org.springframework.stereotype.Repository;

@Repository("RedditAPI")
public class RedditAPI  implements RedditDao{

    @Override
    public String downloadImagesFromSubreddit(String subreddit, int amountOfPostsToSearch, String[] titleFilters) {

        return null;
    }
}
