package com.awsmanager.model;

public class ClassLabels {
    private String[] mnist = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    private String[] cifar10 = new String[]{"airplane", "automobile", "bird", "cat", "deer", "dog", "frog", "horse", "ship", "truck"};
    public ClassLabels(){}
    public String getLabel(String dataType, int pos){
        switch (dataType){
            case "Mnist": return mnist[pos];
            case "Cifar10": return cifar10[pos];
        }
        return null;
    }
}
