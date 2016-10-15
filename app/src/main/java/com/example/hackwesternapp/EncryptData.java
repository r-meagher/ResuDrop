package com.example.hackwesternapp;

class EncryptData {
    private String companyName;
    private String applicantName;
    private String key;
    private String id;

    EncryptData(String companyName, String applicantName, String key, String id) {
        this.companyName = companyName;
        this.applicantName = applicantName;
        this.key = key;
        this.id = id;
    }

    String getCompanyName() { return companyName; }

    String getApplicantName() { return applicantName; }

    String getKey() {
        return key;
    }

    String getId() {
        return id;
    }
}
