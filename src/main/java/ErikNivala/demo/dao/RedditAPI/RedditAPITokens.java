package ErikNivala.demo.dao.RedditAPI;

import java.util.Date;

public class RedditAPITokens {
    private String refresh_token;
    private String access_Token;
    private Date lastTokenRefreshTime;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_Token() {
        return access_Token;
    }

    public void setAccess_Token(String access_Token) {
        this.access_Token = access_Token;
    }

    public Date getLastTokenRefreshTime() {
        return lastTokenRefreshTime;
    }

    public void setLastTokenRefreshTime(Date lastTokenRefreshTime) {
        this.lastTokenRefreshTime = lastTokenRefreshTime;
    }
}

