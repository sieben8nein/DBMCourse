import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        TestDB db = new TestDB( "mongodb+srv://sieben8nein:sieben8neinten@testcluster-gb47g.azure.mongodb.net/test?retryWrites=true&w=majority");

        db.setDatabase("examples");
        db.setCollection("inventory");
        System.out.println(db.getFirstDoc());
    }
}
