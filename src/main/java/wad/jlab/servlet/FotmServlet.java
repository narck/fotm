package wad.jlab.servlet;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.jlab.data.TwitterCache;
import wad.jlab.repo.TwitterHistory;
import wad.jlab.service.EvaluatorService;
import wad.jlab.service.TwitterEvaluator;
import wad.jlab.service.UpdaterThread;


/**
 * The default controller. Handles all 3 pages.
 * Repository/Cache handling and utilizing the services are responsibilities of this class.
 */
@Controller
public class FotmServlet {
    
    /**
     * 
     */
    private boolean threadStarted = false;
    /**
     * The repository in which we cache our results.
     */
    private final TwitterHistory history = new TwitterHistory();
    
    /**
     * A service which gives us results in string form.
     */
    private final EvaluatorService twitter = new TwitterEvaluator();
    
    private final UpdaterThread thread = new UpdaterThread(history, twitter);
    
    /**
     * Mapping for the "now" page. If cache has timed out, fetches a new result with 
     * TwitterEvaluator. Use setCache() method for saving new caches to the repository.
     * 
     * In general, checks if the history is empty, and caches a new result everytime a 
     * cache timeout occurs.
     * 
     * Note that an "oresult" string is passed to the model if errors occur within core logic.
     * 
     * @see setNewCache()
     * @param model
     * @return path to a .jsp view
     */
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String getNow(Model model) {
        System.out.println("get initted");
        if (!threadStarted) {
            System.out.println("starting thread");
            setNewCache().getHashtag();
            System.out.println("got result");
            threadStarted=true;
            System.out.println("boolean set");
            Runnable r = thread;
            Thread thread = new Thread(r);
            thread.start();
        }
        
        System.out.println("thread started");
        
        model.addAttribute("message", history.getLatest().getHashtag());
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
     * Controller for the history view. 
     * Check first if there's a new cache object. Passes an "oresult" to the model
     * if it finds an empty repo.
     * @param model
     * @return path to a .jsp view
     */
    @RequestMapping(value="/history", method=RequestMethod.GET)
        public String getHistory(Model model) {
            TwitterCache month = history.getMonthsTrending();
            List<TwitterCache> hist = history.getTruncated(); 
            if (month==null) {
                model.addAttribute("month", "oresult");
                return "history";
            }
            
            
            model.addAttribute("month", history.getMonthsTrending().getHashtag());  
            model.addAttribute("histItems", hist);
            
            
            //history.getMonthsTrending();
            model.addAttribute("history","active");
            return "history";
        }
    
    
    /**
     * Controller for the about page.
     * Just activates the bootstrap link.
     * @param model
     * @return .jsp view
     */
    @RequestMapping(value="/about", method=RequestMethod.GET)
        public String getAbout(Model model) {
        
            model.addAttribute("about","active");
            return "about";
        }
    
    /**
     * REST mapping for getting Fotn
     * @return FOTN as a String
     */
    @RequestMapping(value="/now.json", method=RequestMethod.GET)
    @ResponseBody 
    public String getJsonNow() {
        return history.getLatest().getHashtagWithHash();
    }
    /**
     * REST mapping for getting Fotm
     * @return FOTM as a String
     */
    @RequestMapping(value="/month.json", method=RequestMethod.GET)
    @ResponseBody
    public String getJsonMonth() {
        return history.getMonthsTrending().getHashtagWithHash();
    }
}