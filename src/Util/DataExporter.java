package Util;

import DatabaseController.ListPartDBController;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Util.LogHandler.logger;

public class DataExporter {

    public static void ExcelExporter() throws SQLException, IOException, ClassNotFoundException {

        String allParts = "SELECT * FROM tbl_part";
        ResultSet partRS;
        try {
            partRS = DatabaseConnector.dbExecuteQuery(allParts);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Part DB");

        XSSFRow row = spreadsheet.createRow(1);
        XSSFCell cell;
        cell = row.createCell(1);
        cell.setCellValue("Part ID");
        cell = row.createCell(2);
        cell.setCellValue("Part Name");
        cell = row.createCell(3);
        cell.setCellValue("Part Count");
        cell = row.createCell(4);
        cell.setCellValue("Specification");

        int i = 2;
        while(partRS.next()) {
            row = spreadsheet.createRow(i);
            cell = row.createCell(1);
            cell.setCellValue(partRS.getInt("part_id"));
            cell = row.createCell(2);
            cell.setCellValue(partRS.getString("name"));
            cell = row.createCell(3);
            cell.setCellValue(partRS.getInt("count"));
            cell = row.createCell(4);
            cell.setCellValue(ListPartDBController.getValofEachPart(partRS.getInt("part_id")));
            i++;
        }
        for (i = 1; i <= 4; i++) spreadsheet.autoSizeColumn(i);

        FileOutputStream out = new FileOutputStream(new File("export-import/Leda_DB.xlsx"));
        workbook.write(out);
        out.close();
        logger.info("Leda_DB.xlsx written successfully.");
        System.out.println("Leda_DB.xlsx written successfully.");
    }

}

