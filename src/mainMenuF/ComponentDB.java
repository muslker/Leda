package mainMenuF;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentDB {

    //  Search for component with spesified name
    public static Component searchComponent (String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM component_table WHERE name='" + name + "'";
        try {
            ResultSet rsCmp = DatabaseCFG.dbExecuteQuery(selectStmt);
            return getComponentFromResultSet(rsCmp);
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
            cmp.setCount(rs.getString("count"));
        }
        return cmp;
    }

    // Search for all Components
    public static ObservableList<Component> searchComponentS () throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM component_table";
        try {
            ResultSet rsCmp = DatabaseCFG.dbExecuteQuery(selectStmt);
            return getComponentList(rsCmp);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Component> getComponentList(ResultSet rs) throws SQLException {
        ObservableList<Component> empList = FXCollections.observableArrayList();
            while (rs.next()) {
                Component cmp = new Component();
                cmp.setName(rs.getString("name"));
                cmp.setValue(rs.getString("value"));
                cmp.setCount(rs.getString("count"));
                empList.add(cmp);
            }
            return empList;
        }

    //  Search Component with it's name
    public static ObservableList<String> searchCompNames() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT name FROM component_table";
        try {
            ResultSet rsCmp = DatabaseCFG.dbExecuteQuery(selectStmt);
            return getCompNames(rsCmp);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<String> getCompNames(ResultSet rs) throws SQLException {
        ObservableList<String> empNames = FXCollections.observableArrayList();
        while (rs.next()) {
            empNames.add(rs.getString("name"));
        }
        return empNames;
    }

    //  Update Component's Count
    public static void updateCompCount (String name, String count) throws SQLException, ClassNotFoundException {
        String updateStmt ="UPDATE component_table SET count= '" + count + "' WHERE name= '" + name + "'";
        try {
            DatabaseCFG.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //  Delete Component with it's name
    public static void deleteComponentwithName(String name) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM component_table WHERE name='" + name + "'";
        try {
            DatabaseCFG.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //  Insert Component
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
