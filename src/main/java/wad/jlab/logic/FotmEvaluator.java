/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.jlab.logic;

/**
 *
 * @author narck
 */

public class FotmEvaluator implements EvaluatorService{
    
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
    private final String baseUrl = "http://twitter.com/";

    public String getPage(String url) {
        return "";
    }
    
    public int countMentions(String d) {
        return 0;
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
