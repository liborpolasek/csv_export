package com.polasek.csv_export;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.polasek.csv_export.table_manager.Table;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FileManager is to process CSV file with the table to object Table.
 *
 * @author Libor Polasek
 */
public class FileManager {

    private final String filePath;
    private List<String[]> content;
    private String[] header;

    public FileManager(String filePath){
        this.filePath = filePath;
        readCSV();
    }

    /**
     * Reads the CSV file and sets header and content.
     * @throws IOException when file file doesn't exists or can't be read
     * @throws CsvException
     */
    private void readCSV() {
        try {
            final List<String[]> file = new CSVReader(new FileReader(this.filePath)).readAll();
            this.header = file.get(0);
            this.content = file.subList(1,file.size());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets country and quarter and returns new filtered table by them.
     * @param country by which we want to filter
     * @param quarter by which we want to filter
     * @return new table with filtered content for given country and quarter
     */
    public Table getFilteredTable(String country, String quarter) {
        int i = 0;
        int countryPos = 0;
        int quarterPos = 0;
        boolean countryFound = false;
        boolean quarterFound = false;

        while(i < this.header.length && !(countryFound && quarterFound)) {
            if(this.header[i].equals("Country")) {
                countryPos = i;
                countryFound = true;
            } else if(this.header[i].equals("Timescale")) {
                quarterPos = i;
                quarterFound = true;
            }
            i++;
        }
        int finalCountryPos = countryPos;
        int finalQuarterPos = quarterPos;
        List<List<String>> filteredContent = this.content.stream()
                .filter(entry-> entry[finalCountryPos].equals(country) && entry[finalQuarterPos].equals(quarter))
                .map(Arrays::asList)
                .collect(Collectors.toList());

        return new Table(filteredContent);
    }

}
