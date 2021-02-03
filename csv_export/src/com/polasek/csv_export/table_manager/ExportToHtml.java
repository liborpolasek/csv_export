package com.polasek.csv_export.table_manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Exports Table to HTML format file.
 *
 * @author Libor Polasek
 */
public class ExportToHtml {

    private Table table;
    private String htmlTable;

    public ExportToHtml(Table table) {
        this.table = table;
        this.htmlTable = generateHtmlTable();
    }

    /**
     * Generates HTML format of table.
     * @return String with HTML format of table
     */
    private String generateHtmlTable() {
        StringBuilder str = new StringBuilder();
        str.append("<table class=\"table\">\n");
        str.append("<caption>Table, PC Quarterly Market Share, ");
        str.append(this.table.getCountry() + ", " + this.table.getQuarter() + "</caption>");
        str.append("<tr>\n<th>Vendor</th>\n<th>Units</th>\n<th>Share</th>\n</tr>\n");
        for(List<String> row : table.getParsedRows()) {
            str.append("<tr>\n");
            for(int i = 2; i < row.size(); i++) {
                str.append("<th>");
                if(i == 4) str.append(row.get(i) + "%");
                else if(i == 3) str.append(Math.floor((Double.parseDouble(row.get(i)) * 100)) / 100);
                else str.append(row.get(i));
                str.append("</th>\n");
            }
            str.append("</tr>\n");
        }
        str.append("<tr>\n<th>Total</th>\n");
        str.append("<th>" + this.table.getTotalUnits() + "</th>\n");
        str.append("<th>100%</th>\n</tr>\n");
        str.append("</table>");
        return str.toString();
    }

    /**
     * Loads the template of html file and replace $body in it with html code of table.
     * @return String of html page with table
     * @throws IOException
     */
    private String generateHtmlCode() {
        String code = null;
        try {
            code = Files.readString(Paths
                    .get("csv_export/src/com/polasek/csv_export/table_manager/template.html").toAbsolutePath())
                    .replace("$body", this.htmlTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * Returns html code of table.
     * @return String of formatted table in html
     */
    public String getHtmlTable() {
        return this.htmlTable;
    }

    /**
     * Exports HTML format of table to file of given name.
     * @param path where to save file
     * @throws IOException when file exists or can't be created
     */
    public void exportHtmlFile(String path) {
        File htmlFile = new File(path);
        try {
            if(htmlFile.createNewFile()) {
                FileWriter fw = new FileWriter(path);
                fw.write(generateHtmlCode());
                fw.close();
            } else {
                System.out.println("File wasn't created. File already exists?");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
