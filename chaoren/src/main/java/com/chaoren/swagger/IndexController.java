package com.chaoren.swagger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lichao on 2018/9/25.
 */
@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = {"/doc"})
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}
