package wad.jlab.service;

/**
 *
 * @author narck
 * 
 * EvaluatorServices simply provide some string to any user classes. 
 * By this setting, using a privately scoped result is recommended.
 */
public interface EvaluatorService {
    
    void evaluate();
    String giveResult();
}
