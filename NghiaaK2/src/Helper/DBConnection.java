package Helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer","root","123456");
            return conn;

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

