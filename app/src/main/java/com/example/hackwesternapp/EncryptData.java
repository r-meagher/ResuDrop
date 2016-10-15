package com.example.hackwesternapp;

class EncryptData {
    private String name;
    private String key;
    private String id;

    EncryptData(String name, String key, String id) {
        this.key = key;
        this.id = id;
    }

    String getName() { return name; }

    String getKey() {
        return key;
    }

    String getId() {
        return id;
    }
}
