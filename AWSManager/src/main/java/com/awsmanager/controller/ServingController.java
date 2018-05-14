package com.awsmanager.controller;
import com.awsmanager.ThreadRunnable.RequestRunnable;
import com.awsmanager.model.*;
import com.awsmanager.service.DBService;
import com.awsmanager.service.DeploymentService;
import com.awsmanager.service.TensorflowService;
import com.awsmanager.service.TensorflowServingClientService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/serving")
public class ServingController {
    TensorflowServingClientService servingClientService = new TensorflowServingClientService();

    @RequestMapping(method = RequestMethod.GET)
    public String main(Model model, @ModelAttribute("servingInput") ServingData servingData) throws IOException {
//        String response = servingClientService.sendGet();
        if(servingData != null){
            model.addAttribute("servingInput", servingData);
            return "servingpage";
        }
        model.addAttribute("servingInput", new ServingData());
        return "servingpage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String main(@Valid @ModelAttribute("servingInput") ServingData servingData, BindingResult result, final RedirectAttributes redirectAttributes) throws IOException {
        if(result.hasErrors()){
            return "servingpage";
        }
        String numberOutput = servingClientService.sendPostServingData(servingData);
        try {
            ClassLabels classLabels = new ClassLabels();
            String output = classLabels.getLabel(servingData.getModelName(), Integer.parseInt(numberOutput));
            servingData.setResult(output);
        }
        catch(Exception e){
            servingData.setResult(numberOutput);
        }
        finally {
            servingData.setImageUrl(servingData.getImageUrl());
            servingData.setModelName(servingData.getModelName());
            servingData.setModelUrl(servingData.getModelUrl());
            redirectAttributes.addFlashAttribute("servingInput", servingData);
            return "redirect:/serving";
        }
    }
}
