package com.mx.beeva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "forward:/index.jsp";
    }

    @RequestMapping(value = { "/welcome**", "/hello**" }, method = RequestMethod.GET)
    public ModelAndView hello() {
        ModelAndView model = new ModelAndView("hello");
        model.addObject("title", "CircusTalent");
        model.addObject("message", "Esta es una pagina publica");
        return model;
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView model = new ModelAndView("admin");
        model.addObject("title", "CircusTalent");
        model.addObject("message", "Esta es una pagina protegida, solo para usuarios autenticados");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView("login");
        if (error != null) {
            model.addObject("error", "Usuario o contrasena incorrectos");
        }
        if (logout != null) {
            model.addObject("msg", "Has cerrado sesion correctamente");
        }
        return model;
    }
}
