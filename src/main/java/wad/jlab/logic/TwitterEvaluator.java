package wad.jlab.logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Ini;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import wad.jlab.data.TwitterCache;

    /**
     * Evaluator service with Twitter integration. Uses twitter4j library for functionality.
     * The class evaluates the 1. trending hashtag of the month, and can return it for later usage.
     * Use string returns.
     *
     * Notice: parameters are not mandatory. You can set them via constructor, 
     * config file or you want. Config is recommended.
     * @param ckey Twitter API Consumer key
     * @param csecret Twitter API Consumer secret
     */
public class TwitterEvaluator implements EvaluatorService {
    
    /**
     * You can set these via any method you deem sane. 
     * Recommend using configparser shipped with this snapshot.
     * Configparser tbi
     */
    
    private Twitter api = new TwitterFactory().getInstance();
    private TwitterCache cache;
    
    
    private String result;
    private boolean connection; // review
    
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;
    
    public TwitterEvaluator()  {
        AccessToken at = new AccessToken("key","key",0L);
        
        api.setOAuthConsumer("key", "key");
        api.setOAuthAccessToken(at);
    }
    
    
    /**
     * Helper method to determine if connection to service is available.
     * Notice that this is not required in the interface. This is purely
     * to determine TwitterEvaluator functionality. As such, twitter.com
     * queried by default.
     * @param url
     * @return 
     */
    public boolean checkConnection(String url) {
        
        try {
            InetAddress byUrl = InetAddress.getByName(url);
            return byUrl.isReachable(500);
        } catch (IOException e){
            e.toString(); // Use default error, pass
            return false;
        }
    }
    
    /**
     * Interface compliant method. Here we start the evaluation chain, 
     * and determine our class return value.
     * 
     * Goes through a list of #hashtags, uses the getInterval() helper
     * method to define tweet density for every hashstag, and compares them.
     * If the results are too similar, TBI is the HackerNews parser service
     * + entropy if need be :)
     */
    @Override
    public void evaluate() {
        
        /**
         * Flow
         * Get a list of tweets
         * Get their respective scores
         * Let algo decide the result
         * Save into TwitterCache
         * */
        
        try {
            this.cache = new TwitterCache("#mongodb", getTweets("#mongodb")); // not final format, just to see if works
        } catch (TwitterException ex) {
            Logger.getLogger(TwitterEvaluator.class.getName()).log(Level.SEVERE, null, ex); //review
        }
              
    }
    
    /**
     * Gets the most recent 100 tweets for any given hashtag.
     * Twitter4j stores a list of tweets in a QueryResult-instance, available through
     * getTweets().
     * @param hashtag The hashtag to query
     * @return Returns the list of 100 most recent tweets
     * @throws TwitterException 
     */
    public List<Status> getTweets(String hashtag) throws TwitterException {
        Query query = new Query(hashtag);
        query.setCount(100);
        
        QueryResult result = api.search(query);
        
        List<Status> tweets = result.getTweets();
        
        return tweets;
    }
    /**
     * Gets the datescore for a hashtag.
     * Since the most popular tags easily get their full 100 tweets in ~3 days,
     * we can count the days of month together to get an indicator which has the
     * most recent tweets per 100 tweet range. If two or more hashtags hit the 
     * top possible score (that means 100 tweets on the day of query, ie. all tweets
     * on the same day), fallback to getTimeScore().
     * @param tagTweets a List<Status> you can get with getTweets().
     * @return Integer of the score. Total maximum is 3100.
     */
    public int getDateScore(List<Status> tagTweets) {
        
        int score = 0;
        
        Calendar cal = Calendar.getInstance();
        for (Status status : tagTweets) {
            cal.setTime(status.getCreatedAt());
            score+=cal.get(Calendar.DAY_OF_MONTH);
        }
        
        return score;
    }

    /**
     * TBI. Returns a time based score for a list of tweets.
     * @param tagTweets
     * @return 
     */
    public int getTimeScore(List<Status> tagTweets) {
        return 0;
    }


    public boolean isConnection() {
        return connection;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    
    /**
     * Interface compliant method.
     * Returns the current cached result.
     * If cache isn't set yet OR if cache has reached timeout
     * it will cache a new result. Otherwise just increments the cacheTime.
     * @return The current result in a String format.
     */
    @Override
    public String giveResult() {
        if (this.cache==null || this.cache.getCacheTimeout()==this.cache.getCacheTime()) {
            evaluate();
        } else {
            this.cache.incrementCacheTime();
        }
        
        return this.cache.getHashtag();
    }
    
}
