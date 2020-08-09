package DatabaseController;

import Model.MainPageModel;
import Util.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static Util.LogHandler.logger;

public class MainPageDBController {
    public static ObservableList<MainPageModel> visibleFeature;
    public static MainPageModel visFtr;

    //  Search for Part with specified name
    public static MainPageModel searchPart(String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM tbl_part WHERE name='" + name + "'";
        try {
            ResultSet rsPart = DatabaseConnector.dbExecuteQuery(selectStmt);
            logger.info("Successfully searched for Part with specified name.");
            return getPartFromResultSet(rsPart);
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("While searching an Part with " + name + " name, an error occurred: " + e);
            System.out.println("While searching an Part with " + name + " name, an error occurred: " + e);
            throw e;
        }
    }
    public static MainPageModel getPartFromResultSet(ResultSet rs) throws SQLException {
        MainPageModel part = null;
        if (rs.next()) {
            part = new MainPageModel();
            part.setName(rs.getString("name"));
            part.setCount(rs.getInt("count"));
        }
        return part;
    }

    //  Search Part with it's name
    public static ObservableList<String> searchPartNames() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT name FROM tbl_part";
        try {
            ResultSet rsPart = DatabaseConnector.dbExecuteQuery(selectStmt);
            logger.info("Successfully searched Part with it's name.");
            return getPartNames(rsPart);
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL select operation has been failed: " + e);
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
            logger.info("Successfully updated part's count.");
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Error occurred while UPDATE Part Operation: " + e);
            System.out.print("Error occurred while UPDATE Part Operation: " + e);
            throw e;
        }
    }

    // Search for selected Part's visible Features
    public static ObservableList<MainPageModel> searchVisibleFeatures(String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT tbl_part.name, tbl_part.count, tbl_relation.spec, tbl_relation.value FROM tbl_part, tbl_relation " +
                "WHERE tbl_part.name= '" + name + "' AND (tbl_part.part_id = tbl_relation.part_id) AND tbl_relation.visibility = '1'";
        try {
            ResultSet rsVisibleFeature = DatabaseConnector.dbExecuteQuery(selectStmt);
            logger.info("Successfully searched for selected Part's visible Features.");
            return getVisibleFeature(rsVisibleFeature);
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL select operation has been failed: " + e);
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    public static ObservableList<MainPageModel> getVisibleFeature(ResultSet rs) throws SQLException {
        visibleFeature = FXCollections.observableArrayList();
        while (rs.next()) {
            visFtr = new MainPageModel();
            visFtr.setName(rs.getString("name"));
            visFtr.setSpec(rs.getString("spec"));
            visFtr.setValue(rs.getString("value"));
            visFtr.setCount(rs.getInt("count"));
            visibleFeature.add(visFtr);
        }
        return visibleFeature;
    }
}
