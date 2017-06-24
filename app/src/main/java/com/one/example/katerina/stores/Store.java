package com.one.example.katerina.stores;

/*** Created by cathr on 24/6/2017. ***/

public class Store {
    private int id, rating;
    private String legalName, storeCategory, contactPoint;


    public Store(int id, String legalName, String storeCategory, String contactPoint, int rating){
        this.id = id;
        this.legalName = legalName;
        this.storeCategory = storeCategory;
        this.contactPoint = contactPoint;
        this.rating = rating;
    }

    public int getId(){
        return id;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public String getContactPoint() {
        return contactPoint;
    }

    public int getRating() {
        return rating;
    }
}

