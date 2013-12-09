package wad.jlab.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import twitter4j.Status;

/** 
 * Simple data class to store the latest winning result.
 * Use to avoid getting rate-limited.
 * @param hashtag Simply provide the hashtag. Everything else is generated.
 */
public class TwitterCache {
    
    @Value("${message}")
    private String hashtag;
    private Date dateFetched;
    private Date formattedDate;
    private int cacheTimeout = 5;
    private int cacheTime = 0;
    private List<Status> tweets;
    
    public TwitterCache(String hashtag) {
        this(hashtag,new ArrayList());
    }
    
    public TwitterCache(String hashtag, List<Status> tweets) {
        this.hashtag=hashtag;
        this.dateFetched = Calendar.getInstance().getTime();
        this.tweets=tweets;
        this.cacheTime+=1;
    }

    public int getCacheTimeout() {
        return cacheTimeout;
    }

    public void setCacheTimeout(int cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
    }

    public int getCacheTime() {
        return cacheTime;
    }
    
    public void incrementCacheTime() {
        this.cacheTime+=1;
    }

    public String getHashtag() {
        return hashtag.substring(1); //return plaintext answer to simplify styling
    }
    
    public String getHashtagWithHash() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
    
    public Date getDateFetched() {
        return dateFetched;
    }
    
    //change into f ull hour timeout
    public boolean hasTimedOut() {
        //return true; // enable to test per hand
        Calendar cachetime = Calendar.getInstance();
        cachetime.setTime(dateFetched);
        return cachetime.get(Calendar.MINUTE) != Calendar.getInstance().get(Calendar.MINUTE); 
    }
    
    
} 
