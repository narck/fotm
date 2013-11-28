package wad.jlab.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.jlab.logic.EvaluatorService;
import wad.jlab.logic.TwitterEvaluator;

@Controller
public class FotmServlet {
    
    private final EvaluatorService twitter = new TwitterEvaluator();
    
    @RequestMapping("*")
    public String handleDefault(Model model) {
        model.addAttribute("message", twitter.giveResult());
        return "index";
    }
}