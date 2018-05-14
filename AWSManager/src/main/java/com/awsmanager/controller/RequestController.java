package com.awsmanager.controller;
import com.awsmanager.ThreadRunnable.RequestRunnable;
import com.awsmanager.model.AwsInstance;
import com.awsmanager.model.Request;
import com.awsmanager.model.GlobalParameter;
import com.awsmanager.service.DBService;
import com.awsmanager.service.DeploymentService;
import com.awsmanager.service.TensorflowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/request")
public class RequestController {
    @RequestMapping(method = RequestMethod.GET)
    public String main(Model model){
        model.addAttribute("userInput", new Request());
        return "requestpage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String main(@Valid @ModelAttribute("userInput") Request request, BindingResult result) throws SQLException, InterruptedException {
        if(result.hasErrors()){
            return "requestpage";
        }
        RequestRunnable requestRunnable = new RequestRunnable(request);
        Thread thread = new Thread(requestRunnable);
        thread.start();
        //System.out.println("test thread");
        return "redirect:/data";
    }
}
