package wad.jlab.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.jlab.service.EvaluatorService;
import wad.jlab.service.TwitterEvaluator;



@Controller
public class FotmServlet {
    
    
    private final EvaluatorService twitter = new TwitterEvaluator();
    
    
    @RequestMapping("/")
    public String handleDefault(Model model) {
        model.addAttribute("now", "active");
        model.addAttribute("message", twitter.giveResult());
        //twitter.giveResult()
        return "index";
    }
    
    
    @RequestMapping(value="/now.json", method=RequestMethod.GET)
    @ResponseBody 
    public String getJsonResult() {
        return twitter.giveResult();
    }
}