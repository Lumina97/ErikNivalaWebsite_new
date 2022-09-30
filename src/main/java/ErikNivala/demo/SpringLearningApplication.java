package ErikNivala.demo;

import ErikNivala.demo.dao.RedditAPI.RedditAuthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLearningApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SpringLearningApplication.class, args);
		RedditAuthentication auth = new RedditAuthentication();
		System.out.println(auth.getAccessToken());
	}

}
