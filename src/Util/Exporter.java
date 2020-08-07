package Util;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.sql.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static Util.LogHandler.logger;

public class Exporter {

    public static void ExcelExporter() throws SQLException, IOException, ClassNotFoundException {

        String allParts = "SELECT * FROM tbl_part";
        String allFeatures = "SELECT * FROM tbl_relation";

        ResultSet partResultSet;
        ResultSet featureResultSet;
        try {
            partResultSet = DatabaseConnector.dbExecuteQuery(allParts);
            featureResultSet = DatabaseConnector.dbExecuteQuery(allFeatures);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Part DB");
        XSSFSheet spreadsheet2 = workbook.createSheet("Feature DB");


        XSSFRow row = spreadsheet.createRow(1);
        XSSFRow row2 = spreadsheet2.createRow(1);
        XSSFCell cell;
        cell = row.createCell(1);
        cell.setCellValue("Part ID");
        cell = row.createCell(2);
        cell.setCellValue("Part Name");
        cell = row.createCell(3);
        cell.setCellValue("Part Count");

        cell = row2.createCell(1);
        cell.setCellValue("Part ID");
        cell = row2.createCell(2);
        cell.setCellValue("Visibility");
        cell = row2.createCell(3);
        cell.setCellValue("Spec");
        cell = row2.createCell(4);
        cell.setCellValue("Value");
        int i = 2;

        while(partResultSet.next()) {
            row = spreadsheet.createRow(i);
            cell = row.createCell(1);
            cell.setCellValue(partResultSet.getInt("part_id"));
            cell = row.createCell(2);
            cell.setCellValue(partResultSet.getString("name"));
            cell = row.createCell(3);
            cell.setCellValue(partResultSet.getString("count"));
            i++;
        }

        i = 2;
        while(featureResultSet.next()) {
            row2 = spreadsheet2.createRow(i);
            cell = row2.createCell(1);
            cell.setCellValue(featureResultSet.getInt("part_id"));
            cell = row2.createCell(2);
            cell.setCellValue(featureResultSet.getInt("visibility") == 1);
            cell = row2.createCell(3);
            cell.setCellValue(featureResultSet.getString("spec"));
            cell = row2.createCell(4);
            cell.setCellValue(featureResultSet.getString("value"));
            i++;
        }

        FileOutputStream out = new FileOutputStream(new File("export-import/Leda_DB.xlsx"));
        workbook.write(out);
        out.close();
        logger.info("Leda_DB.xlsx written successfully.");
        System.out.println("Leda_DB.xlsx written successfully.");
    }

}

