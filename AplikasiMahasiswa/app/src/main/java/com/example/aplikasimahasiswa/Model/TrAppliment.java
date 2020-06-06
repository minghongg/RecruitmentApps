package com.example.aplikasimahasiswa.Model;

public class TrAppliment {
    public String getNIM() {
        return NIM;
    }

    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getApplimentStatus() {
        return applimentStatus;
    }

    public void setApplimentStatus(String applimentStatus) {
        this.applimentStatus = applimentStatus;
    }

    public int getApplimentID() {
        return applimentID;
    }

    public void setApplimentID(int applimentID) {
        this.applimentID = applimentID;
    }

    private String NIM;
    private String jobID;
    private String applimentStatus;
    private int applimentID;
}
