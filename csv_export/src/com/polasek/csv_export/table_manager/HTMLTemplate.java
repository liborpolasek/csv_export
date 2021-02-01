package com.polasek.csv_export.table_manager;

public class HTMLTemplate {

  private String language;
  private String header;
  private String body;
  private HTMLTable htmlTable;

  public HTMLTemplate(final String language, final String header, final String body, final HTMLTable htmlTable) {
    this.language = language;
    this.header = header;
    this.body = body;
    this.htmlTable = htmlTable;
  }

  public void printHTML() {
    System.out.println("");
  }

}
