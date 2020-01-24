import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class MongoDBDatabase {
    private MongoClient mongoClient;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    public MongoDBDatabase(String mongoUri) {
        connectAsClient(mongoUri);
    }

    private void connectAsClient(String mongoUri) {
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

    public String getFirstDoc(){
        return collection.find().first().toJson();
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
}
