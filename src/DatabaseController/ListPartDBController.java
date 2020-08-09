package DatabaseController;

import Model.ListPartModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Util.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

import static Util.LogHandler.logger;

public class ListPartDBController {

    //  Delete Part with it's name
    public static void deletePartwithName(String name) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM tbl_part WHERE name='" + name + "'";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            logger.warning("Error occurred while DELETE Part Operation: " + e);
            System.out.print("Error occurred while DELETE Part Operation: " + e);
            throw e;
        }
    }

    // Search for all Parts and these values
    public static ObservableList<ListPartModel> listAllParts() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM tbl_part";
        try {
            ResultSet rsAllPart = DatabaseConnector.dbExecuteQuery(selectStmt);
            logger.info("Successfully searched for selected Part's visible Features.");
            return getAllParts(rsAllPart);
        } catch (SQLException e) {
            logger.warning("SQL select operation has been failed: " + e);
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    public static ObservableList<ListPartModel> getAllParts(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<ListPartModel> allParts = FXCollections.observableArrayList();
        while (rs.next()) {
            ListPartModel allPart = new ListPartModel();
            allPart.setId(rs.getInt("part_id"));
            allPart.setName(rs.getString("name"));
            allPart.setVal(getValofEachPart(allPart.getId()));
            allPart.setCount(rs.getInt("count"));
            allParts.add(allPart);
        }
        return allParts;
    }

    //  Search for values of each Part
    public static String getValofEachPart(int id) throws SQLException, ClassNotFoundException, NullPointerException {
        String selectStmt = "SELECT value FROM tbl_relation WHERE part_id ='" + id + "'";
        String vals = "";
        try {
            ResultSet rsSpecs = DatabaseConnector.dbExecuteQuery(selectStmt);
            while (rsSpecs.next()) vals += (rsSpecs.getString("value")) + " ";
            logger.info("Successfully searched for all specs and values");
            return vals;
        } catch (SQLException | NullPointerException e) {
            logger.warning("SQL select operation has been failed: " + e);
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
}
