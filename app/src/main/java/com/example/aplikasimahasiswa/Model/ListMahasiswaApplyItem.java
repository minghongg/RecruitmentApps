package com.example.aplikasimahasiswa.Model;

public class ListMahasiswaApplyItem {
    private String Name_Text;
    private String Job_Text;


    private String applimentID;
    private String Job_ID;

    private String nim;

    private String Status;

    public ListMahasiswaApplyItem(String name_Text, String job_Text){
        Name_Text = name_Text;
        Job_Text = job_Text;
    }

    public String getJob_ID() {
        return Job_ID;
    }

    public void setJob_ID(String job_ID) {
        Job_ID = job_ID;
    }

    public String nameApplyment(){
        return Name_Text;
    }

    public String JobApplyment(){
        return Job_Text;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
    public String getApplimentID() {
        return applimentID;
    }

    public void setApplimentID(String applimentID) {
        this.applimentID = applimentID;
    }
}
