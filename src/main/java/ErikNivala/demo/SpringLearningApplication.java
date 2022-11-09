package ErikNivala.demo;

import ErikNivala.demo.dao.FileHandling.ImageDownloader;
import ErikNivala.demo.dao.RedditAPI.RedditAuthentication;
import ErikNivala.demo.dao.RedditAPI.RedditLinksGatherer;
import ErikNivala.demo.dao.RedditAPI.SubredditValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SpringLearningApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SpringLearningApplication.class, args);
		RedditLinksGatherer links = new RedditLinksGatherer();
		ArrayList<String> linksStrings =  links.gatherLinks("Wallpaper",50,null);

		ImageDownloader downloader = new ImageDownloader();
		downloader.DownloadImagesFromLinks(linksStrings,1);

		System.out.println("test");
	}

}
