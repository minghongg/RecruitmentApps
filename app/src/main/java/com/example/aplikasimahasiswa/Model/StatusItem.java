package com.example.aplikasimahasiswa.Model;

public class StatusItem {
    private String CompanyName_Text;
    private String Status_Text;
    private int sort;
    private String jobName;

    private String jobID;

    public StatusItem(String CompanyName, String Status){
        CompanyName_Text = CompanyName;
        Status_Text = Status;
            if(Status.equals("Pending")){
            sort=1;
        }else if(Status.equals("Accepted")){
            sort=0;
        }else{
            sort=2;
        }
    }

    public String CompanyName(){
        return CompanyName_Text;
    }

    public String Status(){
        return Status_Text;
    }

    public int Sort() {return sort;}

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }
}
