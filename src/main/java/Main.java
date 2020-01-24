import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
//        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
//        mongoLogger.setLevel(Level.SEVERE);
//        MongoDBDatabase mongoDB = new MongoDBDatabase("ii");
//
//        mongoDB.setDatabase("examples");
//        mongoDB.setCollection("inventory");
//        System.out.println(mongoDB.getSpedificFields("random"));

        PostgreSQLDatabase postgreSQLDatabase = new PostgreSQLDatabase();
        postgreSQLDatabase.getCustomerList().stream().forEach(System.out::println);

    }
}
