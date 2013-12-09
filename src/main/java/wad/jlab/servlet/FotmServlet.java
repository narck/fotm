package wad.jlab.servlet;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.jlab.data.TwitterCache;
import wad.jlab.repo.TwitterHistory;
import wad.jlab.service.EvaluatorService;
import wad.jlab.service.TwitterEvaluator;


/*
 * The default controller. Handles all 3 pages.
 */
@Controller
public class FotmServlet {
    
    
    private final TwitterHistory history = new TwitterHistory();
    private final EvaluatorService twitter = new TwitterEvaluator();
    
    /**
     * Mapping for the "now" page. If cache has timed out, fetches a new result with 
     * TwitterEvaluator. Use setCache() method 
     * @see setNewCache()
     * @param model
     * @return path to a .jsp view
     */
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String getNow(Model model) {
        
        if (history.getHistory().isEmpty()) {
            String result = setNewCache().getHashtag();
            model.addAttribute("message", result);
        } else if (history.getLatest().hasTimedOut()){
            String result = setNewCache().getHashtag();
            model.addAttribute("message", result); }
        else {
            model.addAttribute("message", history.getLatest().getHashtag());
        }
        
        
        
        model.addAttribute("now", "active");
        return "index";
    }
    
    
    /**
     * Helper method to avoid copypasting while setting caches.
     * @return TwitterCache returns the saved cache.
     */
    public TwitterCache setNewCache() {
            twitter.evaluate();
            TwitterCache newcache = new TwitterCache(twitter.giveResult());
            history.addToHistory(newcache);
            return newcache;
    }
    
    
    /**
     * Controller for the 
     * @param model
     * @return path to a .jsp view
     */
    @RequestMapping(value="/history", method=RequestMethod.GET)
        public String getHistory(Model model) {
            TwitterCache month = history.getMonthsTrending();
            List<TwitterCache> hist = history.getTruncated(); 
            if (month==null) {
                model.addAttribute("month", "oresult");
            } else {
                model.addAttribute("month", history.getMonthsTrending().getHashtag());    
            }
            
            model.addAttribute("histItems", hist);
            
            
            //history.getMonthsTrending();
            model.addAttribute("history","active");
            return "history";
        }
    
    @RequestMapping(value="/about", method=RequestMethod.GET)
        public String getAbout(Model model) {
            model.addAttribute("about","active");
            return "about";
        }
    
    // implement json
    @RequestMapping(value="/now.json", method=RequestMethod.GET)
    @ResponseBody 
    public String getJsonNow() {
        return twitter.giveResult();
    }
    
    @RequestMapping(value="month.json", method=RequestMethod.GET)
    @ResponseBody
    public String getJsonMonth() {
        return twitter.giveResult();
    }
    
    
    
}