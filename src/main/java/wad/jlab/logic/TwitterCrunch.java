
package wad.jlab.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import twitter4j.Status;

/**
 * A helper class for TwitterEvaluator. 
 * 
 * Takes a SortedMap<String, List<Status>> containing a String as a key,
 * and a list of tweets associated to it, and calculates which hashtag 
 * the best score.
 * 
 * The "main" method is crunchTrendingTag, which returns the value.
 */
public class TwitterCrunch {
    
    public TwitterCrunch() {}
    
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
    public int getHashtagDateScore(List<Status> tagTweets) {
        
        int score = 0;
        
        Calendar cal = Calendar.getInstance();
        for (Status status : tagTweets) {
            cal.setTime(status.getCreatedAt());
            score+=cal.get(Calendar.DAY_OF_MONTH);
        }
        
        return score;
    }

    /**
     * Computes the timescore of a list of tweets. This is done by simply adding 
     * the hours of day together. Note that usually other tweets than tweets
     * @param tagTweets a List<Status> you can get with getTweets().
     * @return Integer value of a score.
     */
    public int getHashtagTimeScore(List<Status> tagTweets) {
        int score = 0;
        Calendar cal = Calendar.getInstance();
        
        for (Status status : tagTweets) {
            cal.setTime(status.getCreatedAt());
            score+=cal.get(Calendar.HOUR_OF_DAY);
        }
        return score;
    }

    /**
     * Remove all tweets from this not this month.
     * To get reliable results, suppress any other months than the ongoing one.
     * 
     * @param tagTweets A list of Status objects.
     * @return The list without tweets from previous months.
     */
    public List<Status> removeOlderTweets(List<Status> tagTweets) {
        Calendar currentDay = Calendar.getInstance();
        Calendar statusDay = Calendar.getInstance();
        ArrayList<Status> deletables = new ArrayList();
        
        for (Status status : tagTweets) {
            statusDay.setTime(status.getCreatedAt());
            if (statusDay.get(Calendar.MONTH) != currentDay.get(Calendar.MONTH)) {
                deletables.add(status);
            }
        }
        for (Status status : deletables) {
            tagTweets.remove(status);
        }
        return tagTweets;
    }
    
    /**
     * Special helper for the general flow.
     * We need to remove the lower daily tags to compare the correct timescores,
     * so we can use this method in the main crunchTrendingTag if there is collision in the
     * dateScores method.
     * 
     * @param dateScores
     * @param timeScores
     * @return The timescore table with the non-colliding (lower) scores removed.
     */
    public Map<String, Integer> removeNonCollidingTimeScores(Map<String, Integer> dateScores, Map<String, Integer> timeScores) {
        int max = findMaxScore(dateScores.values());
       
        List<String> deletables = new ArrayList<>();
        for (String hashtag : dateScores.keySet()) {
            if (dateScores.get(hashtag)!=max) {
                deletables.add(hashtag);
            }
        }
        for (String hashtag : deletables) {
            timeScores.remove(hashtag);
        }
        return timeScores;
    }
    
    /**
     * Returns the highest value from a Collection.
     * 
     * @param scores
     * @return The highest value in the collection.
     */
    public int findMaxScore(Collection<Integer> scores) {
        int max = 0;
        for (Integer i : scores) {
            if (i>max) {
                max=i;
            }
        }    
        return max;
    }

    /**
     * Checks whether there are two identical dateScores in the flow by making a valueset.
     * @param dateScore
     * @return boolean value
     */
    public boolean collisionsInScores(Map<String, Integer> dateScore) {
        Set uniqueScores = new HashSet<>(dateScore.values());
        return dateScore.size() != uniqueScores.size();
    }
    
    /**
     * The "main" method. Uses the helper methods to determine the result as String.
     * Toggle comments to see them in the server log. They help a lot when debugging the fetched results.
     * 
     * NOTE that if you need syslogging, uncomment the indented system outs.
     * @param hashtagsAndTweets A Map of hashtags and their latest tweets.
     * @return A string of the most popular hashtag.
     */
    public String crunchTrendingTag(SortedMap<String, List<Status>> hashtagsAndTweets) {
        SortedMap<String, Integer> dateScores = new TreeMap<>();
        SortedMap<String, Integer> timeScores = new TreeMap<>();
        
        String winner = "notfound"; // you should not see this
        populateScores(dateScores, timeScores, hashtagsAndTweets);
        
            //System.out.println("Determining tag...");
        if (collisionsInScores(dateScores)) {
                //System.out.println("Datescores: "+  dateScores);
            removeNonCollidingTimeScores(dateScores, timeScores);
                //System.out.println("Removing lower scores from timescores...");
                //System.out.println(timeScores);
            winner = compareHashtags(timeScores); 
        } else {
                //System.out.println("No datescore collisions!");
                //System.out.println(dateScores);
            winner = compareHashtags(dateScores);
        }
        
        return winner;
    }
    
    /**
     * Compares score mappings together. Takes the first value in treemap, and traverses
     * through the map comparing each scoremap to the leading score.
     * 
     * @param scores
     * @return The string of the most trending tag.
     */
    public String compareHashtags(SortedMap<String, Integer> scores) {
        String leader = scores.firstKey();
        
        if (collisionsInScores(scores)) {
            return getRandomFromScores(scores);
        }
        int leadingScore = 0;
        for (String hashtag : scores.keySet()) {
            if (scores.get(hashtag) > scores.get(leader)) {
                if (scores.get(hashtag) > scores.get(leader)) {
                    leader = hashtag;
                    leadingScore = scores.get(hashtag);
                }
            }
        }
        return leader;
    }
    
    

    /**
     * Helper method to shorten the main method. 
     * @see crunchTrendingTag()
     * Simply populates the scoremaps given.
     * @param dateScores
     * @param timeScores
     * @param hashtagsAndTweets 
     * 
     */
    private void populateScores(SortedMap<String, Integer> dateScores, SortedMap<String, Integer> timeScores, SortedMap<String, List<Status>> hashtagsAndTweets) {
        // Populate scores
        for (String hashtag : hashtagsAndTweets.keySet()) {
            List<Status> tweets = hashtagsAndTweets.get(hashtag);
            removeOlderTweets(tweets); // invoke here to enable testability
            dateScores.put(hashtag, getHashtagDateScore(tweets));
            timeScores.put(hashtag, getHashtagTimeScore(tweets));
        }
    }

    /**
     * Helper method to shorten the compare 
     * @param scores
     * @return Random element from scoremap
     */
    private String getRandomFromScores(SortedMap<String, Integer> scores) {
            Random r = new Random();
            Set<String> keys = scores.keySet();
            String[] keysArray = new String[0];
            keysArray = keys.toArray(keysArray);
            String randomKey = keysArray[r.nextInt(keysArray.length) ];
            return randomKey;
    }
}