package com.polasek.csv_export;

import java.util.List;

import com.polasek.csv_export.table_manager.Table;

import static com.polasek.csv_export.Constants.ALPHABETICALLY_BY_VENDOR;
import static com.polasek.csv_export.Constants.NUMERIC_BY_UNITS;

public class Application {

    public static void main(String args[]) {
        System.out.println("hello world!");
        FileManager f = new FileManager("/Users/andresito/Code/riborpolasek/csv_export-main/csv_export/data.csv");
        Table table = f.getFilteredTable("Czech Republic", "2010 Q4");
        List<Double> soldUnitsInformation = table.getSoldUnits("Dell");
        int numberOfRow = table.getNumberOfRow("Dell");
        Table alphabeticallySortedTable = table.sortRowsByCondition(ALPHABETICALLY_BY_VENDOR, true);
        Table numericSortedTable = table.sortRowsByCondition(NUMERIC_BY_UNITS, true);

        //Information
        //Question a)
        System.out.println("a) Creation of the table: \n" + table);
        //Question b)
        System.out.println("b) Information about units sold: \n" + soldUnitsInformation);
        //Question c)
        System.out.println("c) Number of the row for a given vendor: \n" + numberOfRow);
        //Question d)
        System.out.println("d) Alphabetically sorted table: \n" + alphabeticallySortedTable);
        //Question e)
        System.out.println("d) Numerically sorted table: \n" + numericSortedTable);

    }

}
