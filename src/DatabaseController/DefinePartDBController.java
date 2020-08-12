package DatabaseController;

import Model.DefinePartModel;
import Util.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static Util.LogHandler.logger;

public class DefinePartDBController {

    //  Insert Item
    public static void insertNameCount(String name, Integer count) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO tbl_part (name, count) VALUES ('" + name + "','" + count + "');";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
            logger.info("Part name and count successfully inserted to DB.");
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Error occurred while INSERT Operation: " + e);
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }
    public static Integer getPartID(String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT part_id FROM tbl_part WHERE name ='" + name + "'";
        int idx = 0;
        try {
            ResultSet rsItem = DatabaseConnector.dbExecuteQuery(selectStmt);
            while (rsItem.next()) {
                idx = rsItem.getInt("part_id");
            }
            logger.info("Part ID searched from DB successfully.");
            return idx;
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("While searching an Part with " + name + " name, an error occurred: " + e);
            System.out.println("While searching an Part with " + name + " name, an error occurred: " + e);
            throw e;
        }
    }

    // Insert new Feature
    public static void insertFeatures(Integer part_id, Integer visibility, String spec, String value) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO tbl_relation (part_id, visibility, spec, value) VALUES" +
                " ('" + part_id + "','" + visibility + "','" + spec + "','" + value + "');";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
            logger.info("Part features inserted DB successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Error occurred while INSERT Operation: " + e);
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }

    // Search for specific Part's Feature
    public static ObservableList<DefinePartModel> searchFeature(int id) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM tbl_relation WHERE part_id='" + id + "'";
        try {
            ResultSet rsFeature = DatabaseConnector.dbExecuteQuery(selectStmt);
            logger.info("Features searched successfully.");
            return getFeatureList(rsFeature);
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL select operation has been failed: " + e);
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    public static ObservableList<DefinePartModel> getFeatureList(ResultSet rs) throws SQLException {
        ObservableList<DefinePartModel> featureList = FXCollections.observableArrayList();
        while (rs.next()) {
            DefinePartModel feature = new DefinePartModel();
            feature.setVisibility(rs.getInt("visibility") == 1);
            feature.setSpec(rs.getString("spec"));
            feature.setValue(rs.getString("value"));
            featureList.add(feature);
        }
        return featureList;
    }

    //  Delete Feature with it's spec
    public static void deleteFeaturewithSpec(String spec) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM tbl_relation WHERE spec='" + spec + "'";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
            logger.info("Features deleted successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Error occurred while DELETE Feature Operation: " + e);
            System.out.print("Error occurred while DELETE Feature Operation: " + e);
            throw e;
        }
    }

    //  Update Part's Spec
    public static void updateSpecData(String newSpec, String oldSpec) throws SQLException, ClassNotFoundException {
        String updateStmt ="UPDATE tbl_relation SET spec= '" + newSpec + "' WHERE spec= '" + oldSpec + "'";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
            logger.info("Spec updated successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Error occurred while UPDATE Spec Operation: " + e);
            System.out.print("Error occurred while UPDATE Spec Operation: " + e);
            throw e;
        }
    }
    //  Update Part's Value
    public static void updateValueData(String newVal, String oldVal) throws SQLException, ClassNotFoundException {
        String updateStmt ="UPDATE tbl_relation SET value= '" + newVal + "' WHERE value= '" + oldVal + "'";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
            logger.info("Value updated successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Error occurred while UPDATE Value Operation: " + e);
            System.out.print("Error occurred while UPDATE Value Operation: " + e);
            throw e;
        }
    }

}
