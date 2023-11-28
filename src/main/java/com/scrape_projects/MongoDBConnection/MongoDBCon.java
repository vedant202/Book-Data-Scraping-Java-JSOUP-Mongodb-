package com.scrape_projects.MongoDBConnection;

import static com.mongodb.client.model.Filters.eq;

import org.bson.BsonValue;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.scrape_projects.Model.BookModel;


import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * MongoDBCon
 */
public class MongoDBCon {
    private String url;
    private String pass;
    private BsonValue insertedId;
    private Map<Integer,BsonValue> insertedIds;
    public void setConfig(){

         try (InputStream inputStream = MongoDBCon.class.getClassLoader().getResourceAsStream("myConfig.config")){


            Properties properties = new Properties();
            properties.load(inputStream);
            String propertyValue = properties.getProperty("username");
            String propertyValue2 = properties.getProperty("password");

            System.out.println("Property Value: " + propertyValue+" "+propertyValue2);


           this.pass = URLEncoder.encode(propertyValue2, "UTF-8");
    this.url = "mongodb+srv://"+propertyValue+":"+this.pass+"@todos.rkq9uab.mongodb.net/?retryWrites=true&w=majority";

            
        } catch (Exception e) {

            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public ArrayList<Document> getAllData(){
        setConfig();
        ArrayList<Document> arr = new ArrayList<>();
        try (MongoClient mogoClient = MongoClients.create(this.url)) {
            MongoDatabase databases = mogoClient.getDatabase("ToDos");
            MongoCollection<Document> collectionBook = databases.getCollection("Books");

            FindIterable<Document>docs = collectionBook.find();

            for (Document document : docs) {
                arr.add(document);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return arr;

    }

    public BsonValue mongoInsert(BookModel book){
        setConfig();
       
        System.out.println(url);
        try(MongoClient mogoClient = MongoClients.create(this.url)){

            // System.out.println(mogoClient.);
            MongoDatabase databases = mogoClient.getDatabase("ToDos");
            MongoCollection<Document> collectionBook = databases.getCollection("Books");
            System.out.println("databases "+databases.getName());
            // var collectionNames = databases.listCollectionNames();
            // for(var coll:collectionNames){
            //     System.out.println(coll);
            // }
            System.out.println(collectionBook.countDocuments());
            // find docs

            // FindIterable<Document> docs = collectionTodo.find();
            // System.out.println(doc);
            // for(Document doc:docs){
            //     System.out.println(doc);
            // }

            // insert docs
            // Document newDoc = new Document().append("title", "New Documents").append("Desc", "New Java doc");

            // System.out.println("newDoc "+newDoc);

            // InsertOneResult insert =  collectionTodo.insertOne(newDoc);
            // System.out.println(insert.getInsertedId());

            // Find by id

            // FindIterable<Document> find_doc = collectionTodo.find(eq("title","New Documents"));
            // System.out.println(find_doc.iterator().next().toJson());

            Document newDoc = new Document().append("title", book.getTitle()).append("rating", book.getRating()).append("price", book.getPrice()).append("inStock", book.isInStock()).append("imgUrl", book.getImageUrl()).append("desc", book.getDesc()).append("insertTime", book.getTime());
            System.out.println(newDoc);
            InsertOneResult insert =  collectionBook.insertOne(newDoc);

            if(insert.wasAcknowledged()){
                System.out.println("Data Inserted Successfully");
                System.out.println("inserted data id "+insert.getInsertedId());
                this.insertedId = insert.getInsertedId();
            }else{
                this.insertedId = null;
            }

            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return this.insertedId;
    }

    public Map<Integer,BsonValue> mongoInsertMany(List<BookModel> books){
        setConfig();
       
        System.out.println(url);
        try(MongoClient mogoClient = MongoClients.create(this.url)){

            // System.out.println(mogoClient.);
            MongoDatabase databases = mogoClient.getDatabase("ToDos");
            MongoCollection<Document> collectionBook = databases.getCollection("Books");
            System.out.println("databases "+databases.getName());
            
            System.out.println(collectionBook.countDocuments());
            // find docs

            // FindIterable<Document> docs = collectionTodo.find();
            // System.out.println(doc);
            // for(Document doc:docs){
            //     System.out.println(doc);
            // }

            // insert docs
            // Document newDoc = new Document().append("title", "New Documents").append("Desc", "New Java doc");

            // System.out.println("newDoc "+newDoc);

            // InsertOneResult insert =  collectionTodo.insertOne(newDoc);
            // System.out.println(insert.getInsertedId());

            // Find by id

            // FindIterable<Document> find_doc = collectionTodo.find(eq("title","New Documents"));
            // System.out.println(find_doc.iterator().next().toJson());
            List<Document> docs = new ArrayList<>();
            for(BookModel book: books){
                Document newDoc = new Document().append("title", book.getTitle()).append("rating", book.getRating()).append("price", book.getPrice()).append("inStock", book.isInStock()).append("imgUrl", book.getImageUrl()).append("desc", book.getDesc()).append("insertTime", book.getTime()).append("isDeleted", book.isDeleted());
                docs.add(newDoc);

            }
            System.out.println(docs);
            InsertManyResult insert =  collectionBook.insertMany(docs);

            if(insert.wasAcknowledged()){
                System.out.println("Data Inserted Successfully");
                System.out.println("inserted data id "+insert.getInsertedIds());
                this.insertedIds = insert.getInsertedIds();
            }else{
                this.insertedIds = null;
            }

            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return this.insertedIds;
    }

    public static void main(String[] args) {
        MongoDBCon con = new MongoDBCon();
        BookModel book = new BookModel();
        book.setTitle("Test3");
        book.setRating("four");
        book.setPrice(40);
        book.setImageUrl("image");
        book.setInStock(true);
        book.setDesc("");
        
        // BsonValue insertedId = con.mongoInsert(book);
        // System.out.println(insertedId);
        ArrayList<Document> arr=con.getAllData();
        System.out.println(arr);
        
    }
}