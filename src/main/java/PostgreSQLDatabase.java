import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDatabase {

    //Data: https://github.com/pthom/northwind_psql/blob/master/northwind.sql

    private String username;
    private String password;
    private Connection connection;
    public PostgreSQLDatabase(){
        createConnection();
    }

    public void createConnection(){
        String url = "jdbc:postgresql://balarama.db.elephantsql.com:5432/cuyudaqq";
        username = "cuyudaqq";
        password = "VSqs-Nx0dgPAsu4ay5GKpfQ71ICwonnF";
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

    public ArrayList<String> getCustomerList(){
        ArrayList<String> customerList = new ArrayList<>();
        try {
            ResultSet result = createStatement().executeQuery(
                    "SELECT * " +
                            "FROM customers " +
                            "WHERE country = 'Mexico' ");
            while (result.next()) {
                customerList.add(result.getString("contact_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerList;
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
