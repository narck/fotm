/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.jlab.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import wad.jlab.data.TwitterCache;
import wad.jlab.repo.TwitterHistory;


/**
 * Helping thread. Checks whether there is need to update cache, and updates 
 * if there is. Takes the object pointers from the servlet as an argument and updates them.
 */
public class UpdaterThread implements Runnable {

    /**
     * The servlet's history and api handler
     */
    private TwitterHistory history;
    private EvaluatorService twitter;
    
    /**
     * Pass the servlet's history and api handler here 
     * @param history
     * @param twitter 
     */
    public UpdaterThread(TwitterHistory history, EvaluatorService twitter) {
        this.history=history;
        this.twitter=twitter;
    }
    
    
    @Override
    public void run() {
        while(true) {
            if (history.getLatest().hasTimedOut()) {
                twitter.evaluate();
                history.addToHistory(new TwitterCache(twitter.giveResult()));
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UpdaterThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    
    }
       
}
