package com.example.hackwesternapp;

class ApplicantData implements Comparable<ApplicantData> {
    private String name;
    private String email;
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

    String getEmail() {
        return email;
    }

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

    @Override
    public int compareTo(ApplicantData ad) {
        int c = Boolean.compare(favourite, ad.isFavourite());

        if (c != 0)
            return c;
        else
            return Integer.compare(rating, ad.getRating());
    }
}
