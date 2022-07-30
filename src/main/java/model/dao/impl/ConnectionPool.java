package model.dao.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
    private static volatile DataSource dataSource;
    public static DataSource getDataSource(){
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    try {
                        InitialContext initialContext = new InitialContext();
                        dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/base_pool");
                    } catch (NamingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dataSource;
    }
}
