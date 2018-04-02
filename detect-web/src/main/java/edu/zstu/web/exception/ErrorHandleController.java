package edu.zstu.web.exception;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Aning
 */
@Controller
public class ErrorHandleController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping(value="/error")
    public String handleError(){
        return "404";
    }

}
