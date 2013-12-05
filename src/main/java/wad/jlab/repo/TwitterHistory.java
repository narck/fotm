/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.jlab.repo;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import wad.jlab.data.TwitterCache;

/**
 *
 * @author narck
 */
@Repository
public class TwitterHistory {
    
    private ArrayList<TwitterCache> history = new ArrayList<>();
    
    public TwitterHistory() {}
    
    public void addToHistory(TwitterCache entry) {
        history.add(entry);
    }

    public ArrayList<TwitterCache> getHistory() {
        return history;
    }
    
    
}
