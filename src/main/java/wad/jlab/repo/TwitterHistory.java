package wad.jlab.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import wad.jlab.data.TwitterCache;

/**
 * A simple list repository to cache 100 latest results.
 * This is used to calculate the top trending tag of the month,
 * and to retrieve the most current cached result.
 */
@Repository
public class TwitterHistory {
    /**
     * The actual repository, a simple ArrayList object.
     */
    private ArrayList<TwitterCache> history = new ArrayList<>();
    
    public TwitterHistory() {}
    
    /**
     * Adds cache objects to the repo. Removes the first if limit is exceeded.
     * @param entry a TwitterCache object to add to repo.
     */
    public void addToHistory(TwitterCache entry) {
        history.add(entry);
        if (history.size()>100) {
            history.remove(0);
        }
    }
    
    
    public ArrayList<TwitterCache> getHistory() {
        return history;
    }
   
    
    /**
     * Truncated list for the servlet. 
     * To keep the view clean, we reverse the actual history and truncate it to 10.
     * Return the original if the list is less than 10 in length.
     * 
     * @return List<TwitterCache> a truncated+reversed list of current history
     */
    public List<TwitterCache> getTruncated() {
        ArrayList<TwitterCache> listForServlet = new ArrayList<>();
        listForServlet.addAll(history);
        Collections.reverse(listForServlet);
        
        if (listForServlet.size() <= 10) {
            return listForServlet;
        } 
        return listForServlet.subList(0, 10);
    }
    
    
    /**
     * Get the newest cache. Null if the list is empty.
     * The controller uses this null return to determine some of its logic.
     * 
     * @return TwitterCache latest cache object
     */
    public TwitterCache getLatest() {
        if (history.size()==0) {
            return null;
        } else {
            return history.get(history.size()-1);
        }
    }
    
    /**
     * Return the month's trending hashtag calculated from
     * the current cache.
     * @return TwitterCache this months trending hashtag
     */
    public TwitterCache getMonthsTrending() {
        Map<String, Integer> counts = generateCountMap();
        
        int max = 0;
        String winner = null;
        for (String tag : counts.keySet()) {
            if (counts.get(tag)>max) {
                max=counts.get(tag);
                winner=tag;
            }
        }
        if (winner!=null) {
            return new TwitterCache(winner);
        } else {
            return null;
        }
    }
    
    /**
     * Helper method to shorten getMonthsTrending().
     * @return Map<String, Integer> map of tag entry counts in the list
     */
    public Map<String, Integer> generateCountMap() {
        Map<String, Integer> counts = new HashMap<String, Integer>();

        for (TwitterCache t : history) {
            String tag = t.getHashtagWithHash();
            if (counts.containsKey(tag)) {
                counts.put(tag, counts.get(tag) + 1);
            } else {
                counts.put(tag, 1);
            }
        }
        return counts;
    }
}
    
    
    
    
    
    

