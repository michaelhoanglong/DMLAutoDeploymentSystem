package com.awsmanager.model;

public class AwsInstance {
    private int id;
    private String instanceId;
    private String instanceIPAddress;
    private int requestId;

    public AwsInstance(){}

    public AwsInstance(String instanceId, String instanceIPAddress, int requestId){
        this.instanceId = instanceId;
        this.instanceIPAddress = instanceIPAddress;
        this.requestId = requestId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceIPAddress() {
        return instanceIPAddress;
    }

    public void setInstanceIPAddress(String instanceIPAddress) {
        this.instanceIPAddress = instanceIPAddress;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
