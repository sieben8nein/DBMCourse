import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import models.Person;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
//        mongoLogger.setLevel(Level.SEVERE);
//        MongoDBDatabase mongoDB = new MongoDBDatabase();
//        mongoDB.setDatabase("examples");
//        mongoDB.setCollection("persons");
//        mongoDB.setUniqueKey("id");
//        mongoDB.createPerson(1, "adam", 21);
//        System.out.println(mongoDB.getPersonById(1));
//        System.out.println(mongoDB.getWithQuery());
//
//        mongoDB.updateNameById(1, "Jason");
//
//        System.out.println(mongoDB.getPersonById(1));
//        Person person = gson.fromJson((String)mongoDB.getPersonById(1), Person.class);
//        System.out.println("name from person: " + person.getName());


//        PostgreSQLDatabase postgreSQLDatabase = new PostgreSQLDatabase();
//        postgreSQLDatabase.getCustomerList().stream().forEach(System.out::println);

        LocalPostgreSQLDatabase localPostgreSQLDatabase = new LocalPostgreSQLDatabase();
        localPostgreSQLDatabase.testQuery();

    }
}
