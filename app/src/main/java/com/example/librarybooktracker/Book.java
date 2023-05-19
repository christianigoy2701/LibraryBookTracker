package com.example.librarybooktracker;

public abstract class Book {
    private String bookCode;
    private String title;
    private String author;
    private int daysBorrowed;
    private boolean isBorrowed;
    private boolean isRegular;

    public Book(String bookCode, String title, String author, boolean isRegular){
        this.bookCode = bookCode;
        this.title = title;
        this.author = author;
        this.daysBorrowed = 0;
        this.isBorrowed = false;
        this.isRegular = isRegular;
    }

    public String getBookCode() {
        return bookCode;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }

    public void setDaysBorrowed(int daysBorrowed) {
        this.daysBorrowed = daysBorrowed;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public boolean isRegular() {
        return isRegular;
    }
    public double calculatePrice(){
        double price = 0;
        if(isRegular){
            price = 20 * daysBorrowed;
        }
        else{
            if(daysBorrowed <= 7){
                price = 50 * daysBorrowed;
            }
            else{
                price = 50 * 7 + (daysBorrowed - 7) * 75;
            }
        }
        return price;
    }
}
