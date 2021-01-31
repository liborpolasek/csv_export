package com.polasek.csv_export.table_manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {

    private final int countryPos = 0;
    private final int quarterPos = 1;
    private final int vendorPos = 2;
    private final int unitPos = 3;
    private final int sharePos = 4;
    private final int totalPercentage = 100;

    private List<List<String>> rows;
    private List<List<String>> parsedRows;
    private float totalUnits = 0;

    public Table(List<List<String>> rows) {
        this.parsedRows = new ArrayList<>();
        this.rows = rows;
        calculateShare();
    }

    private void calculateShare() {
        for(List<String> row : rows) {
            this.totalUnits += Float.parseFloat(row.get(this.unitPos));
        }

        for(List<String> row : rows) {
            parsedRows.add(Arrays.asList(new String[5]));
            float result = 0;
            try {
                result = Float.parseFloat(row.get(this.unitPos)) / this.totalUnits;
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
            parsedRows.get(parsedRows.size() - 1).set(countryPos, row.get(countryPos));
            parsedRows.get(parsedRows.size() - 1).set(quarterPos, row.get(quarterPos));
            parsedRows.get(parsedRows.size() - 1).set(vendorPos, row.get(vendorPos));
            parsedRows.get(parsedRows.size() - 1).set(unitPos, row.get(unitPos));
            parsedRows.get(parsedRows.size() - 1).set(sharePos, String.valueOf(result));
        }
    }

    public List<List<String>> getParsedRows() {
        return this.parsedRows;
    }

    public List<Double> getSoldUnits(String vendor) {
        List<Double> soldUnits = new ArrayList<>();
        for(List<String> l : this.parsedRows) {
            if(l.get(this.vendorPos).equals(vendor)) {
                soldUnits.add((double) Math.round(Double.parseDouble(l.get(this.unitPos))));
                soldUnits.add(((double) Math.round(Double.parseDouble(l.get(this.sharePos)) * 1000) / 10));
                break;
            }
        }
        return soldUnits;
    }

    public int getNumberOfRow(String vendor) {
        for(int i = 0; i < this.parsedRows.size(); i++) {
            if(this.parsedRows.get(i).get(this.vendorPos).equals(vendor)) {
                return i;
            }
        }
        return -1;
    }

    public Table sortRowsAlphabetically() {
        List<List<String>> sorted = this.rows;
        this.rows.sort(new VendorComparator<>());
        return new Table(sorted);
    }
}
