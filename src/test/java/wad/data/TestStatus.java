/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.data;

import java.util.Calendar;
import java.util.Date;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/**
 * Due to twitter4j Status class supporting only results from the API,
 * this class will allow us to test TwitterCrunch. 
 * NOTE: Only the createdAt method is needed. 
 * @param Date createdAt A sample date you want to test
 */
public class TestStatus implements Status {

    private Calendar cal;
    
    public TestStatus(Date createdAt) {
        this.cal = Calendar.getInstance();
        cal.setTime(createdAt);
    }
    
    @Override
    public Date getCreatedAt() {
        return this.cal.getTime();
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isTruncated() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getInReplyToStatusId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getInReplyToUserId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getInReplyToScreenName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GeoLocation getGeoLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Place getPlace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isFavorited() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRetweeted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getFavoriteCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User getUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRetweet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Status getRetweetedStatus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long[] getContributors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getRetweetCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRetweetedByMe() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getCurrentUserRetweetId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPossiblySensitive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getIsoLanguageCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(Status o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getAccessLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserMentionEntity[] getUserMentionEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public URLEntity[] getURLEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HashtagEntity[] getHashtagEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MediaEntity[] getMediaEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SymbolEntity[] getSymbolEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
