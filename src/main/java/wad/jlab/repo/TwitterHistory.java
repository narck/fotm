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
    
    private ArrayList<TwitterCache> history = new ArrayList<>();
    
    public TwitterHistory() {}
    
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
     * To keep the view clean, we reverse the actual history and truncate it to 25.
     * Return the original if the list is less than 25 in length.
     * @return List<TwitterCache> a truncated+reversed list of current history
     */
    public List<TwitterCache> getTruncated() {
        ArrayList<TwitterCache> listForServlet = new ArrayList<>();
        listForServlet.addAll(history);
        Collections.reverse(listForServlet);
        
        if (listForServlet.size() <= 5) {
            return listForServlet;
        } 
        return listForServlet.subList(0, 5);
    }
    
    
    /**
     * Get the newest cache. Null if the list is empty.
     * @return TwitterCache latest
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
        Map<String, Integer> counts = new HashMap<String, Integer>();

        for (TwitterCache t : history) {
            String tag = t.getHashtagWithHash();
            if (counts.containsKey(tag)) {
                counts.put(tag, counts.get(tag) + 1);
            } else {
                counts.put(tag, 1);
            }
        }
        
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
}
    
    
    
    
    
    

