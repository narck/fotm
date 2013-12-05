/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * Just instantiate and use.
 * @author narchie
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
     * @param tagTweets
     * @return 
     */
    public int getHashtagTimeScore(List<Status> tagTweets) {
        
        int score = 0;
        Calendar cal = Calendar.getInstance();
        
        for (Status status : tagTweets) {
            cal.setTime(status.getCreatedAt());
            score+=cal.get(Calendar.HOUR_OF_DAY);
            //score+=cal.get(Calendar.MINUTE/2); // review
        }
        
        return score;
    }

    /*
     * Remove all tweets from this month.
     * To get reliable results, suppress any other months than the ongoing one.
     * 
     * @param List<Status> tagTweets A list of Status objects.
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
    
    
    /*
     * Generic method to remove any tags from the scores that are do not collide
     * with the maximum value,
     * 
     * @param Map<String, Integer> scores a generic Map score table.
     * @return Map<String, Integer> scores with low scores removed.
     */
    public Map<String, Integer> removeNonCollidingScores(Map<String, Integer> scores) {
        int max = findMaxScore(scores.values());
       
        List<String> deletables = new ArrayList<>();
        for (String hashtag : scores.keySet()) {
            if (scores.get(hashtag)!=max) {
                deletables.add(hashtag);
            }
        }
        for (String hashtag : deletables) {
            scores.remove(hashtag);
        }
        
        return scores;
    }
    
    /*
     * Special helper for the general flow.
     * We need to remove the lower daily tags to compare the correct timescores,
     * so we can use this method in the main crunchTrendingTag if there is collision in the
     * dateScores method.
     * 
     * @params
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
    
    public int findMaxScore(Collection<Integer> scores) {
        int max = 0;
        
        for (Integer i : scores) {
            if (i>max) {
                max=i;
            }
        }    
        return max;
    }

    public boolean collisionsInScores(Map<String, Integer> dateScore) {
        Set uniqueScores = new HashSet<>(dateScore.values());
        return dateScore.size() != uniqueScores.size();
    }
    
    /*
     * The "main" method. Uses the helper methods to determine the result as String.
     * @param HashMap<String, List<Status>> A Map of hashtags and their latest tweets.
     * 
     */
    public String crunchTrendingTag(SortedMap<String, List<Status>> hashtagsAndTweets) {
        SortedMap<String, Integer> dateScores = new TreeMap<>();
        SortedMap<String, Integer> timeScores = new TreeMap<>();
        
        String winner = "notfound"; // you should not see this
        
        // Populate scores
        for (String hashtag : hashtagsAndTweets.keySet()) {
            List<Status> tweets = hashtagsAndTweets.get(hashtag);
            removeOlderTweets(tweets); // invoke here to enable testability
            dateScores.put(hashtag, getHashtagDateScore(tweets));
            timeScores.put(hashtag, getHashtagTimeScore(tweets));
        }
        System.out.println("Determining tag...");
        if (collisionsInScores(dateScores)) {
            System.out.println("Datescores: "+  dateScores);
            removeNonCollidingTimeScores(dateScores, timeScores);
            System.out.println("Removing lower scores from timescores...");
            System.out.println(timeScores);
            winner = compareHashtags(timeScores); 
        } else {
            System.out.println("No datescore collisions!");
            System.out.println(dateScores);
            winner = compareHashtags(dateScores);
        }
        
        return winner;
    }
    
    
    /*
     * DEPRECATED
     */
    public int getMaxDateScore(Map<String, Integer> scores) {
        Collection<Integer> scoreList = scores.values();
        int max = 0;
        for (Integer i : scoreList) {
            if (i>max) {
                max=i;
            }
        }
        
        return max;
    }
    
    /*
     * Compares score maps together. Takes the first value in treemap, and traverses
     * through the map comparing each scoremap to the leading score.
     * 
     * @param SortedMap<String, Integer> scores
     */
    public String compareHashtags(SortedMap<String, Integer> scores) {
//        
        Random r = new Random();
        String leader = scores.firstKey(); 
        
        if (collisionsInScores(scores)) {
            Set<String> keys = scores.keySet();
            String[] keysArray = new String[0];
            keysArray = keys.toArray(keysArray);
            String randomKey = keysArray[r.nextInt(keysArray.length) ];
            return randomKey;
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
}