package com.scrape_projects;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scrape_projects.Model.BookModel;
import com.scrape_projects.MongoDBConnection.MongoDBCon;


// vedantPassword@123!!!
public class Main {

    public static void getAllBooks(){
        String url = "https://books.toscrape.com/";
        try {
            URL obj = new URL(url);
            System.out.println(obj);
            HttpURLConnection con=(HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();

            System.out.println("Response code: " + responseCode);
            BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream())); 

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            }
            in.close();
            String html = response.toString();
            

            Document doc =  Jsoup.parse(html);

            // System.out.println(doc);

            Elements articles = doc.select("article");
            // System.out.println(articles);
            
            List<BookModel> books = new ArrayList<>();

            for(Element article :articles){

                // System.out.println(article);
                Elements img=article.select(".image_container a img");
                String img_src = img.attr("src");
                img_src = img_src.replace("media/", "https://books.toscrape.com/media/");
                System.out.println(img_src);

                String rating = article.select(".star-rating").attr("class").split(" ")[1];
                System.out.println(rating);


                String title = article.select("h3 a").attr("title");
                System.out.println(title);

                String price = article.select(".price_color").text();
                price = price.substring(2);
                System.out.println(price);

                boolean inStock = article.select(".icon-ok").size()>0?true:false;
                System.out.println(inStock);

                BookModel book = new BookModel();
                book.setTitle(title);
                book.setImageUrl(url);
                book.setPrice(Double.parseDouble(price) );
                book.setRating(rating);
                book.setInStock(inStock);
                book.setDeleted(false);
                book.setDesc("");


                books.add(book);
            }

            MongoDBCon mongoDBCon = new MongoDBCon();
            var insertedIds = mongoDBCon.mongoInsertMany(books);
            System.out.println(insertedIds);
            // var prices = doc.select("article .price_color").eachText();
            // // var string ="";
            // System.out.println(articles);
            // System.out.println(prices);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        


        System.out.println("Hello world!");
        getAllBooks();
    }
}