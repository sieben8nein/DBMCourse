import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalPostgreSQLDatabase {
    private String username;
    private String password;
    private Connection connection;
    public LocalPostgreSQLDatabase(){
        createConnection();

    }

    private void createConnection(){
        String url = "jdbc:postgresql://localhost:5433/postgres";
        username = "postgres";
        password = "sieben8nein";
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("successfully connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("driver could not be found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection Failed! Check output console");
        }
    }

    public void testQuery(){
        try {
            ResultSet result = createStatement().executeQuery(
                    "SELECT * " +
                            "FROM products ");
            while (result.next()) {
                System.out.println(result.getString("price"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Statement createStatement() {
        Statement execStat = null;
        try {
            execStat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return execStat;
    }
}
