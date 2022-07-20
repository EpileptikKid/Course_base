package model.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private ConnectionPool() {}
    private static ConnectionPool instance = null;
    public static ConnectionPool getInstance(){
        if (instance==null) {
            instance = new ConnectionPool();
        }
        return instance;
    }
    public Connection getConnection(){
        Context ctx = null;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/base_pool");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
