package com.example.hackwesternapp;

import java.io.Serializable;

class ApplicantData implements Comparable<ApplicantData>, Serializable {
    private String name;
    private String email;
    private String id;
    private String url;
    private int rating;
    private boolean favourite;

    public ApplicantData(String name, String email) {
        this.name = name;
        this.email = email;
        this.favourite = false;
        setRating(0);
    }

    String getName() {
        return name;
    }

    void setName(String s) { name = s; }

    String getEmail() {
        return email;
    }

    void setEmail(String s) { email = s; }

    String getId() { return id; }

    void setId(String s) { id = s; }

    void setRating(int rating) {
        if (rating < 0) rating = 0;
        if (rating > 5) rating = 5;
        this.rating = rating;
    }

    int getRating() {
        return rating;
    }

    void toggleFavourite() {
        favourite = !favourite;
    }

    boolean isFavourite() {
        return favourite;
    }

    String getUrl() { return url; }

    void setUrl(String s) { url = s; }

    @Override
    public int compareTo(ApplicantData ad) {
        int c = Boolean.compare(favourite, ad.isFavourite());

        if (c != 0)
            return c;
        else
            return Integer.compare(rating, ad.getRating());
    }
}
