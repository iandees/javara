package com.yellowbkpk.termscrape;
/*
 * Section.java
 *
 * Copyright 2007 General Electric Company. All Rights Reserved.
 */

/**
 * @author Ian Dees
 *
 */
public class Section {

    private boolean isFirstYearOnly;
    private String department;
    private Integer block;
    private String sectionID;
    private String catalogURL;
    private String credits;
    private String division;

    /**
     * @param b
     */
    public void setFirstYearOnly(boolean b) {
        isFirstYearOnly = b;
    }

    /**
     * @param data
     */
    public void setDepartment(String data) {
        department = data;
    }

    /**
     * @param block
     */
    public void setBlock(Integer bl) {
        block = bl;
    }

    /**
     * @param parseInt
     */
    public void setSectionNumber(String sectionNum) {
        sectionID = sectionNum;
    }

    /**
     * @param catalogURL
     */
    public void setCatalogURL(String catURL) {
        catalogURL = catURL;
    }

    /**
     * @param data
     */
    public void setCredits(String data) {
        credits = data;
    }

    /**
     * @param data
     */
    public void setDivision(String data) {
        division = data;
    }

}
