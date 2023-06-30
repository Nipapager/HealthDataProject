package com.healthdata;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReadCaloriesBurned readCaloriesBurned = new ReadCaloriesBurned();
        ReadBodyWeight readBodyWeight = new ReadBodyWeight();
        ReadBodyHeight readBodyHeight = new ReadBodyHeight();
        ReadHeartRate readHeartRate = new ReadHeartRate();
        ReadSpeed readSpeed = new ReadSpeed();
        ReadStepCount readStepCount = new ReadStepCount();
        ReadPhysicalActivity readPhysicalActivity = new ReadPhysicalActivity();
        GraphDBManager manager = new GraphDBManager();
        ShaclValidation shaclValidation = new ShaclValidation();
        Scanner scanner = new Scanner(System.in);
        try {
            readCaloriesBurned.processFiles();
            readBodyWeight.processFiles();
            readBodyHeight.processFiles();
            readHeartRate.processFiles();
            readSpeed.processFiles();
            readStepCount.processFiles();
            readPhysicalActivity.processFiles();
            TTLFilesMerger.mergeFiles();
            manager.createRepositoryAndUploadFiles();
            String RepoId;
            RepoId = manager.getRepositoryId();
            shaclValidation.validate(RepoId);
        } catch (IOException e) {
            System.err.println("Error while processing files: " + e.getMessage());
            e.printStackTrace();
        }
        scanner.close();
    }
}