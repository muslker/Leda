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

    // Search for selected Part's visible Features
    public static ObservableList<MainPageModel> searchVisibleFeatures(String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT tbl_part.name, tbl_part.count, tbl_relation.spec, tbl_relation.value FROM tbl_part, tbl_relation " +
                "WHERE tbl_part.name= '" + name + "' AND (tbl_part.part_id = tbl_relation.part_id) AND tbl_relation.visibility = '1'";
        try {
            ResultSet rsVisibleFeature = DatabaseConnector.dbExecuteQuery(selectStmt);
            logger.info("Successfully searched for selected Part's visible Features.");
            return getVisibleFeature(rsVisibleFeature);
        } catch (SQLException e) {
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
