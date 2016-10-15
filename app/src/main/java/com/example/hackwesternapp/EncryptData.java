package com.example.hackwesternapp;

class EncryptData {
    private String key;
    private String id;

    EncryptData(String key, String id) {
        this.key = key;
        this.id = id;
    }

    String getKey() {
        return key;
    }

    String getId() {
        return id;
    }
}
