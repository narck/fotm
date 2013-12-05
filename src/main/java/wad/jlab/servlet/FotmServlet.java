package wad.jlab.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.jlab.logic.EvaluatorService;
import wad.jlab.logic.TwitterEvaluator;



@Controller
public class FotmServlet {
    
    
    private final EvaluatorService twitter = new TwitterEvaluator();
    
    @RequestMapping("/css/{file}.css")
    public String serveCss(@RequestParam String file) {
        return file;
    }
    
    @RequestMapping("*")
    public String handleDefault(Model model) {
        model.addAttribute("message", twitter.giveResult());
        //twitter.giveResult()
        return "index";
    }
    
    @RequestMapping("/history")
    public String viewHistory() {
        return "";
    }
    
    @RequestMapping(value="/now.json", method=RequestMethod.GET)
    @ResponseBody 
    public String getJsonResult() {
        return twitter.giveResult();
    }
}