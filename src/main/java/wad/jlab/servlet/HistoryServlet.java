/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.jlab.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.jlab.service.EvaluatorService;
import wad.jlab.service.TwitterEvaluator;

/**
 *
 * @author narck
 */
@Controller
    public class HistoryServlet {

        private final EvaluatorService twitter = new TwitterEvaluator();

        @RequestMapping("/history")
        public String handleDefault(Model model) {
            model.addAttribute("history","active");
            model.addAttribute("message", twitter.giveResult());
            return "history";
        }
    }
