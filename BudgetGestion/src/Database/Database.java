/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Salim
 */
public class Database {
    
    private static final String LOCALHOST = "://localhost:3307";
    private static final String DATABASE_NAME = "budget";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private static Database INSTANCE;
    
    private Database(){};
    
    public static Database getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new Database();
            return INSTANCE;
        } else {
            return INSTANCE;
        }
    }
    
    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:mysql" + LOCALHOST + "/" + DATABASE_NAME,
                        USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    
}
