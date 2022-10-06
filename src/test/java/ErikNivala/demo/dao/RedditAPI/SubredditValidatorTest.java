package ErikNivala.demo.dao.RedditAPI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubredditValidatorTest {
    SubredditValidator validator;

    @BeforeEach
    void setUp() {
    validator = new SubredditValidator();
    }

    @AfterEach
    void tearDown() {
        validator = null;
    }

    @Test
    void validateSubredditWithValidSubredditEntry() {
        assertEquals(true, validator.validateSubreddit("background"));
    }

    @Test
    void validateSubredditWithInvalidSubredditEntry() {
        assertEquals(false, validator.validateSubreddit("ashjfobjklasd"));
    }

    @Test
    void validateSubredditWithNoSubredditEntry() {
        assertEquals(false, validator.validateSubreddit(""));
    }
}