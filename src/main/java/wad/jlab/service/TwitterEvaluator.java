package wad.jlab.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
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
import wad.jlab.logic.TwitterCrunch;
import wad.jlab.repo.TwitterHistory;

    /**
     * Evaluator service with Twitter integration. Uses twitter4j library for functionality.
     * The class evaluates the 1. trending hashtag of the month, and can return it for later usage.
     * Use string returns.
     *
     * API keys are set in the default constructor 
     * via the apiconfig.ini located in src/main/resources/config/apiconfig.ini
     * Use it to set your keys
     */
public class TwitterEvaluator implements EvaluatorService {
    
    private Twitter api = new TwitterFactory().getInstance();
    private TwitterHistory history = new TwitterHistory();
    private String result;
    private final TwitterCrunch crunch = new TwitterCrunch();
    private final String[] queryHashtags =  {
        "#hadoop", 
        "#redis",
        "#mongodb",
        "#cassandra",
        "#postgresql",
        "#couchdb",
        "#mysql"};
    
    private boolean connection; // review
    
    
    /*
     * Default constructor. Use try-cactch to avoid bleed exception declaration
     */
    public TwitterEvaluator()  {
        
        Ini ini = new Ini();
        try {
            File config = new File("src/main/resources/config/apiconfig.ini");
            ini.load(new FileReader(config));
            Ini.Section consumer = ini.get("consumer");
            Ini.Section access = ini.get("access");
                
            AccessToken at = new AccessToken(access.get("token"),access.get("secret"),0L);
        
            api.setOAuthConsumer(consumer.get("key"), consumer.get("secret"));
            api.setOAuthAccessToken(at);
            
        } catch (Exception e) {
            System.out.println("Configuration not found");
        }
    }
    
    
    /**
     * Helper method to determine if connection to service is available.
     * Notice that this is not required in the interface. This is purely
     * to determine TwitterEvaluator functionality. As such, twitter.com
     * queried by default.
     * @param url
     * @return boolean 
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
     * 
     * Set the desired hashtags in the class attribute queryHashtags.
     * NOTE: The result is not semantic, so consider what tags you use if you want to 
     * query something specific!
     */
    @Override
    public void evaluate() {
        SortedMap<String, List<Status>> tweetMap = new TreeMap<>();
        
     
        try {
            for (String hashtag : queryHashtags) {
                tweetMap.put(hashtag, getTweets(hashtag));
            }
            String result = crunch.crunchTrendingTag(tweetMap);
            
            this.result = result;
            
        } catch (TwitterException ex) {
            this.result = "noresult";
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
     * Simply returns the latest result. Evaluation and caching have been moved to
     * TwitterHistory and controller level.
     * @return The current result in a String format.
     */
    @Override
    public String giveResult() {
        return this.result;
    }
}
