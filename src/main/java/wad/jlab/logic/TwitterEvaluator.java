package wad.jlab.logic;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

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
    
    private String result;
    private boolean connection; // review
    
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;
    private Twitter twitter = TwitterFactory.getSingleton();
    private QueryResult cache; //cache the results for some time
    
    public TwitterEvaluator() {
        AccessToken at = new AccessToken("key","key",0L);
        
        twitter.setOAuthConsumer("key", "key");
        twitter.setOAuthAccessToken(at);
    }
    
    
    //review
//    public TwitterEvaluator(String ckey, String csecret, ) {
//        this.consumerKey = ckey;
//        this.consumerSecret = csecret;
//    }
//    
    /**
     * Helper method to determine if connection to service is available.
     * Notice that this is not required in the interface. This is purely
     * to determine TwitterEvaluator functionality. As such, twitter.com
     * queried by default.
     */
    public boolean checkConnection(String url) {
        
        try {
            InetAddress byUrl = InetAddress.getByName(url);
            if (byUrl.isReachable(500)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
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
        
        // Use a hashmap to store density
        // NOTE: Only need to use locally for the moment
        HashMap<String, Integer> hashtagDensity = new HashMap<String, Integer>();
        
        
        // TBI
        
        // Due to Twitter API being too restrictive, and due to
        // project scope the Twitter Stream API is a bit overkill.
        // Here we parse the latest 100 tweets, and count the interval
        // (or other viable method, consider a wildcard for slower streams
        
        
        Query query = new Query("#hadoop");
        query.setCount(100);
        try {
            QueryResult result = twitter.search(query);
            this.result = result.getTweets().get(0).getText(); //dummy return to check if its working
        } catch (TwitterException ex) {
            Logger.getLogger(TwitterEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getInterval() {
        // returns the interval of tweets
    }
    
    

    public boolean isConnection() {
        return connection;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    @Override
    public String giveResult() {
        evaluate(); // call evaluate first to get
        return result;
    }
    
}
