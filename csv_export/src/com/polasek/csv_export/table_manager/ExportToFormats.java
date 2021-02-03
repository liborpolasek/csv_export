package com.polasek.csv_export.table_manager;

/**
 * This class extends ExportToHtml class and adds export to CSV and Excel files.
 *
 * @author Libor Polasek
 */
public class ExportToFormats extends ExportToHtml{

    public ExportToFormats(Table table) {
        super(table);
    }

    /**
     * Exports table to CSV file.
     * @param path name and where to save file
     */
    public void exportCSVFile(String path) {

    }

    /**
     * Exports table to Excel file.
     * @param path name and where to save file
     */
    public void exportExcelFile(String path) {

    }
}
