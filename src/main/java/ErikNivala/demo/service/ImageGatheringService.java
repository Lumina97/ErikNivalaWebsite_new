package ErikNivala.demo.service;

import ErikNivala.demo.dao.PersonDao;
import ErikNivala.demo.dao.RedditAPI.RedditDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ImageGatheringService {

    private final RedditDao redditDao;

    @Autowired
    public ImageGatheringService(@Qualifier("postgres")  RedditDao redditDao) {
        this.redditDao = redditDao;
    }

    public String gatherImagesFromSubreddit(String subreddit, int amountOfPostsToSearch, String[] titleFilters){
        redditDao.downloadImagesFromSubreddit(subreddit,amountOfPostsToSearch,titleFilters);
        return "";
    }

}
