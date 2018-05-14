package com.awsmanager.ThreadRunnable;

import com.awsmanager.exception.TimeOutException;
import com.awsmanager.model.Request;
import com.awsmanager.service.TensorflowService;

import java.sql.SQLException;

public class RequestRunnable implements Runnable {
    private TensorflowService tensorflowService;
    private Request request;

    public RequestRunnable(Request request){
        this.tensorflowService = new TensorflowService();
        this.request = request;
    }
    @Override
    public void run() {
        try {
            if(this.request != null){
                tensorflowService.executeUserRequest(this.request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeOutException e) {
            e.printStackTrace();
        }
    }
}
