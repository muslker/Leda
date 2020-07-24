package mainMenuF;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentDB {

    public static Component searchComponent (String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM component_table WHERE name='" + name + "'";
        try {
            ResultSet rsCmp = DatabaseCFG.dbExecuteQuery(selectStmt);
            Component component = getComponentFromResultSet(rsCmp);
            return component;
        } catch (SQLException e) {
            System.out.println("While searching an component with " + name + " name, an error occurred: " + e);
            throw e;
        }
    }

    public static Component getComponentFromResultSet(ResultSet rs) throws SQLException {
        Component cmp = null;
        if (rs.next()) {
            cmp = new Component();
            cmp.setName(rs.getString("name"));
            cmp.setValue(rs.getString("value"));
        }
        return cmp;
    }

    public static void deleteComponentwithName(String name) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM component_table WHERE name=" + "'" + name + "'";
        try {
            DatabaseCFG.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void insertComponent(String name, String value, String count) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO component_table (name, value, count) VALUES ('" + name + "','" + value + "','" + count + "');";
        try {
            DatabaseCFG.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }
}
