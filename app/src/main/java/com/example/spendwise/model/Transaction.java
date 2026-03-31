package com.example.spendwise.model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int id;
    private String title;
    private String category;
    private double price;
    private String date;
    private String note;

    public Transaction() {}

    public Transaction(int id, String title, String category, double price, String date, String note) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.date = date;
        this.note = note;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
