package ErikNivala.demo.dao.RedditAPI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedditAuthenticationTest {
    RedditAuthentication auth;


    @BeforeEach
    void setUp() {
     auth = new RedditAuthentication();
    }

    @AfterEach
    void tearDown() {
        auth = null;
    }

    @Test
    void getAccessToken() {
        String token = auth.getAccessToken();

        boolean hasToken = false;

        if(token != null && token.isEmpty() == false)
            hasToken = true;

        assertEquals(true,  hasToken,"Token was not returned correctly");
    }
}