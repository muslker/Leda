package Util;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

import static Util.LogHandler.logger;

public class DatabaseConnector {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static Connection conn = null;
    private static final String connStr = "jdbc:sqlite:LedaSQLite.db";

    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.warning("JDBC Driver not found." + e);
            System.out.println("JDBC Driver not found." + e);
            e.printStackTrace();
            throw e;
        }

        try {
            conn = DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            logger.warning("Connection Failed! Check output console" + e);
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static void dbDisconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
        try {
            dbConnect();

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);

            crs.populate(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Problem occurred at executeQuery operation : " + e);
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
        return crs;
    }

    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Problem occurred at executeUpdate operation : " + e);
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }
}