package wad.jlab.logic;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * config file or you want. Config is recommended and TBI
     */
public class TwitterEvaluator implements EvaluatorService {
    
    /**
     * You can set these via any method you deem sane. 
     * Recommend using configparser shipped with this snapshot.
     * Configparser tbi
     */
    
    private Twitter api = new TwitterFactory().getInstance();
    private TwitterCache cache;
    private final TwitterCrunch crunch = new TwitterCrunch();
    
    
    private String result;
    private boolean connection; // review
    
    
    public TwitterEvaluator()  {
        AccessToken at = new AccessToken("","",0L);
        
        api.setOAuthConsumer("", "");
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
     * Goes through a list of hashtags, uses the TwitterCrunch helper class
     * to define tweet density for every hashstag.
     * If the results are too similar, TBI is the HackerNews parser service
     * + entropy if need be :)
     */
    @Override
    public void evaluate() {
        
        /**
         * Flow
         * Get a list of tweets
         * Get their respective scores
         * Let crunch decide the result
         * Save into TwitterCache
         * */
        SortedMap<String, List<Status>> twidder = new TreeMap<>();
        
        // not final format, just testing : )
        try {
            //twidder.put("#mongodb", getTweets("#mongodb"));
            twidder.put("#redis", getTweets("#redis"));
            twidder.put("#cassandra", getTweets("#cassandra"));
            twidder.put("#mysql", getTweets("#mysql"));
            twidder.put("#couchdb", getTweets("#couchdb"));
            String keka = crunch.crunchTrendingTag(twidder);
            
            this.cache = new TwitterCache(keka, twidder.get(keka));
            
        } catch (TwitterException ex) { 
            // consider placing empty cache to avoid crashes
            this.cache = new TwitterCache("nothing found :I", null);
            Logger.getLogger(TwitterEvaluator.class.getName()).log(Level.SEVERE, null, ex); //review
        }
              
    }
    
    /**
     * Gets the most recent 100 (or less) tweets for any given hashtag.
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

    
    //review
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
