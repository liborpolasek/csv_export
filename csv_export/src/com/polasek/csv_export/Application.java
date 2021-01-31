package com.polasek.csv_export;

import com.polasek.csv_export.table_manager.Table;

public class Application {

    public static void main(String args[]) {
        System.out.println("hello world!");
        FileManager f = new FileManager("C:\\Users\\ribor\\IdeaProjects\\polasek\\csv_export\\data.csv");
        Table table = f.getFilteredTable("Czech Republic", "2010 Q4");
        System.out.println(table.getParsedRows());
        System.out.println(table.getSoldUnits("Dell"));
        System.out.println(table.getNumberOfRow("Dell"));
        System.out.println(table.sortRowsAlphabetically().getParsedRows());
    }

}
