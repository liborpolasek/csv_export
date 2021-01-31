package com.polasek.csv_export.table_manager;

import java.util.List;

public class Column {

    private String label;
    private List<String> values;

    public Column(String label, List<String> values) {
        this.label = label;
        this.values = values;
    }
}
