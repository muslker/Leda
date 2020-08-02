package DatabaseController;

import Model.ListPartModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Util.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListPartDBController {

    //  Search for Part with spesified name
    public static ListPartModel searchPart(String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM tbl_part WHERE name='" + name + "'";
        try {
            ResultSet rsPart = DatabaseConnector.dbExecuteQuery(selectStmt);
            return getPartFromResultSet(rsPart);
        } catch (SQLException e) {
            System.out.println("While searching an Part with " + name + " name, an error occurred: " + e);
            throw e;
        }
    }

    public static ListPartModel getPartFromResultSet(ResultSet rs) throws SQLException {
        ListPartModel part = null;
        if (rs.next()) {
            part = new ListPartModel();
            part.setName(rs.getString("name"));
            part.setCount(rs.getInt("count"));
        }
        return part;
    }

    // Search for all Parts
    public static ObservableList<ListPartModel> searchParts() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM tbl_part";
        try {
            ResultSet rsPart = DatabaseConnector.dbExecuteQuery(selectStmt);
            return getPartList(rsPart);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    public static ObservableList<ListPartModel> getPartList(ResultSet rs) throws SQLException {
        ObservableList<ListPartModel> partList = FXCollections.observableArrayList();
            while (rs.next()) {
                ListPartModel part = new ListPartModel();
                part.setName(rs.getString("name"));
                part.setCount(rs.getInt("count"));
                partList.add(part);
            }
            return partList;
        }

    //  Search Part with it's name
    public static ObservableList<String> searchPartNames() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT name FROM tbl_part";
        try {
            ResultSet rsPart = DatabaseConnector.dbExecuteQuery(selectStmt);
            return getPartNames(rsPart);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    public static ObservableList<String> getPartNames(ResultSet rs) throws SQLException {
        ObservableList<String> partNames = FXCollections.observableArrayList();
        while (rs.next()) {
            partNames.add(rs.getString("name"));
        }
        return partNames;
    }

    //  Update Part's Count
    public static void updatePartCount(String name, Integer count) throws SQLException, ClassNotFoundException {
        String updateStmt ="UPDATE tbl_part SET count= '" + count + "' WHERE name= '" + name + "'";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //  Delete Part with it's name
    public static void deletePartwithName(String name) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM tbl_part WHERE name='" + name + "'";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //  Insert Part
    public static void insertPart(String name, Integer count) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO tbl_part (name, count) VALUES ('" + name + "','" + count + "');";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }
}
