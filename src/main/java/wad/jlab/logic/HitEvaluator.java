/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.jlab.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


    /**
     * Evaluator service with no predetermined functionality - although 
     * easily extendable to parse, if needed at all, HTML, since JSoup is packaged.
     *
     */

public class HitEvaluator implements EvaluatorService {
    
    public HitEvaluator() {
    
    }
    private final String[] databases = {
        "mongodb",
        "couchdb",
        "mariadb", 
        "postgresql",
        "mysql",
        "rethinkdb",
        "cassandra",
        "hadoop"
        };
    private String baseUrl = "http://twitter.com/";

    public String getPage(String url) {
        return "";
    }
    
    public int countMentions(String d) {
        return 0;
    }    
    
    public boolean setUrl(String newUrl) {
        
        
        Pattern p = Pattern.compile("^http://.*(.fi|.com)");
        Matcher m = p.matcher(newUrl);    
        if (m.matches()) {
        return true;
        };
        
        return false;
    }
    
    @Override
    public void evaluate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String giveResult() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
