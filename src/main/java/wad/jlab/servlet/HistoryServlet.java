/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.jlab.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.jlab.logic.EvaluatorService;
import wad.jlab.logic.TwitterEvaluator;

/**
 *
 * @author narck
 */
public class HistoryServlet {
    
    @Controller
    public class FotmServlet {

        private final EvaluatorService twitter = new TwitterEvaluator();

        @RequestMapping("*")
        public String handleDefault(Model model) {
            model.addAttribute("history", twitter.giveResult());
            return "index";
        }
    }
}
