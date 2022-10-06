package ErikNivala.demo;

import ErikNivala.demo.dao.RedditAPI.RedditAuthentication;
import ErikNivala.demo.dao.RedditAPI.SubredditValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLearningApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SpringLearningApplication.class, args);
		SubredditValidator valid = new SubredditValidator();
		valid.validateSubreddit("backkjhfground");
	}

}
