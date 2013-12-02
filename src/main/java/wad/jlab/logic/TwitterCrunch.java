/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wad.jlab.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    
    public TwitterCrunch() {
    
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
     * TBI. Returns a time based score for a list of tweets.
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
     * The "main" method. Uses the helper methods to determine the result as String.
     * @param HashMap<String, List<Status>> A Map of hashtags and their latest tweets.
     * 
     */
    public String crunchTrendingTag(SortedMap<String, List<Status>> hashtagsAndTweets) {
        SortedMap<String, Integer> dateScores = new TreeMap<>();
        SortedMap<String, Integer> timeScores = new TreeMap<>();
        String winner = "notfound"; //fallback

        for (String hashtag : hashtagsAndTweets.keySet()) {
            List<Status> tweets = hashtagsAndTweets.get(hashtag);
            removeOlderTweets(tweets); // invoke here to enable method testability
            dateScores.put(hashtag, getHashtagDateScore(tweets));
            timeScores.put(hashtag, getHashtagTimeScore(tweets));
        }
        
        if (checkIfDateCollision(hashtagsAndTweets)) {
            //add: remove hashtags lower than maximum
            removeNonMaxScoreTags(hashtagsAndTweets, getMaxDateScore(dateScores));
            winner = compareHashtags(timeScores); 
        } else {
            winner = compareHashtags(dateScores);
        }
        
        return winner;
    }
    
    
    /*
     * 
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
     * javadoc
     */
    public boolean checkIfDateCollision(Map<String, List<Status>> hashtagsAndTweets) {
        Set uniqueScores = hashtagsAndTweets.entrySet();
        return hashtagsAndTweets.size()==uniqueScores.size();
    }
    
    /*
     * javadoc
     */
    public String compareHashtags(SortedMap<String, Integer> scores) {
        
        String leader = scores.firstKey();
        
        for (String hashtag : scores.keySet()) {
            if (scores.get(hashtag) > scores.get(leader)) {
                if (scores.get(hashtag) > scores.get(leader)) {
                    leader = hashtag;
                }
            }
        }
        return leader;
    }
    
    
    /*
     * 123
     */
    public Map<String, List<Status>> removeNonMaxScoreTags(Map<String, List<Status>> hashtagsAndTweets, int max) {
        
        ArrayList<String> deletables = new ArrayList<>(); 
        for (String hashtag : hashtagsAndTweets.keySet()) {
            if (getHashtagDateScore(hashtagsAndTweets.get(hashtag))!=max) {
                deletables.add(hashtag);
            }
        }
        for (String deleteTag : deletables) {
            hashtagsAndTweets.remove(deleteTag);
        }
        return hashtagsAndTweets;
    
    }
}