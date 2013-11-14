package wad.jlab.logic;

import java.net.InetAddress;
import java.util.Calendar;

    /**
     * Evaluator service with Twitter integration. Uses twitter4j library for functionality.
     * The class evaluates the 1. trending hashtag of the month, and can return it for later usage.
     * Use string returns.
     *
     * Notice: parameters are not mandatory. You can set them via constructor, 
     * config file or you want. Config is recommended.
     * @param ckey Twitter API Consumer key
     * @param csecret Twitter API Consumer secret
     */
public class TwitterEvaluator implements EvaluatorService {
    
    /**
     * You can set these via any method you deem sane. 
     * Recommend using configparser shipped with this snapshot.
     */
    private String consumerkey;
    private String consumersecret;
    private String result = "asd";
    private Calendar currentMonth;
    private boolean connection; // review
    
    public TwitterEvaluator() {
        
    }
    
    public TwitterEvaluator(String ckey, String csecret) {
        this.consumerkey = ckey;
        this.consumersecret = csecret;
    }
    
    /**
     * Helper method to determine if connection to service is available.
     * Notice that this is not required in the interface. This is purely
     * to determine TwitterEvaluator functionality. As such, twitter.com
     * queried by default.
     */
    public boolean checkConnection(String url) {
        
        try {
            InetAddress byUrl = InetAddress.getByName(url);
            if (byUrl.isReachable(500)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.toString(); // Use default error, pass
            return false;
        }
    }
    
    /**
     * Interface compliant method. Here we start the evaluation chain, 
     * and determine our class return value.
     * Sees what (system) month it is, and evaluates Twitter feeds based on that.
     * TODO month resolvation reliably / unit testing / ...
     */
    @Override
    public void evaluate() {
        //Calendar.getInstance().get(Calendar.MONTH);
        
        if (checkConnection("http://twitter.com")) {
            //
        } else {
            //
        }
    }

    public boolean isConnection() {
        return connection;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    @Override
    public String giveResult() {
        return result;
    }
    
}
