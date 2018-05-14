package com.awsmanager.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Request {
    private int id;
    @NotBlank(message = "Model name cannot be emptied!")
    private String requesterName;
    @NotBlank(message = "Requester name cannot be emptied!")
    private String modelName;
    @NotNull (message = "Number of Worker cannot be emptied!")
    @Min(1)
    private int numberOfWorker;
    @NotNull (message = "Number of PS cannot be emptied!")
    @Min(1)
    private int numberOfPS;
    private float executionTime;
    private float accuracy;
    private String status;
    private String downloadUrl;
    private String trainingAlgorithm;
    private String dataPreprocessing;
    private String additionalInfo;

    public String getDataPreprocessing() {
        return dataPreprocessing;
    }

    public void setDataPreprocessing(String dataPreprocessing) {
        this.dataPreprocessing = dataPreprocessing;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getTrainingAlgorithm() {
        return trainingAlgorithm;
    }

    public void setTrainingAlgorithm(String trainingAlgorithm) {
        this.trainingAlgorithm = trainingAlgorithm;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request(){

    };
    public Request(int id, String modelName, int numberOfWorker, int numberOfPS, String trainingAlgorithm, String dataPreprocessing, String additionalInfo){
        this.id = id;
        this.modelName = modelName;
        this.numberOfPS = numberOfPS;
        this.numberOfWorker = numberOfWorker;
        this.executionTime = 0.0f;
        this.accuracy = 0.0f;
        this.trainingAlgorithm = trainingAlgorithm;
        this.dataPreprocessing = dataPreprocessing;
        this.additionalInfo = additionalInfo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;

    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getNumberOfWorker() {
        return numberOfWorker;
    }

    public void setNumberOfWorker(int numberOfWorker) {
        this.numberOfWorker = numberOfWorker;
    }

    public int getNumberOfPS() {
        return numberOfPS;
    }

    public void setNumberOfPS(int numberOfPS) {
        this.numberOfPS = numberOfPS;
    }

    public float getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(float executionTime) {
        this.executionTime = executionTime;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
