package com.example.aplikasimahasiswa.Model;

public class Perusahaan {
    public String getPerusahaanID() {
        return PerusahaanID;
    }

    public void setPerusahaanID(String perusahaanID) {
        PerusahaanID = perusahaanID;
    }

    private String PerusahaanID;

    public String getPerusahaanName() {
        return PerusahaanName;
    }

    public void setPerusahaanName(String perusahaanName) {
        PerusahaanName = perusahaanName;
    }

    public String getPerusahaanEmail() {
        return PerusahaanEmail;
    }

    public void setPerusahaanEmail(String perusahaanEmail) {
        PerusahaanEmail = perusahaanEmail;
    }

    public String getPerusahaanAddress() {
        return PerusahaanAddress;
    }

    public void setPerusahaanAddress(String perusahaanAddress) {
        PerusahaanAddress = perusahaanAddress;
    }

    private String PerusahaanName;
    private String PerusahaanEmail;
    private String PerusahaanAddress;

    public String getPerusahaanPassword() {
        return PerusahaanPassword;
    }

    public void setPerusahaanPassword(String perusahaanPassword) {
        PerusahaanPassword = perusahaanPassword;
    }

    private String PerusahaanPassword;
}
