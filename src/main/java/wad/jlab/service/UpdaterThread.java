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
 *
 * @author narck
 */


public class UpdaterThread implements Runnable {

    private TwitterHistory history;
    private EvaluatorService twitter;
    
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
