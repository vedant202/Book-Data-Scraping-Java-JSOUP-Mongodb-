package com.scrape_projects.Model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * BookModel
 */
public class BookModel {
    private String title;
   

    private String imageUrl;
    private String rating;
    private double price;
    public void setPrice(double price) {
        this.price = price;
    }

    private boolean inStock;
    private boolean isDeleted;
    private LocalDateTime time = LocalDateTime.now();
    private String desc;
    
    // private String 
    public String getTitle(){
        return title;
    }
    public BookModel(){

    }

    public BookModel(String title, String imageUrl, String rating, int price, boolean inStock, boolean isDeleted,
            LocalDateTime time, String desc) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.price = price;
        this.inStock = inStock;
        this.isDeleted = isDeleted;
        this.time = time;
        this.desc = desc;
    }

    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = this.time.format(formatter);
        return formattedDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BookModel [title=" + title + ", imageUrl=" + imageUrl + ", rating=" + rating + ", price=" + price
                + ", inStock=" + inStock + ", isDeleted=" + isDeleted + ", time=" + time + ", desc=" + desc + "]";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // public LocalDateTime getTime() {
    //     return time;
    // }

    // public void setTime(LocalDateTime time) {
    //     this.time = time;
    // }

    public void setTitle(String title){
        this.title = title;
    }
    

    
}