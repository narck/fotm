package wad.jlab.data;

import java.util.Calendar;
import java.util.Date;

/** 
 * Simple data class to store the latest winning result.
 * Use to avoid getting rate-limited.
 * @param hashtag Simply provide the hashtag. Everything else is generated.
 */
public class TwitterCache {
    /**
     * The hashtag as a string, with leading hash.
     */
    private String hashtag;
    /**
     * The date when this cache entry was created.
     */
    private Date dateFetched;
    /**
     * These two are just to enable testing by hand if you need them.
     */
    private int cacheTimeout = 5;
    private int cacheTime = 0;
    
    
    /**
     * Constructs the new cache and sets a date to it. Cachetime is for testing if needed.
     * @param hashtag as a string object.
     */
    public TwitterCache(String hashtag) {
        this.hashtag=hashtag;
        this.dateFetched = Calendar.getInstance().getTime();
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
    /**
     * Increments the current cachetime.
     */
    public void incrementCacheTime() {
        this.cacheTime+=1;
    }

    /**
     * To access the locale properties we pass a substring by default to the servlet.
     * 
     * @return String the hashtag without leading hash.
     */
    public String getHashtag() {
        return hashtag.substring(1); //return plaintext answer to simplify styling
    }
    
    /**
     * Returns the hashtag with leading hash. This is used for caching purposes in the history level.
     * @return String hashtag with leading hash.
     */
    public String getHashtagWithHash() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
    
    public Date getDateFetched() {
        return dateFetched;
    }
    
    /**
     * Returns whether the cache has timed out or not.
     * Currently checks if over an hour has passed since the last query. This is to avoid getting rate-limited.
     * @return boolean timeout value.
     */
    public boolean hasTimedOut() {
        //return true; // enable to test per hand
        Calendar cachetime = Calendar.getInstance();
        cachetime.setTime(dateFetched);
        return cachetime.get(Calendar.HOUR_OF_DAY) != Calendar.getInstance().get(Calendar.HOUR_OF_DAY); 
    }
    
    
} 
