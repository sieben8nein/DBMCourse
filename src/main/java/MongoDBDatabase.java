import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import java.util.ArrayList;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;

@SuppressWarnings("Duplicates")
public class MongoDBDatabase {
    private MongoClient mongoClient;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    public MongoDBDatabase() {
        connectAsClient();
    }

    private void connectAsClient() {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://sieben8nein:sieben8neinten@testcluster-gb47g.azure.mongodb.net/test?retryWrites=true&w=majority");
        this.mongoClient = new MongoClient(uri);
    }

    public void setDatabase(String database) {
        db = mongoClient.getDatabase(database);
    }

    public void setCollection(String collection) {
        this.collection = db.getCollection(collection);
    }

    public void setUniqueKey(String key){
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(Indexes.ascending(key), indexOptions);
    }

    public ArrayList<String> getAllDocs(){
        MongoCursor<Document> cursor = collection.find().iterator();
        ArrayList<String> docs = new ArrayList<String>();
        try {
            while(cursor.hasNext()){
                docs.add(cursor.next().toJson());
            }
        }
        finally {
            cursor.close();
        }
        return docs;
    }

    public String getWithQuery(){
        BasicDBObject query = new BasicDBObject("qty", new BasicDBObject("$gt", 25));
        Document doc = collection.find(query).first();
        FindIterable<Document> iterable = collection.find(query).sort(new Document("qty", 1));
        StringBuilder sb = new StringBuilder();
        for (Document d : iterable){
            sb.append(gson.toJson(d));
        }
        if(sb.length() <= 0){
            sb.append("could not find any matching data");
        }
        return sb.toString();
    }

    public String getSpedificFields(String field){
        BasicDBObject query = new BasicDBObject("qty", new BasicDBObject("$gt", 25));
        Document doc = collection.find(query).first();
        FindIterable<Document> iterable = collection.find(query).projection(Projections.include("qty", "status"));
        StringBuilder sb = new StringBuilder();
        for (Document d : iterable){
            sb.append(gson.toJson(d));
        }
        return sb.toString();
    }

    public MongoDatabase createDatabase(String name){
        return mongoClient.getDatabase(name);
    }
    public MongoCollection<Document> createCollection(String name){
        return db.getCollection(name);
    }

    public void createPerson(int id, String name, int age){
        try {
            Document doc = new Document("id", id).append("name", name).append("age", age);
            collection.insertOne(doc);
        } catch (MongoWriteException e){
            System.out.println("could not create person, the id is already in use");
        }

    }

    public String getPersonById(int id){
        setCollection("persons");
        BasicDBObject query = new BasicDBObject("id", id);
        Document doc = collection.find(query).first();
        return gson.toJson(doc);
    }

    public String updateNameById(int id, String newName){
        setCollection("persons");
        collection.updateOne(eq("id", id), set("name", newName));
        return getPersonById(1);
    }
}
