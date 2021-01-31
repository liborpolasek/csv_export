package com.polasek.csv_export;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.polasek.csv_export.table_manager.Table;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {

    private final String filePath;
    private List<String[]> content;
    private String[] header;

    public FileManager(String filePath){
        this.filePath = filePath;
        readCSV();
    }

    private void readCSV() {
        try {
            final List<String[]> file = new CSVReader(new FileReader(this.filePath)).readAll();
            this.header = file.get(0);
            this.content = file.subList(1,file.size());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

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

    public void getData(String nameOfColumn, String Column, List<String> columns) {
        int cPosition = getPosition(nameOfColumn);
        int[] columnsPositions = new int[columns.size()];
        for(int i = 0; i < columns.size(); i++) {
            columnsPositions[i] = getPosition(columns.get(i));
        }
        //if(cPosition == -1) return new ArrayList<>();

        ArrayList<String[]> data = new ArrayList<>();
        for(int i = 0; i < this.content.size(); i++) {
            if(this.content.get(i)[cPosition].equals(Column)) {/*
                data.add(Arrays.asList(columnsPositions).stream()
                        .map(Arrays.asList(this.content.get(i))::get)
                        .collect(Collectors.toList()));*/
            }
        }
    }

    private int getPosition(String column) {
        for(int i = 0; i < this.header.length; i++) {
            if(this.header[i].equals(column)) {
                return i;
            }
        }
        return -1;
    }

}
