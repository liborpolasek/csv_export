package com.polasek.csv_export;

import java.nio.file.Paths;
import java.util.List;

import com.polasek.csv_export.table_manager.ExportToHtml;
import com.polasek.csv_export.table_manager.Table;

import static com.polasek.csv_export.Constants.ALPHABETICALLY_BY_VENDOR;
import static com.polasek.csv_export.Constants.NUMERIC_BY_UNITS;

public class Application {

    public static void main(String args[]) {
        FileManager f = new FileManager(Application.class.getResource("data.csv").getPath());
        Table table = f.getFilteredTable("Czech Republic", "2010 Q4");
        List<Double> soldUnitsInformation = table.getSoldUnits("Dell");
        int numberOfRow = table.getNumberOfRow("Dell");
        Table alphabeticallySortedTable = table.sortRowsByCondition(ALPHABETICALLY_BY_VENDOR, true);
        Table numericSortedTable = table.sortRowsByCondition(NUMERIC_BY_UNITS, true);
        ExportToHtml export = new ExportToHtml(table);

        //Information
        //Task a)
        System.out.println("a) Creation of the table: \n" + table);
        //Task b)
        System.out.println("b) Information about units sold: \n" + soldUnitsInformation);
        //Task c)
        System.out.println("c) Number of the row for a given vendor: \n" + numberOfRow);
        //Task d)
        System.out.println("d) Alphabetically sorted table: \n" + alphabeticallySortedTable);
        //Task e)
        System.out.println("d) Numerically sorted table: \n" + numericSortedTable);
        //Task f)
        System.out.println("f) HTML code of table: ");
        System.out.println(export.getHtmlTable());
        export.exportHtmlFile("table.html");
    }
}
