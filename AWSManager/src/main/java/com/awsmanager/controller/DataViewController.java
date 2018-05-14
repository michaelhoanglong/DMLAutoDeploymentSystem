package com.awsmanager.controller;

import com.awsmanager.model.AwsInstance;
import com.awsmanager.model.Request;
import com.awsmanager.service.CommandDBService;
import com.awsmanager.service.DBService;
import com.awsmanager.service.DeploymentService;
import com.awsmanager.service.TensorflowService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/data")
public class DataViewController {
    DBService dbService = new DBService();
    CommandDBService commandDBService = new CommandDBService();
    DeploymentService deploymentService = new DeploymentService();
    TensorflowService tensorflowService = new TensorflowService();

    @RequestMapping(method = RequestMethod.GET)
    public String data(Model model) throws SQLException {
        ArrayList<Request> dataList = dbService.getRequestData();
        model.addAttribute("data",dataList);
        model.addAttribute("requestFormData", new Request());
        return "datapage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String terminateInstance(@ModelAttribute("requestData") Request request, BindingResult result) throws SQLException {
        // this only handle terminate button in datapage
        List<AwsInstance> awsInstanceList = dbService.getInstancesByRequestId(request.getId());
        for(AwsInstance instance : awsInstanceList){
            deploymentService.terminateInstance(instance.getInstanceId());
            commandDBService.deleteCommandByInstanceId(instance.getInstanceId());
        }
        dbService.deleteInstanceByRequestId(request.getId());
        dbService.deleteRequestData(request.getId());
        return "redirect:/data";
    }
}
