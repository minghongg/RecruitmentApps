package com.example.aplikasimahasiswa.Model;

import java.io.Serializable;

public class Vacancy implements Serializable {

    private String VacancyName;

    public String getVacancyID() {
        return VacancyID;
    }

    public void setVacancyID(String vacancyID) {
        VacancyID = vacancyID;
    }

    private String VacancyID;

    public String getVacancyEmail() {
        return VacancyEmail;
    }

    public void setVacancyEmail(String vacancyEmail) {
        VacancyEmail = vacancyEmail;
    }

    public String getVacancyAddress() {
        return VacancyAddress;
    }

    public void setVacancyAddress(String vacancyAddress) {
        VacancyAddress = vacancyAddress;
    }

    private String VacancyEmail;
    private String VacancyAddress;

    public String getJobID() {
        return JobID;
    }

    public void setJobID(String jobID) {
        JobID = jobID;
    }

    private String JobID;
    public String getVacancyName() {
        return VacancyName;
    }

    public void setVacancyName(String vacancyName) {
        VacancyName = vacancyName;
    }
}
