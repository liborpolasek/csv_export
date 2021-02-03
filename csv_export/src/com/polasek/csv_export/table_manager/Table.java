package com.polasek.csv_export.table_manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.polasek.csv_export.Constants.ALPHABETICALLY_BY_VENDOR;
import static com.polasek.csv_export.Constants.NUMERIC_BY_UNITS;

/**
 * This class represent object Table with all content.
 *
 * @author Libor Polasek
 */
public class Table {

    private final int countryPos = 0;
    private final int quarterPos = 1;
    private final int vendorPos = 2;
    private final int unitPos = 3;
    private final int sharePos = 4;

    private List<List<String>> parsedRows;
    private String country;
    private String quarter;
    private float totalUnits = 0;


    public Table(List<List<String>> rows) {
        this.parsedRows = new ArrayList<>();
        this.country = rows.get(0).get(countryPos);
        this.quarter = rows.get(0).get(quarterPos);
        calculateShare(rows);
    }

    /**
     * It is called in constructor and it adds to the table share column.
     * @param rows which are use to create the table and calculate share
     * @throws NumberFormatException when parse to float can't be performed
     */
    private void calculateShare(List<List<String>> rows) {
        for(List<String> row : rows) {
            this.totalUnits += Float.parseFloat(row.get(this.unitPos));
        }

        for(List<String> row : rows) {
            parsedRows.add(Arrays.asList(new String[5]));
            double result = 0;
            try {
                result = Math.floor((Double.parseDouble(row.get(this.unitPos)) / this.totalUnits) * 1000) / 10;
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

    /**
     * Simple returns whole content of table.
     * @return content of table
     */
    public List<List<String>> getParsedRows() {
        return this.parsedRows;
    }

    /**
     * Returns country of table for which is filtered.
     * @return country of table
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Returns quarter of table for which is filtered.
     * @return quarter of table
     */
    public String getQuarter() {
        return this.quarter;
    }

    public float getTotalUnits() {
        return this.totalUnits;
    }

    /**
     * Returns sold units and share of units on given market for given vendor.
     * @param vendor for what units and share I want to return
     * @return list with sold units and share
     */
    public List<Double> getSoldUnits(String vendor) {
        List<Double> soldUnits = new ArrayList<>();
        for(List<String> l : this.parsedRows) {
            if(l.get(this.vendorPos).equals(vendor)) {
                soldUnits.add((double) Math.round(Double.parseDouble(l.get(this.unitPos))));
                soldUnits.add(Double.parseDouble(l.get(this.sharePos)));
                break;
            }
        }
        return soldUnits;
    }

    /**
     * Returns number of a row of the vendor.
     * @param vendor for what number of row I want
     * @return number of row for given vendor or -1 if it isn't found
     */
    public int getNumberOfRow(String vendor) {
        for(int i = 0; i < this.parsedRows.size(); i++) {
            if(this.parsedRows.get(i).get(this.vendorPos).equals(vendor)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Sorts rows by given condition. Condition can be ALPHABETICALLY_BY_VENDOR or NUMERIC_BY_UNITS.
     * @param condition according to which it will be sorted
     * @param ascOrder true - if ascending order, else false - for descending order
     * @return new sorted Table
     */
    public Table sortRowsByCondition(int condition, boolean ascOrder) {
        List<List<String>> sorted = new ArrayList<>(this.parsedRows);
        switch(condition) {
            case(ALPHABETICALLY_BY_VENDOR):
                sorted = sorted.stream()
                    .sorted(Comparator.comparing(o -> o.get(vendorPos).toLowerCase()))
                    .collect(Collectors.toList());
                break;
            case(NUMERIC_BY_UNITS):
                sorted = sorted.stream()
                    .sorted(Comparator.comparingDouble(o -> Double.parseDouble(o.get(unitPos))))
                    .collect(Collectors.toList());
                break;
            default:
                break;
        }
        if(!ascOrder) {
            Collections.reverse(sorted);
        }
        return new Table(sorted);
    }

    /**
     * Overrided toString to get better String format of table.
     * @return String format of table
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Table, PC Quarterly Market Share, "+ this.country +", " + this.quarter + "\n");
        str.append("Vendor " + "Units " + "Share\n");
        for(List<String> l : this.parsedRows) {
            str.append(l.get(vendorPos) + " ");
            str.append(l.get(unitPos) + " ");
            str.append(Double.parseDouble(l.get(this.sharePos)) + " ");
            str.append("\n");
        }
        return str.toString();
    }

}
