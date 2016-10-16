package com.example.hackwesternapp;

class EncryptData {
    private String companyName;
    private String applicantName;
    private String id;

    EncryptData(String companyName, String applicantName, String id) {
        this.companyName = companyName;
        this.applicantName = applicantName;
        this.id = id;
    }

    String getCompanyName() { return companyName; }

    String getApplicantName() { return applicantName; }

    String getId() {
        return id;
    }
}
