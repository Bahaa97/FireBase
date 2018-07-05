package com.task.senior.Model;

public class Item {

    private String key;
    private String name;
    private String description;
    private int price;
    private double rate;

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Item setPrice(int price) {
        this.price = price;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public Item setRate(double rate) {
        this.rate = rate;
        return this;
    }

    public String getKey() {
        return key;
    }

    public Item setKey(String key) {
        this.key = key;
        return this;
    }
}
